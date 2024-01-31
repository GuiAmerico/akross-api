package br.com.akross.akrossapi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_squad")
public class Squad extends AbstractEntity<UUID>{
  @Id
  @GeneratedValue
  private UUID id;
  private String name;
  @ManyToMany
  @JoinTable(name = "tb_squads_collaborators",
      joinColumns = @JoinColumn(name = "squad_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "collaborator_id", referencedColumnName = "id")
  )
  private List<Collaborator> collaborators = new ArrayList<>();
  @ManyToOne
  private Company company;

}
