package by.russianzak.vktestproject.exceptions.handlers;

import by.russianzak.vktestproject.exceptions.WebError;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.UnknownHttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;


@ControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException e) {
    return new ResponseEntity<>(new WebError(HttpStatus.UNAUTHORIZED.value(), e.getMessage()),
        HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException e) {
    return new ResponseEntity<>(new WebError(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
        HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException e) {
    return new ResponseEntity<>(new WebError(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
    return new ResponseEntity<>(new WebError(HttpStatus.UNAUTHORIZED.value(), e.getMessage()),
        HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<?> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
    return new ResponseEntity<>(new WebError(HttpStatus.BAD_REQUEST.value(), ex.getMessage()),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleHttpMessageNotReadable(MethodArgumentNotValidException ex) {
    List<String> errors = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .toList();
    return new ResponseEntity<>(new WebError(HttpStatus.BAD_REQUEST.value(), errors.toString()),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<?> handleIllegalStateException(IllegalStateException ex) {
    return new ResponseEntity<>(new WebError(HttpStatus.BAD_REQUEST.value(), ex.getMessage()),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<?> handleNoHandlerFoundException(NoHandlerFoundException ex) {
    return new ResponseEntity<>(new WebError(HttpStatus.NOT_FOUND.value(), ex.getMessage()),
        HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<?> handleUsernameAlreadyExistsException(ResponseStatusException ex) {
    return new ResponseEntity<>(new WebError(ex.getRawStatusCode(), ex.getReason()),
        ex.getStatus());
  }

  @ExceptionHandler(HttpClientErrorException.class)
  public ResponseEntity<?> handleHttpClientErrorException(HttpClientErrorException ex) {
    return new ResponseEntity<>(new WebError(ex.getRawStatusCode(), ex.getMessage()), ex.getStatusCode());
  }

  @ExceptionHandler(HttpServerErrorException.class)
  public ResponseEntity<?> handleHttpServerErrorException(HttpServerErrorException ex) {
    return new ResponseEntity<>(new WebError(ex.getRawStatusCode(), ex.getMessage()), ex.getStatusCode());
  }

  @ExceptionHandler(UnknownHttpStatusCodeException.class)
  public ResponseEntity<?> handleUnknownHttpStatusCodeException(UnknownHttpStatusCodeException  ex) {
    return new ResponseEntity<>(new WebError(ex.getRawStatusCode(), ex.getMessage()), HttpStatus.NOT_FOUND);
  }

}

