package com.example.pathfinder.web.exception;

import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class AccessDeniedGlobalExceptionHandler {

  @ExceptionHandler(AccessDeniedException.class)
  public ModelAndView handleObjectNotFoundException(ObjectNotFoundException e) {
    ModelAndView modelAndView = new ModelAndView();

    modelAndView.addObject("message", e.getMessage());
    modelAndView.setViewName("error/403");
    modelAndView.setStatus(HttpStatus.FORBIDDEN);

    return modelAndView;
  }
}

