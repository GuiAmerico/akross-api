package br.com.akross.akrossapi.controllers.request;

import br.com.akross.akrossapi.validations.PhoneNumber;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCollaboratorRequest {

  @NotBlank(message = "Name is mandatory")
  private String name;
  @NotBlank(message = "Email is mandatory")
  @Email(message = "Email must be valid")
  private String email;
  @PhoneNumber(message = "Phone is invalid")
  @NotBlank(message = "Phone is mandatory")
  private String phone;
  @CPF(message = "CPF is invalid")
  @NotBlank(message = "CPF is mandatory")
  private String cpf;
  @NotNull(message = "Photo is mandatory")
  @Valid
  private PhotoRequest photo;
  private List<UUID> allocatedIn;
}
