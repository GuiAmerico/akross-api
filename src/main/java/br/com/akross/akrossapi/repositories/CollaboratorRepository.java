package br.com.akross.akrossapi.repositories;

import br.com.akross.akrossapi.models.Collaborator;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;

public interface CollaboratorRepository extends AbstractRepository<Collaborator, UUID> {


  @Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END FROM Collaborator c WHERE c.cpf = :cpf AND c.company.id = :companyId")
  boolean existsByCpf(String cpf, UUID companyId);
  @Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END FROM Collaborator c WHERE c.email = :email AND c.company.id = :companyId")
  boolean existsByEmail(String email, UUID companyId);
  @Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END FROM Collaborator c WHERE c.phone = :phone AND c.company.id = :companyId")
  boolean existsByPhone(String phone, UUID companyId);
}
