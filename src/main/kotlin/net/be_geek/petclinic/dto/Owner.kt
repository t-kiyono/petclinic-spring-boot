package net.be_geek.petclinic.dto

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema

data class Owner(
    val id: Int?,
    @Schema(example = "George", required = true)
    val firstName: String,
    @Schema(example = "Franklin", required = true)
    val lastName: String,
    @Schema(example = "110 W. Liberty St.", required = true)
    val address: String,
    @Schema(example = "Madison", required = true)
    val city: String,
    @Schema(example = "6085551023", required = true)
    val telephone: String,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val pets: List<Pet>?
)
