package br.com.akross.akrossapi.controllers.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SquadResponse {

  private UUID id;
  private String name;
  @JsonIgnoreProperties({"squads", "company"})
  private List<CollaboratorResponse> collaborators;
  @JsonIgnoreProperties({"squads", "collaborators"})
  private CompanyResponse company;
}
