package br.com.akross.akrossapi.repositories;

import br.com.akross.akrossapi.models.Collaborator;
import br.com.akross.akrossapi.models.Company;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface CompanyRepository extends AbstractRepository<Company, UUID> {


  boolean existsByCnpj(String cnpj);

  @Query("SELECT s.collaborators FROM Squad s LEFT JOIN Collaborator c ON c.active = TRUE WHERE s.id = :squadId AND s.active = TRUE AND s.company.active = TRUE")
  Page<Collaborator> findAllBySquad(UUID squadId, Pageable pageable);

  @Query("SELECT cpn.collaborators FROM Company cpn LEFT JOIN Collaborator clbrt ON cpn.active = TRUE WHERE cpn.id = :companyId AND cpn.active = TRUE")
  Page<Collaborator> findAllByCompany(UUID companyId, Pageable pageable);

}
