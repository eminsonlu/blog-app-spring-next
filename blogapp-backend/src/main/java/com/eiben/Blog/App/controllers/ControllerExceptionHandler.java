package com.eiben.Blog.App.controllers;

import com.eiben.Blog.App.controllers.dto.response.AbstractBaseResponse;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({UsernameNotFoundException.class })
  public AbstractBaseResponse handleUserNotFoundException(UsernameNotFoundException ex, WebRequest request) {
    return new AbstractBaseResponse(null, ex.getMessage());
  }


}
