package br.com.akross.akrossapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UploadPhotoException extends RuntimeException {

  private static final long serialVersionUID = 1L;


  public UploadPhotoException(String message) {
    super(message);
  }
}