package br.com.akross.akrossapi.models;

import br.com.akross.akrossapi.validations.AllowedContentTypes;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class Photo {

  private String photoFilePath;
  private String photoContentType;
}
