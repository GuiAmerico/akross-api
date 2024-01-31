package br.com.akross.akrossapi.controllers.response;

import br.com.akross.akrossapi.models.Address;
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
public class CompanyResponse {
  private UUID id;
  private String name;
  private String cnpj;
  private Photo photo;
  private Address address;
  @JsonIgnoreProperties({"company", "collaborators"})
  private List<SquadResponse> squads;
  @JsonIgnoreProperties({"squads", "company"})
  private List<CollaboratorResponse> collaborators;
}
