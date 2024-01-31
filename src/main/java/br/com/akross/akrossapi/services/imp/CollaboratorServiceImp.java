package br.com.akross.akrossapi.services.imp;

import static br.com.akross.akrossapi.utils.Util.getExtension;

import br.com.akross.akrossapi.controllers.request.CreateCollaboratorRequest;
import br.com.akross.akrossapi.controllers.request.PhotoRequest;
import br.com.akross.akrossapi.controllers.request.UpdateCollaboratorRequest;
import br.com.akross.akrossapi.controllers.response.CollaboratorResponse;
import br.com.akross.akrossapi.exceptions.ResourceAlreadyRegisteredException;
import br.com.akross.akrossapi.exceptions.ResourceNotFoundException;
import br.com.akross.akrossapi.exceptions.UploadPictureException;
import br.com.akross.akrossapi.models.Collaborator;
import br.com.akross.akrossapi.models.Company;
import br.com.akross.akrossapi.models.Photo;
import br.com.akross.akrossapi.models.Squad;
import br.com.akross.akrossapi.repositories.CollaboratorRepository;
import br.com.akross.akrossapi.repositories.CompanyRepository;
import br.com.akross.akrossapi.repositories.SquadRepository;
import br.com.akross.akrossapi.services.CollaboratorService;
import br.com.akross.akrossapi.utils.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class CollaboratorServiceImp implements CollaboratorService {

  private final CollaboratorRepository collaboratorRepository;
  private final CompanyRepository companyRepository;
  private final SquadRepository squadRepository;
  private final ModelMapper mapper;

  private static boolean isSquadBelongsToCompany(UUID squadId, Company company) {
    return company.getSquads().stream()
      .anyMatch(
        companySquad -> companySquad.getId().equals(squadId)
      );
  }

  @Override
  public CollaboratorResponse hireCollaborator(
    CreateCollaboratorRequest collaboratorRequest,
    UUID companyId
  ) {
    validCollaborator(collaboratorRequest);
    Company company = companyRepository.findById(companyId)
      .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

    List<UUID> squadsId = collaboratorRequest.getAllocatedIn();
    Collaborator collaborator = mapper.map(collaboratorRequest, Collaborator.class);
    if (Objects.nonNull(squadsId)) {
      squadsId.forEach(
        squadId -> {
          Squad squad = squadRepository.findById(squadId)
            .orElseThrow(() -> new ResourceNotFoundException("Squad not found"));
          if (!isSquadBelongsToCompany(squadId, company)
          ) {
            throw new ResourceNotFoundException("Squad not found in company");
          }
          collaborator.addSquad(squad);
        }
      );
    }
    PhotoRequest photoRequest = collaboratorRequest.getPhoto();
    collaborator.setCompany(company);
    String filePath = collaborator.getName()
      .concat("_")
      .concat(UUID.randomUUID().toString())
      .concat(getExtension(photoRequest.getPhotoContentType()));
    try {
      Util.convertBase64toImage(
        photoRequest.getBase64Photo(),
        filePath
      );
    } catch (IOException e) {
      throw new UploadPictureException(e.getMessage());
    }
    Photo photo = new Photo(filePath, photoRequest.getPhotoContentType());
    collaborator.setPhoto(photo);
    return mapper.map(collaboratorRepository.save(collaborator), CollaboratorResponse.class);
  }

  @Override
  public CollaboratorResponse findById(UUID id) {
    return collaboratorRepository
      .findById(id)
      .map(collaborator -> mapper.map(collaborator, CollaboratorResponse.class))
      .orElseThrow(() -> new ResourceNotFoundException("Collaborator not found"));
  }

  @Override
  public Page<CollaboratorResponse> findByAllBySquad(UUID squadId, Pageable pageable) {
    return companyRepository.findAllBySquad(squadId, pageable)
      .map(collaborator -> mapper.map(collaborator, CollaboratorResponse.class));
  }

  @Override
  public Page<CollaboratorResponse> findByAllByCompany(UUID companyId, Pageable pageable) {
    return companyRepository.findAllByCompany(companyId, pageable)
      .map(collaborator -> mapper.map(collaborator, CollaboratorResponse.class));
  }

  @Override
  public void dismissCollaborator(UUID id) {
    Collaborator collaborator = collaboratorRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Collaborator not found"));
    collaborator.setActive(false);
    collaborator.setCompany(null);
    collaborator.setSquads(Collections.emptyList());
    collaboratorRepository.save(collaborator);
  }

  @Override
  public void update(UUID id, UpdateCollaboratorRequest collaboratorRequest) {
    List<UUID> squadsId = collaboratorRequest.getAllocatedIn();
    Collaborator collaborator = mapper.map(collaboratorRequest, Collaborator.class);
    List<Squad> newSquads = new ArrayList<>();
    if (Objects.nonNull(squadsId)) {
      squadsId.forEach(
        squadId -> {
          Squad squad = squadRepository.findById(squadId)
            .orElseThrow(() -> new ResourceNotFoundException("Squad not found"));
          if (!isSquadBelongsToCompany(squadId, collaborator.getCompany())) {
            throw new ResourceNotFoundException("Squad not found in company");
          }
          newSquads.add(squad);
        }
      );
    }
    collaborator.setSquads(newSquads);
    collaboratorRepository.save(collaborator);
  }

  private void validCollaborator(CreateCollaboratorRequest companyRequest) {
    if (isCpfRegistered(companyRequest)) {
      throw new ResourceAlreadyRegisteredException("CPF already registered");
    }
    if (isEmailRegistered(companyRequest)) {
      throw new ResourceAlreadyRegisteredException("Email already registered");
    }
    if (isPhoneRegistered(companyRequest)) {
      throw new ResourceAlreadyRegisteredException("Phone already registered");
    }
  }

  private boolean isCpfRegistered(CreateCollaboratorRequest companyRequest) {
    return collaboratorRepository.existsByCpf(companyRequest.getCpf());
  }

  private boolean isEmailRegistered(CreateCollaboratorRequest companyRequest) {
    return collaboratorRepository.existsByEmail(companyRequest.getEmail());
  }

  private boolean isPhoneRegistered(CreateCollaboratorRequest companyRequest) {
    return collaboratorRepository.existsByPhone(companyRequest.getPhone());
  }
}
