package net.devfit.petclinic.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import net.devfit.petclinic.controller.api.OwnersControllerApi;
import net.devfit.petclinic.domain.model.Owner;
import net.devfit.petclinic.domain.service.OwnersService;

@Controller
@RequiredArgsConstructor
public class OwnersController implements OwnersControllerApi {

  private final OwnersService ownersService;

  @Override
  public ResponseEntity<Owner> createOwner(Owner owner) {
    return null;
  }

  @Override
  public ResponseEntity<Void> deleteOwner(Integer ownerId) {
    return null;
  }

  @Override
  public ResponseEntity<List<Owner>> queryOwners(String lastName) {
    return null;
  }

  @Override
  public ResponseEntity<Owner> showOwner(Integer ownerId) {
    return ResponseEntity.ok(ownersService.showOwner(ownerId));
  }

  @Override
  public ResponseEntity<Owner> updateOwner(Integer ownerId, Owner owner) {
    return null;
  }
}
