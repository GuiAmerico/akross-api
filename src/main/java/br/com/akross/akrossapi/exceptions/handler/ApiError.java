package br.com.akross.akrossapi.exceptions.handler;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

  private String[] errors;
  private final Instant causedAt = Instant.now();

}
