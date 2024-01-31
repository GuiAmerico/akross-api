package br.com.akross.akrossapi.controllers.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SquadRequest {

  @NotBlank(message = "Name is mandatory")
  private String name;
}
