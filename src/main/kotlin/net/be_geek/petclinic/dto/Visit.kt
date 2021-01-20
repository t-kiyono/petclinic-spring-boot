package net.be_geek.petclinic.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

data class Visit(
    val id: Int?,
    val petId: Int,
    @Schema(example = "2013-01-01", required = true)
    val visitDate: LocalDate,
    @Schema(example = "rabies shot", required = true)
    val description: String
)
