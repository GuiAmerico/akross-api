package br.com.akross.akrossapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceAlreadyRegisteredException extends RuntimeException {

  private static final long serialVersionUID = 1L;


  public ResourceAlreadyRegisteredException(String message) {
    super(message);
  }
}