package com.example.pathfinder.web.exception;

import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionsHandler {

  @ExceptionHandler(AccessDeniedException.class)
  public ModelAndView handleAccessDeniedException(ObjectNotFoundException e) {
    ModelAndView modelAndView = new ModelAndView();

    modelAndView.addObject("message", e.getMessage());
    modelAndView.setViewName("error/403");
    modelAndView.setStatus(HttpStatus.FORBIDDEN);

    return modelAndView;
  }

  @ExceptionHandler(ObjectNotFoundException.class)
  public ModelAndView handleObjectNotFoundException(ObjectNotFoundException e) {
    ModelAndView modelAndView = new ModelAndView();

    modelAndView.addObject("message", e.getMessage());
    modelAndView.setViewName("error/404");
    modelAndView.setStatus(HttpStatus.NOT_FOUND);

    return modelAndView;
  }

  @ExceptionHandler(Exception.class)
  public ModelAndView handleServerError(Exception e) {
    ModelAndView modelAndView = new ModelAndView();

    modelAndView.setViewName("error/500");
    modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

    return modelAndView;
  }
}
