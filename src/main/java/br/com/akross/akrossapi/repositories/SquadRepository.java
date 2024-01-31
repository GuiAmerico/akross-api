package br.com.akross.akrossapi.repositories;

import br.com.akross.akrossapi.controllers.response.SquadResponse;
import br.com.akross.akrossapi.models.Squad;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface SquadRepository extends AbstractRepository<Squad, UUID> {


  @Query("SELECT s FROM Squad s WHERE s.company.id = ?1 AND s.active = true AND s.company.active = true")
  Page<Squad> findAllByCompany(UUID companyId, Pageable pageable);

  boolean existsByName(String name);
}
