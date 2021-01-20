package net.be_geek.petclinic.dto

import io.swagger.v3.oas.annotations.media.Schema

data class Specialty(
    val id: Int?,
    @Schema(example = "radiology", required = true)
    val name: String
)
