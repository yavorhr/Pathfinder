package com.example.pathfinder.web.exception;

import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ObjectNotFoundGlobalExceptionHandler {

  @ExceptionHandler(ObjectNotFoundException.class)
  public ModelAndView handleObjectNotFoundException(ObjectNotFoundException e) {
    ModelAndView modelAndView = new ModelAndView();

    modelAndView.addObject("message", e.getMessage());
    modelAndView.setViewName("error/404");
    modelAndView.setStatus(HttpStatus.NOT_FOUND);

    return modelAndView;
  }
}

