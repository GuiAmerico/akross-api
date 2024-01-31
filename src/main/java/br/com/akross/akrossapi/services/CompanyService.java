package br.com.akross.akrossapi.services;

import br.com.akross.akrossapi.controllers.request.CreateCompanyRequest;
import br.com.akross.akrossapi.controllers.request.UpdateCompanyRequest;
import br.com.akross.akrossapi.controllers.response.CompanyResponse;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyService {

  Page<CompanyResponse> findAll(Pageable pageable);
  CompanyResponse findById(UUID id);
  CompanyResponse create(CreateCompanyRequest companyRequest);
  void delete(UUID id);
  void update(UUID id, UpdateCompanyRequest companyRequest);
}
