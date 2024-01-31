package br.com.akross.akrossapi.controllers.request;

import br.com.akross.akrossapi.validations.PhoneNumber;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCollaboratorRequest {
  private String name;
  @Email(message = "Email must be valid")
  private String email;
  @PhoneNumber(message = "Phone is invalid")
  private String phone;
  @Valid
  private PhotoRequest photo;
  private List<UUID> allocatedIn;
}
