package br.com.akross.akrossapi.controllers;

import br.com.akross.akrossapi.controllers.request.CreateCompanyRequest;
import br.com.akross.akrossapi.controllers.request.UpdateCompanyRequest;
import br.com.akross.akrossapi.controllers.response.CompanyResponse;
import br.com.akross.akrossapi.services.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Company")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/companies")
public class CompanyController {

  private final CompanyService companyService;

  @Operation(summary = "Get all companies")
  @GetMapping
  public ResponseEntity<Page<CompanyResponse>> findAll(
    @PageableDefault(size = 5) Pageable pageable
  ) {
    Page<CompanyResponse> companies = companyService.findAll(pageable);
    return ResponseEntity.ok(companies);
  }

  @Operation(summary = "Get a company by ID")
  @GetMapping("/{id}")
  public ResponseEntity<CompanyResponse> findById(@PathVariable UUID id) {
    CompanyResponse company = companyService.findById(id);
    return ResponseEntity.ok(company);
  }

  @Operation(summary = "Create a new company")
  @PostMapping
  public ResponseEntity<CompanyResponse> create(
    @RequestBody @Valid CreateCompanyRequest companyRequest) {
    CompanyResponse createdCompany = companyService.create(companyRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdCompany);
  }

  @Operation(summary = "Delete a company")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    companyService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Update a company")
  @PutMapping("/{id}")
  public ResponseEntity<Void> update(
    @PathVariable UUID id,
    @RequestBody @Valid UpdateCompanyRequest companyRequest
  ) {
    companyService.update(id, companyRequest);
    return ResponseEntity.noContent().build();
  }
}
