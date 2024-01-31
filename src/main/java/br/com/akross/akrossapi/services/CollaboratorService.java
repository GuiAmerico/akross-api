package br.com.akross.akrossapi.services;

import br.com.akross.akrossapi.controllers.request.CreateCollaboratorRequest;
import br.com.akross.akrossapi.controllers.request.UpdateCollaboratorRequest;
import br.com.akross.akrossapi.controllers.response.CollaboratorResponse;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CollaboratorService {

  CollaboratorResponse hireCollaborator(CreateCollaboratorRequest collaboratorRequest, UUID companyId);

  CollaboratorResponse findById(UUID id);

  Page<CollaboratorResponse> findByAllBySquad(UUID squadId, Pageable pageable);

  void dismissCollaborator(UUID id);

  void update(UUID id, UpdateCollaboratorRequest collaboratorRequest);


  Page<CollaboratorResponse> findByAllByCompany(UUID companyId, Pageable pageable);
}
