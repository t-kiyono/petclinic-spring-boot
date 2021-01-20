package net.be_geek.petclinic.entity

import org.springframework.data.annotation.Id

data class Owners(
    @Id val id: Int?,
    val firstName: String,
    val lastName: String,
    val address: String,
    val city: String,
    val telephone: String
)
