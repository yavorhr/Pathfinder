package com.example.pathfinder.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;


@ControllerAdvice
public class ServerErrorGlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ModelAndView handleServerError(Exception e) {
    ModelAndView modelAndView = new ModelAndView();

    modelAndView.setViewName("error/500");
    modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

    return modelAndView;
  }
}
