package net.be_geek.petclinic.repository

import net.be_geek.petclinic.entity.Specialties
import org.springframework.data.repository.CrudRepository

interface SpecialtiesRepository: CrudRepository<Specialties, Int> {
}