package br.com.akross.akrossapi.exceptions.handler;

import br.com.akross.akrossapi.exceptions.ResourceAlreadyRegisteredException;
import br.com.akross.akrossapi.exceptions.ResourceNotFoundException;
import br.com.akross.akrossapi.exceptions.ResourceNotValidException;
import br.com.akross.akrossapi.exceptions.UploadPictureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiHandler {

  private static ApiError getApiError(Exception ex) {
    String[] errors = getErrors(ex);
    return new ApiError(errors);
  }

  private static String[] getErrors(Exception ex) {
    return new String[]{ex.getMessage()};
  }

  @ExceptionHandler(ResourceAlreadyRegisteredException.class)
  public ResponseEntity<ApiError> handleResourceAlreadyRegisteredException(
    ResourceAlreadyRegisteredException ex
  ) {
    ApiError apiError = getApiError(ex);
    return ResponseEntity
      .status(HttpStatus.CONFLICT)
      .body(apiError);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ApiError> handleResourceNotFoundException(
    ResourceNotFoundException ex
  ) {
    ApiError apiError = getApiError(ex);
    return ResponseEntity
      .status(HttpStatus.NOT_FOUND)
      .body(apiError);
  }

  @ExceptionHandler(ResourceNotValidException.class)
  public ResponseEntity<ApiError> handleResourceNotValidException(
    ResourceNotValidException ex
  ) {
    ApiError apiError = getApiError(ex);
    return ResponseEntity
      .status(HttpStatus.NOT_FOUND)
      .body(apiError);
  }

  @ExceptionHandler(UploadPictureException.class)
  public ResponseEntity<ApiError> handleResourceNotValidException(
    UploadPictureException ex
  ) {
    ApiError apiError = getApiError(ex);
    return ResponseEntity
      .status(HttpStatus.INTERNAL_SERVER_ERROR)
      .body(apiError);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<ApiError> handleMethodArgumentNotValid(
    MethodArgumentNotValidException ex) {
    String[] errors = ex.getBindingResult().getAllErrors().stream()
      .map(e -> ((FieldError) e).getField() + " -> " + e.getDefaultMessage())
      .toArray(String[]::new);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(errors));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleAllException(
    Exception ex
  ) {
    ApiError apiError = getApiError(ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
  }
}
