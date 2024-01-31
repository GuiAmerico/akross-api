package br.com.akross.akrossapi.controllers.request;

import br.com.akross.akrossapi.models.Address;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCompanyRequest {

  @NotBlank(message = "Name is mandatory")
  private String name;
  @NotBlank(message = "CNPJ is mandatory")
  @CNPJ(message = "CNPJ is invalid")
  private String cnpj;
  @NotNull(message = "Photo is mandatory")
  @Valid
  private PhotoRequest photo;
  @NotNull(message = "Address is mandatory")
  @Valid
  private Address address;
}
