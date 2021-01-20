package net.be_geek.petclinic.entity

import org.springframework.data.annotation.Id

data class Specialties(
    @Id val id: Int?,
    val name: String
)
