package net.be_geek.petclinic.service

import net.be_geek.petclinic.dto.Specialty
import net.be_geek.petclinic.entity.Specialties
import net.be_geek.petclinic.repository.SpecialtiesRepository
import net.be_geek.petclinic.repository.VetSpecialtiesRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class SpecialtiesService(val specialtiesRepository: SpecialtiesRepository,
                         val vetsSpecialtiesRepository: VetSpecialtiesRepository) {

  fun findAll(): List<Specialty> {
    return specialtiesRepository.findAll()
        .map {
          Specialty(
              id = it.id,
              name = it.name
          )
        }
  }

  fun findById(specialtyId: Int): Optional<Specialty> {
    return specialtiesRepository.findById(specialtyId)
        .map {
          Specialty(
              id = it.id,
              name = it.name
          )
        }
  }

  @Transactional
  fun create(specialty: Specialty): Specialty {
    return saveSpecialty(specialty)
  }

  @Transactional
  fun update(specialtyId: Int, specialty: Specialty): Specialty {
    if (specialtyId != specialty.id) {
      throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Specialty id mismatch")
    }

    return saveSpecialty(specialty)
  }

  @Transactional
  fun delete(specialtyId: Int) {
    val specialty = findById(specialtyId)
        .orElseThrow { ResponseStatusException(HttpStatus.BAD_REQUEST, "Specialty not found") }

    vetsSpecialtiesRepository.deleteBySpecialtyId(specialtyId)

    val entity = Specialties(
        id = specialty.id,
        name = specialty.name
    )
    specialtiesRepository.delete(entity)
  }

  private fun saveSpecialty(specialty: Specialty): Specialty {
    val entity = Specialties(
        id = specialty.id,
        name = specialty.name
    )

    val savedEntity = specialtiesRepository.save(entity)

    return Specialty(
        id = savedEntity.id,
        name = savedEntity.name
    )
  }
}