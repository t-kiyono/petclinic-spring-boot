package net.be_geek.petclinic.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import net.be_geek.petclinic.dto.Pet
import net.be_geek.petclinic.service.PetsService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/owners/{ownerId}/pets", produces = [MediaType.APPLICATION_JSON_VALUE])
class PetsController(val petsService: PetsService) {

  @Operation(summary = "Finds Pets by Owner id", operationId = "queryPets")
  @GetMapping
  fun query(@PathVariable("ownerId") ownerId: Int): List<Pet> {
    return petsService.findAllByOwnerId(ownerId)
  }

  @Operation(summary = "Find Pet by ownerId and petId", operationId = "showPet")
  @ApiResponses(value = [
    ApiResponse(responseCode = "200", description = "OK"),
    ApiResponse(responseCode = "404", description = "Pet not found", content = [ Content() ])
  ])
  @GetMapping("/{petId}")
  fun show(@PathVariable("ownerId") ownerId: Int,
           @PathVariable("petId") petId: Int): Pet {
    return petsService.findById(ownerId, petId)
        .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found")  }
  }

  @Operation(summary = "Create Pet", operationId = "createPet")
  @PostMapping
  fun create(@PathVariable("ownerId") ownerId: Int,
             @RequestBody pet: Pet): Pet {
    return petsService.create(ownerId, pet)
  }

  @Operation(summary = "Update Pet", operationId = "updatePet")
  @PutMapping("/{petId}")
  fun update(@PathVariable("ownerId") ownerId: Int,
             @PathVariable("petId") petId: Int,
             @RequestBody pet: Pet): Pet {
    return petsService.update(ownerId, petId, pet)
  }

  @Operation(summary = "Delete Pet", operationId = "deletePet")
  @DeleteMapping("/{petId}")
  fun delete(@PathVariable("ownerId") ownerId: Int,
             @PathVariable("petId") petId: Int) {
    petsService.delete(ownerId, petId)
  }

}
