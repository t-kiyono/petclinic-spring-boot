package net.be_geek.petclinic.service

import net.be_geek.petclinic.converter.DataConverter
import net.be_geek.petclinic.dto.Vet
import net.be_geek.petclinic.entity.Specialties
import net.be_geek.petclinic.entity.Vets
import net.be_geek.petclinic.repository.SpecialtiesRepository
import net.be_geek.petclinic.repository.VetSpecialtiesRepository
import net.be_geek.petclinic.repository.VetsRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class VetsService(val vetsRepository: VetsRepository,
                  val specialtiesRepository: SpecialtiesRepository,
                  val vetSpecialtiesRepository: VetSpecialtiesRepository) {

  companion object {
    const val CACHE_NAME = "vetsCache"
  }

  @Cacheable(cacheNames = [CACHE_NAME], key="'findAll'")
  fun findAll(): List<Vet> {
    return DataConverter.convertVets(vetsRepository.findAllVetsView())
  }

  @Cacheable(cacheNames = [CACHE_NAME], key="#vetId")
  fun findById(vetId: Int): Optional<Vet> {
    val results = vetsRepository.findVetsViewById(vetId)

    return if (results.count() > 0) {
      Optional.of(DataConverter.convertVets(results).first())
    } else {
      Optional.empty()
    }
  }

  @CacheEvict(cacheNames = [CACHE_NAME], key = "'findAll'")
  @Transactional
  fun create(vet: Vet): Vet {
    val specialties = specialtiesRepository.findAll();
    if (!vet.specialties.all { specialties.contains(Specialties(it.id, it.name)) }) {
      throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Specialties not exist")
    }

    val savedVet = saveVet(vet)

    vet.specialties.forEach {
      vetSpecialtiesRepository.create(savedVet.id!!, it.id!!)
    }

    return savedVet
  }

  @Caching(evict = [
    CacheEvict(cacheNames = [CACHE_NAME], key = "#vetId"),
    CacheEvict(cacheNames = [CACHE_NAME], key = "'findAll'")
  ])
  @Transactional
  fun update(vetId: Int, vet: Vet): Vet {
    val specialties = specialtiesRepository.findAll();
    if (!vet.specialties.all { specialties.contains(Specialties(it.id, it.name)) }) {
      throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Specialties not exist")
    }

    if (vetId != vet.id) {
      throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Veterinarian id mismatch")
    }

    val savedVet = saveVet(vet)

    vetSpecialtiesRepository.deleteByVetId(vetId)
    vet.specialties.forEach {
      vetSpecialtiesRepository.create(vetId, it.id!!)
    }

    return savedVet
  }

  @Caching(evict = [
    CacheEvict(cacheNames = [CACHE_NAME], key = "#vetId"),
    CacheEvict(cacheNames = [CACHE_NAME], key = "'findAll'")
  ])
  @Transactional
  fun delete(vetId: Int) {
    val vet = findById(vetId)
        .orElseThrow { ResponseStatusException(HttpStatus.BAD_REQUEST, "Veterinarian not found") }

    vetSpecialtiesRepository.deleteByVetId(vetId)

    val entity = Vets(
        id = vet.id,
        firstName = vet.firstName,
        lastName = vet.lastName
    )
    vetsRepository.delete(entity)
  }

  private fun saveVet(vet: Vet): Vet {
    val entity = Vets(
        id = null,
        firstName = vet.firstName,
        lastName = vet.lastName
    )

    val savedEntity = vetsRepository.save(entity)

    return Vet(
        id = savedEntity.id,
        firstName = savedEntity.firstName,
        lastName = savedEntity.lastName,
        specialties = vet.specialties
    )
  }

}