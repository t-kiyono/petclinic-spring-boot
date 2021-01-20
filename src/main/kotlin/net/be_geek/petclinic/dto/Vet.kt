package net.be_geek.petclinic.dto

import io.swagger.v3.oas.annotations.media.Schema

data class Vet(
    val id: Int?,
    @Schema(example = "James", required = true)
    val firstName: String,
    @Schema(example = "Carter", required = true)
    val lastName: String,
    val specialties: List<Specialty>
)
