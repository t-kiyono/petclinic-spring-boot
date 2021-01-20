package net.be_geek.petclinic.repository

import net.be_geek.petclinic.entity.Owners
import net.be_geek.petclinic.entity.OwnersView
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.repository.CrudRepository
import java.util.*

interface OwnersRepository : CrudRepository<Owners, Int>, OwnersViewRepository {
}
