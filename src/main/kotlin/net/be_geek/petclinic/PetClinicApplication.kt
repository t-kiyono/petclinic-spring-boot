package net.be_geek.petclinic

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@EnableCaching
@SpringBootApplication
class PetClinicApplication

fun main(args: Array<String>) {
  runApplication<PetClinicApplication>(*args)
}
