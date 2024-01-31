package br.com.akross.akrossapi.repositories;

import br.com.akross.akrossapi.models.Collaborator;
import java.util.UUID;

public interface CollaboratorRepository extends AbstractRepository<Collaborator, UUID> {


  boolean existsByCpf(String cpf);

  boolean existsByEmail(String email);

  boolean existsByPhone(String phone);
}
