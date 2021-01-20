package net.be_geek.petclinic.dto

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

data class Pet(
    val id: Int?,
    val ownerId: Int,
    @Schema(example = "Leo", required = true)
    val name: String,
    @Schema(example = "2010-09-07", required = true)
    val birthDate: LocalDate,
    val type: PetType,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val visits: List<Visit>?
)
