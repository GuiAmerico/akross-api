package br.com.akross.akrossapi.services.imp;

import static br.com.akross.akrossapi.utils.Util.getExtension;

import br.com.akross.akrossapi.controllers.request.CreateCompanyRequest;
import br.com.akross.akrossapi.controllers.request.PhotoRequest;
import br.com.akross.akrossapi.controllers.request.UpdateCompanyRequest;
import br.com.akross.akrossapi.controllers.response.CompanyResponse;
import br.com.akross.akrossapi.exceptions.ResourceAlreadyRegisteredException;
import br.com.akross.akrossapi.exceptions.ResourceNotFoundException;
import br.com.akross.akrossapi.exceptions.UploadPhotoException;
import br.com.akross.akrossapi.models.Company;
import br.com.akross.akrossapi.models.Photo;
import br.com.akross.akrossapi.repositories.CompanyRepository;
import br.com.akross.akrossapi.services.CompanyService;
import br.com.akross.akrossapi.utils.Util;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CompanyServiceImp implements CompanyService {

  private final CompanyRepository companyRepository;
  private final ModelMapper mapper;

  @Override
  public Page<CompanyResponse> findAll(Pageable pageable) {
    return companyRepository.findAll(pageable)
      .map(company -> mapper.map(company, CompanyResponse.class));
  }

  @Override
  public CompanyResponse findById(UUID id) {
    return companyRepository.findById(id)
      .map(company -> mapper.map(company, CompanyResponse.class))
      .orElseThrow(() -> new ResourceNotFoundException("Company not found"));
  }

  @Override
  public CompanyResponse create(CreateCompanyRequest companyRequest) {
    validCompany(companyRequest);
    PhotoRequest photoRequest = companyRequest.getPhoto();
    String filePath = getFilePath(
      companyRequest.getName(),
      photoRequest.getBase64Photo(),
      photoRequest.getPhotoContentType()
    );
    Company company = mapper.map(companyRequest, Company.class);
    Photo photo = new Photo(filePath, photoRequest.getPhotoContentType());
    company.setPhoto(photo);
    return mapper.map(companyRepository.save(company), CompanyResponse.class);
  }

  @Override
  public void delete(UUID id) {
    companyRepository.deleteById(id);
  }

  @Override
  public void update(UUID id, UpdateCompanyRequest companyRequest) {
    Company company = companyRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Company not found"));
    PhotoRequest photoRequest = companyRequest.getPhoto();
    if (Objects.nonNull(photoRequest.getBase64Photo())) {
      Objects
        .requireNonNull(photoRequest.getPhotoContentType(), "Photo content type is required");
      String filePath = getFilePath(
        company.getName(),
        photoRequest.getBase64Photo(),
        photoRequest.getBase64Photo()
      );
      Photo photo = new Photo(filePath, photoRequest.getPhotoContentType());
      company.setPhoto(photo);
    }
    mapper.map(companyRequest, company);
    companyRepository.save(company);
  }

  private void validCompany(CreateCompanyRequest companyRequest) {
    if (isCnpjRegistered(companyRequest)) {
      throw new ResourceAlreadyRegisteredException("CNPJ already registered");
    }
  }

  private boolean isCnpjRegistered(CreateCompanyRequest companyRequest) {
    return companyRepository.existsByCnpj(companyRequest.getCnpj());
  }

  private String getFilePath(String name, String base64Photo, String contentType) {
    String filePath = name
      .concat("_")
      .concat(UUID.randomUUID().toString())
      .concat(getExtension(contentType));
    try {
      Util.convertBase64toImage(
        base64Photo,
        filePath
      );
    } catch (IOException e) {
      throw new UploadPhotoException(e.getMessage());
    }
    return filePath;
  }
}
