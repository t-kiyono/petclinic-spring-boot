package net.be_geek.petclinic.entity

import org.springframework.data.annotation.Id
import java.time.LocalDate

data class Visits(
    @Id val id: Int?,
    val petId: Int,
    val visitDate: LocalDate,
    val description: String
)
