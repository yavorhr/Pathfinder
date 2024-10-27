package com.example.pathfinder.model.binding;

public class CommentBindingModel {
  private boolean approved;
  private String textContent;

  public CommentBindingModel() {
  }

  public boolean isApproved() {
    return approved;
  }

  public CommentBindingModel setApproved(boolean approved) {
    this.approved = approved;
    return this;
  }

  public String getTextContent() {
    return textContent;
  }

  public CommentBindingModel setTextContent(String textContent) {
    this.textContent = textContent;
    return this;
  }
}
