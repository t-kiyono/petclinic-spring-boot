package net.be_geek.petclinic.service

import net.be_geek.petclinic.converter.DataConverter
import net.be_geek.petclinic.dto.Owner
import net.be_geek.petclinic.entity.Owners
import net.be_geek.petclinic.repository.OwnersRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class OwnersService(val ownersRepository: OwnersRepository) {

  companion object {
    const val CACHE_NAME = "ownersCache"
  }

  @Cacheable(cacheNames = [CACHE_NAME], key = "'findAll'")
  fun findAll(): List<Owner> {
    return DataConverter.convertOwner(ownersRepository.findAllOwnersView())
  }

  fun findAllByLastName(lastName: String): List<Owner> {
    return DataConverter.convertOwner(ownersRepository.findAllOwnersViewByLastName(lastName))
  }

  @Cacheable(cacheNames = [CACHE_NAME], key = "#ownerId")
  fun findById(ownerId: Int): Optional<Owner> {
    val results = ownersRepository.findOwnersViewById(ownerId)

    return if (results.count() > 0) {
      Optional.of(DataConverter.convertOwner(results).first())
    } else {
      Optional.empty()
    }
  }

  @CacheEvict(cacheNames = [CACHE_NAME], key = "'findAll'")
  @Transactional
  fun create(owner: Owner): Owner {
    return saveOwner(owner)
  }

  @Caching(evict = [
    CacheEvict(cacheNames = [CACHE_NAME], key = "#ownerId"),
    CacheEvict(cacheNames = [CACHE_NAME], key = "'findAll'")
  ])
  @Transactional
  fun update(ownerId: Int, owner: Owner): Owner {
    if (ownerId != owner.id) {
      throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Owner id mismatch")
    }

    return saveOwner(owner)
  }

  @Caching(evict = [
    CacheEvict(cacheNames = [CACHE_NAME], key = "#ownerId"),
    CacheEvict(cacheNames = [CACHE_NAME], key = "'findAll'")
  ])
  @Transactional
  fun delete(ownerId: Int) {
    val entity = ownersRepository.findById(ownerId)
        .orElseThrow { throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Owner not found") }

    ownersRepository.delete(entity)
  }

  private fun saveOwner(owner: Owner): Owner {
    val entity = Owners(
        id = owner.id,
        firstName = owner.firstName,
        lastName = owner.lastName,
        address = owner.address,
        city = owner.city,
        telephone = owner.telephone
    )
    val savedEntity = ownersRepository.save(entity)
    return Owner(
        id = savedEntity.id,
        firstName = savedEntity.firstName,
        lastName = savedEntity.lastName,
        address = savedEntity.address,
        city = savedEntity.city,
        telephone = savedEntity.telephone,
        pets = null
    )
  }

}
