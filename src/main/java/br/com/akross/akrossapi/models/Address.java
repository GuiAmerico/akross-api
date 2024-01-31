package br.com.akross.akrossapi.models;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class Address {

  @NotBlank(message = "StreetAddress is mandatory")
  private String streetAddress;
  @NotBlank(message = "City is mandatory")
  private String city;
  @NotBlank(message = "State is mandatory")
  private String state;
  @NotBlank(message = "PostalCode is mandatory")
  private String postalCode;
  private String complement;
  @NotNull(message = "Number is mandatory")
  @Positive(message = "Number must be positive")
  private int number;
}
