package net.devfit.petclinic.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.devfit.petclinic.domain.model.Pet;
import net.devfit.petclinic.domain.repository.PetsRepository;

@Service
@RequiredArgsConstructor
public class PetsService {

  private final PetsRepository petsRepository;

  public List<Pet> queryByOwnerId(Integer ownerId) {
    return petsRepository.findByOwnerId(ownerId);
  }

}
