package br.com.akross.akrossapi;

import static br.com.akross.akrossapi.mock.MockUtils.getCollaborator;
import static br.com.akross.akrossapi.mock.MockUtils.getCollaboratorRequest;
import static br.com.akross.akrossapi.mock.MockUtils.getCompany;
import static br.com.akross.akrossapi.mock.MockUtils.getCompanyRequest;
import static br.com.akross.akrossapi.mock.MockUtils.getSquad;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.akross.akrossapi.controllers.response.CompanyResponse;
import br.com.akross.akrossapi.exceptions.ResourceAlreadyRegisteredException;
import br.com.akross.akrossapi.models.Collaborator;
import br.com.akross.akrossapi.repositories.CollaboratorRepository;
import br.com.akross.akrossapi.repositories.CompanyRepository;
import br.com.akross.akrossapi.repositories.SquadRepository;
import br.com.akross.akrossapi.services.imp.CollaboratorServiceImp;
import br.com.akross.akrossapi.services.imp.CompanyServiceImp;
import br.com.akross.akrossapi.utils.Util;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class NestedTests {

  @BeforeAll
  static void beforeAll() {
    Mockito.mockStatic(Util.class);
    when(Util.getExtension(any())).thenReturn(".png");
  }
  @Nested
  class CompanyServiceImpTest {

    @InjectMocks
    CompanyServiceImp companyServiceImp;
    @Mock
    CompanyRepository companyRepository;
    ModelMapper mapper = new ModelMapper();

    @BeforeEach
    void setUp() {

      companyServiceImp = new CompanyServiceImp(
        companyRepository,
        mapper
      );
    }

    @Test
    @DisplayName("Should create a company")
    void create() {
      when(companyRepository.save(any())).thenReturn(getCompany());
      CompanyResponse companyResponse = companyServiceImp.create(getCompanyRequest());
      assertThat(companyResponse).isNotNull();
    }

    @Test
    @DisplayName("Should not create a registered company")
    void createInvalidCompany() {
      when(companyRepository.existsByCnpj(getCompany().getCnpj())).thenReturn(true);
      assertThatThrownBy(() -> companyServiceImp.create(getCompanyRequest()))
        .hasMessage("CNPJ already registered")
        .isInstanceOf(ResourceAlreadyRegisteredException.class);
    }
  }

  @Nested
  class CollaboratorServiceImpTest {

    @InjectMocks
    CollaboratorServiceImp collaboratorServiceImp;
    @Mock
    CollaboratorRepository collaboratorRepository;
    @Mock
    CompanyRepository companyRepository;
    @Mock
    SquadRepository squadRepository;
    ModelMapper mapper = new ModelMapper();
    @Captor
    ArgumentCaptor<Collaborator> collaboratorArgumentCaptor;


    @BeforeEach
    void setUp() {
      collaboratorServiceImp = new CollaboratorServiceImp(
        collaboratorRepository,
        companyRepository,
        squadRepository,
        mapper
      );
    }

    @Test
    @DisplayName("Should hire a collaborator")
    void hireCollaborator() {
      when(companyRepository.findById(getCompany().getId())).thenReturn(Optional.of(getCompany()));
      when(squadRepository.findById(getSquad().getId())).thenReturn(Optional.of(getSquad()));
      when(collaboratorRepository.save(any())).thenReturn(getCollaborator());
      collaboratorServiceImp.hireCollaborator(
        getCollaboratorRequest(), getCompany().getId()
      );

      verify(collaboratorRepository).save(collaboratorArgumentCaptor.capture());
      Collaborator collaborator = collaboratorArgumentCaptor.getValue();
      assertThat(collaborator).isNotNull();
      assertThat(collaborator.getSquads().get(0)).isEqualTo(getSquad());
      assertThat(collaborator.getCompany()).isEqualTo(getCompany());
    }

    @Test
    @DisplayName("Should dismiss a collaborator")
    void dismissCollaborator() {
      when(collaboratorRepository.findById(getCollaborator().getId())).thenReturn(Optional.of(getCollaborator()));

      collaboratorServiceImp.dismissCollaborator(getCollaborator().getId());

      verify(collaboratorRepository).save(collaboratorArgumentCaptor.capture());
      Collaborator collaborator = collaboratorArgumentCaptor.getValue();
      assertThat(collaborator).isNotNull();
      assertThat(collaborator.getSquads()).isEmpty();
      assertThat(collaborator.getCompany()).isNull();
    }
  }
}
