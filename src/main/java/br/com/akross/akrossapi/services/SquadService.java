package br.com.akross.akrossapi.services;

import br.com.akross.akrossapi.controllers.request.SquadRequest;
import br.com.akross.akrossapi.controllers.response.SquadResponse;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SquadService {

  SquadResponse findById(UUID id);

  SquadResponse create(SquadRequest squadRequest, UUID companyId);

  void delete(UUID id);

  void update(UUID id, SquadRequest squadRequest);

  Page<SquadResponse> findAllByCompany(UUID companyId, Pageable pageable);
}
