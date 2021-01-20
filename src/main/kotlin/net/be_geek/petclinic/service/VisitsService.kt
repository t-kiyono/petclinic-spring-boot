package net.be_geek.petclinic.service

import net.be_geek.petclinic.converter.DataConverter
import net.be_geek.petclinic.dto.Visit
import net.be_geek.petclinic.entity.Visits
import net.be_geek.petclinic.repository.VisitsRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class VisitsService(val visitsRepository: VisitsRepository,
                    val petsService: PetsService) {

  companion object {
    const val CACHE_NAME = "visitsCache"
  }

  @Cacheable(cacheNames = [CACHE_NAME], key = "{#ownerId, #petId}")
  fun findAllByPetId(ownerId: Int, petId: Int): List<Visit> {
    petsService.findById(ownerId, petId)
        .orElseThrow { ResponseStatusException(HttpStatus.BAD_REQUEST, "Pet not found") }

    return DataConverter.convertVisits(visitsRepository.findAllVisitsView(petId))
  }

  @Cacheable(cacheNames = [CACHE_NAME], key = "{#ownerId, #petId, #visitId}")
  fun findById(ownerId: Int, petId: Int, visitId: Int): Optional<Visit> {
    val pet = petsService.findById(ownerId, petId)
        .orElseThrow { ResponseStatusException(HttpStatus.BAD_REQUEST, "Pet not found") }

    if (pet.visits != null && pet.visits.count { it.id == visitId } > 0) {
      val results = visitsRepository.findVisitsViewById(petId, visitId)
      if (results.count() > 0) {
        return Optional.of(DataConverter.convertVisits(results).first())
      }
    }

    return Optional.empty()
  }

  @Caching(evict = [
    CacheEvict(cacheNames = [CACHE_NAME, PetsService.CACHE_NAME], key="{#ownerId, #petId}"),
    CacheEvict(cacheNames = [PetsService.CACHE_NAME], key="#ownerId")
  ])
  @Transactional
  fun create(ownerId: Int, petId: Int, visit: Visit): Visit {
    validate(ownerId, petId, visit)

    return saveVisit(visit)
  }

  @Caching(evict = [
    CacheEvict(cacheNames = [CACHE_NAME], key="{#ownerId, #petId, #visitId}"),
    CacheEvict(cacheNames = [CACHE_NAME, PetsService.CACHE_NAME], key="{#ownerId, #petId}"),
    CacheEvict(cacheNames = [PetsService.CACHE_NAME], key="#ownerId")
  ])
  @Transactional
  fun update(ownerId: Int, petId: Int, visitId: Int, visit: Visit): Visit {
    validate(ownerId, petId, visit)

    if (visitId != visit.id) {
      throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Visit id mismatch")
    }

    return saveVisit(visit)
  }

  @Caching(evict = [
    CacheEvict(cacheNames = [CACHE_NAME], key="{#ownerId, #petId, #visitId}"),
    CacheEvict(cacheNames = [CACHE_NAME, PetsService.CACHE_NAME], key="{#ownerId, #petId}"),
    CacheEvict(cacheNames = [PetsService.CACHE_NAME], key="#ownerId")
  ])
  @Transactional
  fun delete(ownerId: Int, petId: Int, visitId: Int) {
    val visit = findById(ownerId, petId, visitId)
        .orElseThrow { ResponseStatusException(HttpStatus.BAD_REQUEST, "Visit not found") }
    validate(ownerId, petId, visit)

    val entity = Visits(
        id = visit.id,
        petId = visit.petId,
        visitDate = visit.visitDate,
        description = visit.description
    )
    visitsRepository.delete(entity)
  }

  private fun validate(ownerId: Int, petId: Int, visit: Visit) {
    petsService.findById(ownerId, petId)
        .orElseThrow { ResponseStatusException(HttpStatus.BAD_REQUEST, "Pet not found") }

    if (petId != visit.petId) {
      throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Pet id mismatch")
    }
  }

  private fun saveVisit(visit: Visit): Visit {
    val entity = Visits(
        id = visit.id,
        petId = visit.petId,
        description = visit.description,
        visitDate = visit.visitDate
    )

    val savedEntity = visitsRepository.save(entity)

    return Visit(
        id = savedEntity.id,
        petId = savedEntity.petId,
        description = savedEntity.description,
        visitDate = savedEntity.visitDate
    )
  }

}