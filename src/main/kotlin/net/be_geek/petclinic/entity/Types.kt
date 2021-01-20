package net.be_geek.petclinic.entity

import org.springframework.data.annotation.Id

data class Types(
    @Id val id: Int?,
    val name: String
)
