package br.com.akross.akrossapi.controllers.request;

import br.com.akross.akrossapi.validations.AllowedContentTypes;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PhotoRequest {
  @NotBlank(message = "Base64Photo is mandatory")
  private String base64Photo;
  @NotBlank(message = "PhotoContentType is mandatory")
  @AllowedContentTypes(value = {"image/jpg", "image/png"}, message = "The allowed content types are: ['image/jpg', 'image/png']")
  private String photoContentType;
}
