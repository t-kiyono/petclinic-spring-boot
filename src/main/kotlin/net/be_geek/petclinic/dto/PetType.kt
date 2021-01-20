package net.be_geek.petclinic.dto

import io.swagger.v3.oas.annotations.media.Schema

data class PetType(
    val id: Int?,
    @Schema(example = "cat", required = true)
    val name: String
)
