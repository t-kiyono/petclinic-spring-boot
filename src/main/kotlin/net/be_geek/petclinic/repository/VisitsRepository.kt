package net.be_geek.petclinic.repository

import net.be_geek.petclinic.entity.Visits
import org.springframework.data.repository.CrudRepository

interface VisitsRepository: CrudRepository<Visits, Int>, VisitsViewRepository {
}
