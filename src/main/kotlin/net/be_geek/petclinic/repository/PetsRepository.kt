package net.be_geek.petclinic.repository

import net.be_geek.petclinic.entity.Pets
import net.be_geek.petclinic.entity.PetsView
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository

interface PetsRepository: CrudRepository<Pets, Int>, PetsViewRepository {
}
