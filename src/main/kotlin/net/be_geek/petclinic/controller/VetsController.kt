package net.be_geek.petclinic.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import net.be_geek.petclinic.dto.Vet
import net.be_geek.petclinic.service.VetsService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/veterinarians", produces = [MediaType.APPLICATION_JSON_VALUE])
class VetsController(val vetsService: VetsService) {

  @Operation(summary = "Finds Veterinarians", operationId = "queryVets")
  @GetMapping
  fun query(): List<Vet> {
    return vetsService.findAll()
  }

  @Operation(summary = "Find Veterinarian by id", operationId = "showVet")
  @ApiResponses(value = [
    ApiResponse(responseCode = "200", description = "OK"),
    ApiResponse(responseCode = "404", description = "Veterinarian not found")
  ])
  @GetMapping("/{vetId}")
  fun show(@PathVariable("vetId") vetId: Int): Vet {
    return vetsService.findById(vetId)
        .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Veterinarian not found") }
  }

  @Operation(summary = "Create Veterinarian", operationId = "createVet")
  @PostMapping
  fun create(@RequestBody vet: Vet): Vet {
    return vetsService.create(vet)
  }

  @Operation(summary = "Update Veterinarian", operationId = "updateVet")
  @PutMapping("/{vetId}")
  fun update(@PathVariable("vetId") vetId: Int,
             @RequestBody vet: Vet): Vet {
    return vetsService.update(vetId, vet)
  }

  @Operation(summary = "Delete Veterinarian", operationId = "deleteVet")
  @DeleteMapping("/{vetId}")
  fun delete(@PathVariable("vetId") vetId: Int) {
    return vetsService.delete(vetId)
  }

}
