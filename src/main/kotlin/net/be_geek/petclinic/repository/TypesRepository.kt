package net.be_geek.petclinic.repository

import net.be_geek.petclinic.entity.Types
import org.springframework.data.repository.CrudRepository

interface TypesRepository: CrudRepository<Types, Int> {
}