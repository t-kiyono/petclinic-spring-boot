package net.be_geek.petclinic.entity

import org.springframework.data.annotation.Id

data class Vets(
    @Id val id: Int?,
    val firstName: String,
    val lastName: String
)
