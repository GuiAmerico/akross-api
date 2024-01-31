package br.com.akross.akrossapi.models;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
@Table(name = "tb_collaborator")
public class Collaborator extends AbstractEntity<UUID> {

  @Id
  @GeneratedValue
  private UUID id;
  private String name;
  private String email;
  private String phone;
  private String cpf;
  @Embedded
  private Photo photo;
  @ManyToMany
  @JoinTable(name = "tb_squads_collaborators",
      joinColumns = @JoinColumn(name = "collaborator_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "squad_id", referencedColumnName = "id")
  )
  private List<Squad> squads = new ArrayList<>();
  @ManyToOne
  private Company company;

  public void addSquad(Squad squad) {
    this.squads.add(squad);
  }
}
