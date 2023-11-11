package net.devfit.petclinic.domain.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.devfit.petclinic.domain.model.Owner;
import net.devfit.petclinic.domain.repository.OwnersRepository;

@Service
@RequiredArgsConstructor
public class OwnersService {

  private final OwnersRepository ownersRepository;

  public Owner showOwner(Integer id) {
    return ownersRepository.findById(id);
  }

}
