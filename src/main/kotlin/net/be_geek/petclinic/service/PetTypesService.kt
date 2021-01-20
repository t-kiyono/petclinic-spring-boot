package net.be_geek.petclinic.service

import net.be_geek.petclinic.dto.PetType
import net.be_geek.petclinic.repository.TypesRepository
import org.springframework.stereotype.Service

@Service
class PetTypesService(val typesRepository: TypesRepository) {

  fun findAll(): List<PetType> {
    return typesRepository.findAll().map { PetType(it.id, it.name) }
  }

}