package br.com.akross.akrossapi.controllers.response;

import br.com.akross.akrossapi.models.Photo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollaboratorResponse {
  private UUID id;
  private String name;
  private String email;
  private String phone;
  private String cpf;
  private Photo photo;
  @JsonIgnoreProperties({"collaborators", "company"})
  private List<SquadResponse> squads;
  @JsonIgnoreProperties({"collaborators", "squads"})
  private CompanyResponse company;
}
