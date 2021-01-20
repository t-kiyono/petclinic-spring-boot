package net.be_geek.petclinic.service

import net.be_geek.petclinic.converter.DataConverter
import net.be_geek.petclinic.dto.Pet
import net.be_geek.petclinic.dto.PetType
import net.be_geek.petclinic.entity.Pets
import net.be_geek.petclinic.entity.Types
import net.be_geek.petclinic.repository.PetsRepository
import net.be_geek.petclinic.repository.TypesRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class PetsService(val petsRepository: PetsRepository,
                  val typesRepository: TypesRepository,
                  val ownersService: OwnersService) {

  companion object {
    const val CACHE_NAME = "petsCache"
  }

  @Cacheable(cacheNames = [CACHE_NAME], key = "#ownerId")
  fun findAllByOwnerId(ownerId: Int): List<Pet> {
    ownersService.findById(ownerId)
        .orElseThrow { ResponseStatusException(HttpStatus.BAD_REQUEST, "Owner not found") }

    return DataConverter.convertPets(petsRepository.findAllPetsView(ownerId))
  }

  @Cacheable(cacheNames = [CACHE_NAME], key = "{#ownerId, #petId}")
  fun findById(ownerId: Int, petId: Int): Optional<Pet> {
    val owner = ownersService.findById(ownerId)
        .orElseThrow { ResponseStatusException(HttpStatus.BAD_REQUEST, "Owner not found") }

    if (owner.pets != null && owner.pets.count { it.id == petId } > 0) {
      val results = petsRepository.findPetsViewById(ownerId, petId)
      if (results.count() > 0) {
        return Optional.of(DataConverter.convertPets(results).first())
      }
    }

    return Optional.empty()
  }

  @Caching(evict = [
    CacheEvict(cacheNames = [CACHE_NAME, OwnersService.CACHE_NAME], key = "#ownerId"),
    CacheEvict(cacheNames = [OwnersService.CACHE_NAME], key = "'findAll'")
  ])
  @Transactional
  fun create(ownerId: Int, pet: Pet): Pet {
    validate(ownerId, pet)

    return savePet(pet, getPetType(pet))
  }

  @Caching(evict = [
    CacheEvict(cacheNames = [CACHE_NAME], key = "{#ownerId, #petId}"),
    CacheEvict(cacheNames = [CACHE_NAME, OwnersService.CACHE_NAME], key = "#ownerId"),
    CacheEvict(cacheNames = [OwnersService.CACHE_NAME], key = "'findAll'")
  ])
  @Transactional
  fun update(ownerId: Int, petId: Int, pet: Pet): Pet {
    validate(ownerId, pet)

    if (petId != pet.id) {
      throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Pet id mismatch")
    }

    return savePet(pet, getPetType(pet))
  }

  @Caching(evict = [
    CacheEvict(cacheNames = [CACHE_NAME], key = "{#ownerId, #petId}"),
    CacheEvict(cacheNames = [CACHE_NAME, OwnersService.CACHE_NAME], key = "#ownerId"),
    CacheEvict(cacheNames = [OwnersService.CACHE_NAME], key = "'findAll'")
  ])
  @Transactional
  fun delete(ownerId: Int, petId: Int) {
    val pet = findById(ownerId, petId)
        .orElseThrow { ResponseStatusException(HttpStatus.BAD_REQUEST, "Pet not found") }
    validate(ownerId, pet)

    val entity = Pets(
        id = pet.id,
        name = pet.name,
        typeId = pet.type.id!!,
        birthDate = pet.birthDate,
        ownerId = pet.ownerId
    )
    petsRepository.delete(entity)
  }

  private fun validate(ownerId: Int, pet: Pet) {
    ownersService.findById(ownerId)
        .orElseThrow { ResponseStatusException(HttpStatus.BAD_REQUEST, "Owner not found") }

    if (ownerId != pet.ownerId) {
      throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Owner id mismatch")
    }
  }

  private fun getPetType(pet: Pet): Types {
    if (pet.type.id == null) {
      throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Pet type id not set to a pet object")
    }
    return typesRepository.findById(pet.type.id)
        .orElseThrow { throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Pet type not found by id=${pet.type.id}") }
  }

  private fun savePet(pet: Pet, type: Types): Pet {
    val entity = Pets(
        id = pet.id,
        ownerId = pet.ownerId,
        name = pet.name,
        typeId = pet.type.id!!,
        birthDate = pet.birthDate
    )

    val savedEntity = petsRepository.save(entity)

    return Pet(
        id = savedEntity.id,
        ownerId = savedEntity.ownerId,
        name = savedEntity.name,
        birthDate = savedEntity.birthDate,
        type = PetType(
            id = type.id,
            name = type.name
        ),
        visits = null
    )
  }

}