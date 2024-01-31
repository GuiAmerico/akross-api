package br.com.akross.akrossapi.controllers.request;

import br.com.akross.akrossapi.models.Address;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCompanyRequest {

  private String name;
  @Valid
  private PhotoRequest photo;
  @Valid
  private Address address;
}
