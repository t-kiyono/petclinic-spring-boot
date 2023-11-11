package net.devfit.petclinic.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import net.devfit.petclinic.controller.api.PetsControllerApi;
import net.devfit.petclinic.domain.model.Pet;
import net.devfit.petclinic.domain.service.PetsService;

@Controller
@RequiredArgsConstructor
public class PetsController implements PetsControllerApi {

  private final PetsService petsService;

  @Override
  public ResponseEntity<Pet> createPet(Integer ownerId, Pet pet) {
    return null;
  }

  @Override
  public ResponseEntity<Void> deletePet(Integer ownerId, Integer petId) {
    return null;
  }

  @Override
  public ResponseEntity<List<Pet>> queryPets(Integer ownerId) {
    return ResponseEntity.ok(petsService.queryByOwnerId(ownerId));
  }

  @Override
  public ResponseEntity<Pet> showPet(Integer ownerId, Integer petId) {
    return null;
  }

  @Override
  public ResponseEntity<Pet> updatePet(Integer ownerId, Integer petId, Pet pet) {
    return null;
  }
}
