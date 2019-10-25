package com.test.friends.application.api.error;

import com.test.friends.context.user.domain.exception.*;
import com.test.friends.context.user.domain.friendship.exception.UserFriendshipExistsException;
import com.test.friends.context.user.domain.request.exception.UserRequestAlreadyExistException;
import com.test.friends.context.user.domain.request.exception.UserRequestNotExistsException;
import com.test.friends.context.user.domain.request.exception.UserRequestSameUserException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  // 400
  @ExceptionHandler({
    UserNotExistException.class,
    UserInvalidCredentialsException.class,
    UsernameException.class,
    UserAlreadyExistsException.class,
    UserPasswordException.class,
    UserFriendshipExistsException.class,
    UserRequestNotExistsException.class,
    UserRequestAlreadyExistException.class,
    UserRequestSameUserException.class
  })
  public ResponseEntity<Object> handleException(final Exception ex) {
    if (ex instanceof UserNotExistException) {
      return new ResponseEntity<>(
          ex.getCause().getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
    if (ex instanceof UserInvalidCredentialsException) {
      return new ResponseEntity<>(
          ex.getCause().getMessage(), new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }
    return new ResponseEntity<>(
        ex.getCause().getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      final MissingServletRequestParameterException ex,
      final HttpHeaders headers,
      final HttpStatus status,
      final WebRequest request) {

    final String error = ex.getParameterName() + " parameter is missing";

    throw new BadRequestException(error);
  }
}
