package net.be_geek.petclinic.controller

import io.swagger.v3.oas.annotations.Operation
import net.be_geek.petclinic.dto.PetType
import net.be_geek.petclinic.service.PetTypesService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/types", produces = [MediaType.APPLICATION_JSON_VALUE])
class PetTypesController(val petTypesService: PetTypesService) {

  @Operation(summary = "Finds PetTypes", operationId = "queryPetTypes")
  @GetMapping
  fun query(): List<PetType> {
    return petTypesService.findAll()
  }

}
