package com.example.pathfinder.model.binding;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class NewCommentBindingModel {
  private String message;
  private Long parentCommentId;

  public NewCommentBindingModel setMessage(String message) {
    this.message = message;
    return this;
  }

  @NotBlank
  @Size(min=10)
  public String getMessage() {
    return message;
  }

  public Long getParentCommentId() {
    return parentCommentId;
  }

  public NewCommentBindingModel setParentCommentId(Long parentCommentId) {
    this.parentCommentId = parentCommentId;
    return this;
  }


}
