package br.com.akross.akrossapi.controllers;

import br.com.akross.akrossapi.controllers.request.CreateCollaboratorRequest;
import br.com.akross.akrossapi.controllers.request.UpdateCollaboratorRequest;
import br.com.akross.akrossapi.controllers.response.CollaboratorResponse;
import br.com.akross.akrossapi.services.CollaboratorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Collaborator")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/companies/{companyId}")
public class CollaboratorController {

  private final CollaboratorService collaboratorService;

  @Operation(summary = "Get all collaborators by squad")
  @GetMapping("/squads/{squadId}/collaborators")
  public ResponseEntity<Page<CollaboratorResponse>> findAll(
    @PathVariable UUID companyId,
    @PathVariable UUID squadId,
    @PageableDefault(size = 5) Pageable pageable

  ) {
    Page<CollaboratorResponse> collaborators = collaboratorService.findByAllBySquad(squadId,
      pageable);
    return ResponseEntity.ok(collaborators);
  }

  @Operation(summary = "Get all collaborators by company")
  @GetMapping("/collaborators")
  public ResponseEntity<Page<CollaboratorResponse>> findAll(
    @PathVariable UUID companyId,
    @PageableDefault(size = 5) Pageable pageable
  ) {
    Page<CollaboratorResponse> collaborators = collaboratorService.findByAllByCompany(companyId,
      pageable);
    return ResponseEntity.ok(collaborators);
  }

  @Operation(summary = "Get a collaborator by ID")
  @GetMapping("/collaborators/{id}")
  public ResponseEntity<CollaboratorResponse> findById(
    @PathVariable UUID id,
    @PathVariable UUID companyId
  ) {
    CollaboratorResponse collaborator = collaboratorService.findById(id);
    return ResponseEntity.ok(collaborator);
  }

  @Operation(summary = "Create a new collaborator")
  @PostMapping("/collaborators")
  public ResponseEntity<CollaboratorResponse> create(
    @RequestBody CreateCollaboratorRequest collaboratorRequest,
    @PathVariable UUID companyId

  ) {
    CollaboratorResponse createdCollaborator = collaboratorService.hireCollaborator(
      collaboratorRequest,
      companyId
    );
    return ResponseEntity.status(HttpStatus.CREATED).body(createdCollaborator);
  }

  @Operation(summary = "Delete a collaborator")
  @DeleteMapping("/collaborators/{id}")
  public ResponseEntity<Void> delete(
    @PathVariable UUID id,
    @PathVariable UUID companyId

  ) {
    collaboratorService.dismissCollaborator(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Update a collaborator")
  @PutMapping("/collaborators/{id}")
  public ResponseEntity<Void> update(
    @PathVariable UUID id,
    @RequestBody UpdateCollaboratorRequest collaboratorRequest,
    @PathVariable UUID companyId

  ) {
    collaboratorService.update(id, collaboratorRequest);
    return ResponseEntity.noContent().build();
  }
}
