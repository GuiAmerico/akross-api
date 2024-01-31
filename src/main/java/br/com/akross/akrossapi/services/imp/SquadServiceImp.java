package br.com.akross.akrossapi.services.imp;

import br.com.akross.akrossapi.controllers.request.SquadRequest;
import br.com.akross.akrossapi.controllers.response.SquadResponse;
import br.com.akross.akrossapi.exceptions.ResourceAlreadyRegisteredException;
import br.com.akross.akrossapi.exceptions.ResourceNotFoundException;
import br.com.akross.akrossapi.models.Company;
import br.com.akross.akrossapi.models.Squad;
import br.com.akross.akrossapi.repositories.CompanyRepository;
import br.com.akross.akrossapi.repositories.SquadRepository;
import br.com.akross.akrossapi.services.SquadService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SquadServiceImp implements SquadService {

  private final SquadRepository squadRepository;
  private final CompanyRepository companyRepository;
  private final ModelMapper mapper;

  @Override
  public Page<SquadResponse> findAllByCompany(UUID companyId, Pageable pageable) {
    return squadRepository.findAllByCompany(companyId, pageable)
      .map(squad -> mapper.map(squad, SquadResponse.class));
  }

  @Override
  public SquadResponse findById(UUID id) {
    return squadRepository.findById(id)
      .map(squad -> mapper.map(squad, SquadResponse.class))
      .orElseThrow(() -> new ResourceNotFoundException("Squad not found"));
  }

  @Override
  public SquadResponse create(SquadRequest squadRequest, UUID companyId) {
    validSquad(squadRequest.getName());
    Company company = companyRepository.findById(companyId)
      .orElseThrow(() -> new ResourceNotFoundException("Company not found"));
    Squad squad = mapper.map(squadRequest, Squad.class);
    squad.setCompany(company);
    return mapper.map(squadRepository.save(squad), SquadResponse.class);
  }

  @Override
  public void delete(UUID id) {
    squadRepository.deleteById(id);
  }

  @Override
  public void update(UUID id, SquadRequest squadRequest) {
    validSquad(squadRequest.getName());
    Squad squad = mapper.map(squadRequest, Squad.class);
    squad.setId(id);
    squadRepository.save(squad);
  }

  private void validSquad(String name) {
    boolean existsByName = isNameRegistered(name);
    if (existsByName) {
      throw new ResourceAlreadyRegisteredException("Squad name already registered");
    }
  }

  private boolean isNameRegistered(String name) {
    return squadRepository.existsByName(name);
  }
}
