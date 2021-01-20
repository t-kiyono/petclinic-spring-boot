package net.be_geek.petclinic.converter

import net.be_geek.petclinic.dto.*
import net.be_geek.petclinic.entity.*

object DataConverter {

  fun convertOwner(src: Iterable<OwnersView>): List<Owner> {
    return src.groupBy { it.owners }
        .map {
          val owner = it.key
          Owner(
              owner.id,
              owner.firstName,
              owner.lastName,
              owner.address,
              owner.city,
              owner.telephone,
              it.value.mapNotNull { v ->
                v.pets?.let { pet ->
                  Pet(
                      pet.id,
                      owner.id!!,
                      pet.name,
                      pet.birthDate,
                      PetType(
                          v.types!!.id,
                          v.types.name
                      ),
                      null
                  )
                }
              }
          )
        }
  }

  fun convertPets(src: Iterable<PetsView>): List<Pet> {
    return src.groupBy { it.pets }
        .map {
          val pets = it.key
          val types = it.value.first().types
          Pet(
              pets.id,
              pets.ownerId,
              pets.name,
              pets.birthDate,
              PetType(
                  types.id,
                  types.name
              ),
              it.value.mapNotNull { v ->
                v.visits?.let { visit ->
                  Visit(
                      visit.id,
                      pets.id!!,
                      visit.visitDate,
                      visit.description
                  )
                }
              }
          )
        }
  }

  fun convertVisits(src: Iterable<VisitsView>): List<Visit> {
    return src.map {
      val visits = it.visits
      Visit(
          visits.id,
          visits.petId,
          visits.visitDate,
          visits.description
      )
    }
  }

  fun convertVets(src: Iterable<VetsView>): List<Vet> {
    return src.groupBy { it.vets }
        .map {
          val vets = it.key
          Vet(
              vets.id,
              vets.firstName,
              vets.lastName,
              it.value.mapNotNull { v ->
                v.specialties?.let { specialty ->
                  Specialty(
                      specialty.id,
                      specialty.name
                  )
                }
              }
          )
        }
  }

}