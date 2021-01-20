package net.be_geek.petclinic.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import net.be_geek.petclinic.dto.Visit
import net.be_geek.petclinic.service.VisitsService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/owners/{ownerId}/pets/{petId}/visits", produces = [MediaType.APPLICATION_JSON_VALUE])
class VisitsController(val visitsService: VisitsService) {

  @Operation(summary = "Finds Visits by ownerId and petId", operationId = "queryVisits")
  @GetMapping
  fun query(@PathVariable("ownerId") ownerId: Int,
            @PathVariable("petId") petId: Int): List<Visit> {
    return visitsService.findAllByPetId(ownerId, petId)
  }

  @Operation(summary = "Find Visit by ownerId and petId and visitId", operationId = "showVisit")
  @ApiResponses(value = [
    ApiResponse(responseCode = "200", description = "OK"),
    ApiResponse(responseCode = "404", description = "Visit not found")
  ])
  @GetMapping("/{visitId}")
  fun show(@PathVariable("ownerId") ownerId: Int,
           @PathVariable("petId") petId: Int,
           @PathVariable("visitId") visitId: Int): Visit {
    return visitsService.findById(ownerId, petId, visitId)
        .orElseThrow { ResponseStatusException(HttpStatus.BAD_REQUEST, "Visit not found") }
  }

  @Operation(summary = "Create Visit", operationId = "createVisit")
  @PostMapping
  fun create(@PathVariable("ownerId") ownerId: Int,
             @PathVariable("petId") petId: Int,
             @RequestBody visit: Visit): Visit {
    return visitsService.create(ownerId, petId, visit)
  }

  @Operation(summary = "Update Visit", operationId = "updateVisit")
  @PutMapping("/{visitId}")
  fun update(@PathVariable("ownerId") ownerId: Int,
             @PathVariable("petId") petId: Int,
             @PathVariable("visitId") visitId: Int,
             @RequestBody visit: Visit): Visit {
    return visitsService.update(ownerId, petId, visitId, visit)
  }

  @Operation(summary = "Delete Visit", operationId = "deleteVisit")
  @DeleteMapping("/{visitId}")
  fun delete(@PathVariable("ownerId") ownerId: Int,
             @PathVariable("petId") petId: Int,
             @PathVariable("visitId") visitId: Int) {
    visitsService.delete(ownerId, petId, visitId)
  }

}
