package br.com.akross.akrossapi.controllers;

import br.com.akross.akrossapi.controllers.request.SquadRequest;
import br.com.akross.akrossapi.controllers.response.SquadResponse;
import br.com.akross.akrossapi.services.SquadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Squad")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/companies/{companyId}/squads")
public class SquadController {

  private final SquadService squadService;

  @Operation(summary = "Get all squads for a specific company")
  @GetMapping
  public ResponseEntity<Page<SquadResponse>> findAll(
    @PathVariable UUID companyId,
    @PageableDefault(size = 5) Pageable pageable
  ) {
    Page<SquadResponse> squads = squadService.findAllByCompany(companyId, pageable);
    return ResponseEntity.ok(squads);
  }

  @Operation(summary = "Get a squad by ID")
  @GetMapping("/{id}")
  public ResponseEntity<SquadResponse> findById(
    @PathVariable UUID companyId,
    @PathVariable UUID id
  ) {
    SquadResponse squad = squadService.findById(id);
    return ResponseEntity.ok(squad);
  }

  @Operation(summary = "Create a new squad for a specific company")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<SquadResponse> create(
    @PathVariable UUID companyId,
    @RequestBody SquadRequest squadRequest
  ) {
    SquadResponse createdSquad = squadService.create(squadRequest, companyId);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdSquad);
  }

  @Operation(summary = "Delete a squad")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(
    @PathVariable UUID companyId,
    @PathVariable UUID id
  ) {
    squadService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Update a squad")
  @PutMapping("/{id}")
  public ResponseEntity<Void> update(
    @PathVariable UUID companyId,
    @PathVariable UUID id,
    @RequestBody SquadRequest squadRequest
  ) {
    squadService.update(id, squadRequest);
    return ResponseEntity.noContent().build();
  }
}
