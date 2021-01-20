package net.be_geek.petclinic.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import net.be_geek.petclinic.dto.Owner
import net.be_geek.petclinic.service.OwnersService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/owners", produces = [MediaType.APPLICATION_JSON_VALUE])
class OwnersController(val ownersService: OwnersService) {

  @Operation(summary = "Finds Owners by LastName", operationId = "queryOwners")
  @GetMapping
  fun query(@RequestParam(value = "lastName", required = false) lastName: String?): List<Owner> {
    return if (lastName == null || lastName.isEmpty()) {
      ownersService.findAll()
    } else {
      ownersService.findAllByLastName(lastName)
    }
  }

  @Operation(summary = "Find Owner by id", operationId = "showOwner")
  @ApiResponses(value = [
    ApiResponse(responseCode = "200", description = "OK"),
    ApiResponse(responseCode = "404", description = "Owner not found", content = [ Content() ])
  ])
  @GetMapping("/{ownerId}")
  fun show(@PathVariable("ownerId") ownerId: Int): Owner {
    return ownersService.findById(ownerId)
        .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Owner not found") }
  }

  @Operation(summary = "Create Owner", operationId = "createOwner")
  @PostMapping
  fun create(@RequestBody owner: Owner): Owner {
    return ownersService.create(owner)
  }

  @Operation(summary = "Update Owner", operationId = "updateOwner")
  @PutMapping("/{ownerId}")
  fun update(@PathVariable("ownerId") ownerId: Int,
             @RequestBody owner: Owner): Owner {
    return ownersService.update(ownerId, owner)
  }

  @Operation(summary = "Delete Owner", operationId = "deleteOwner")
  @DeleteMapping("/{ownerId}")
  fun delete(@PathVariable("ownerId") ownerId: Int) {
    ownersService.delete(ownerId)
  }

}
