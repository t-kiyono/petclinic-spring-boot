package net.be_geek.petclinic.repository

import net.be_geek.petclinic.entity.Vets
import org.springframework.data.repository.CrudRepository

interface VetsRepository: CrudRepository<Vets, Int>, VetsViewRepository {
}