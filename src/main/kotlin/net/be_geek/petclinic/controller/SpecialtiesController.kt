package net.be_geek.petclinic.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import net.be_geek.petclinic.dto.Specialty
import net.be_geek.petclinic.service.SpecialtiesService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/specialties", produces = [MediaType.APPLICATION_JSON_VALUE])
class SpecialtiesController(val specialtiesService: SpecialtiesService) {

  @Operation(summary = "Finds Specialties", operationId = "querySpecialties")
  @GetMapping
  fun query(): List<Specialty> {
    return specialtiesService.findAll()
  }

  @Operation(summary = "Find Specialty by id", operationId = "showSpecialty")
  @ApiResponses(value = [
    ApiResponse(responseCode = "200", description = "OK"),
    ApiResponse(responseCode = "404", description = "Specialty not found")
  ])
  @GetMapping("/{specialtyId}")
  fun show(@PathVariable("specialtyId") specialtyId: Int): Specialty {
    return specialtiesService.findById(specialtyId)
        .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Specialty not found") }
  }

  @Operation(summary = "Create Specialty", operationId = "createSpecialty")
  @PostMapping
  fun create(@RequestBody specialty: Specialty): Specialty {
    return specialtiesService.create(specialty)
  }

  @Operation(summary = "Update Specialty", operationId = "updateSpecialty")
  @PutMapping("/{specialtyId}")
  fun update(@PathVariable("specialtyId") specialtyId: Int,
             @RequestBody specialty: Specialty): Specialty {
    return specialtiesService.update(specialtyId, specialty)
  }

  @Operation(summary = "Delete Specialty", operationId = "deleteSpecialty")
  @DeleteMapping("/{specialtyId}")
  fun delete(@PathVariable("specialtyId") specialtyId: Int) {
    specialtiesService.delete(specialtyId)
  }

}
