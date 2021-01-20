package net.be_geek.petclinic.entity

import org.springframework.data.annotation.Id
import java.time.LocalDate

data class Pets(
    @Id val id: Int?,
    val name: String,
    val birthDate: LocalDate,
    val typeId: Int,
    val ownerId: Int
)
