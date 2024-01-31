package br.com.akross.akrossapi.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_company")
public class Company extends AbstractEntity<UUID> {

  @Id
  @GeneratedValue
  private UUID id;
  private String name;
  private String cnpj;
  @Embedded
  private Photo photo;
  @Embedded
  private Address address;
  @OneToMany(cascade = CascadeType.ALL ,mappedBy = "company")
  private List<Squad> squads = new ArrayList<>();
  @OneToMany(cascade = CascadeType.ALL ,mappedBy = "company")
  private List<Collaborator> collaborators = new ArrayList<>();
}
