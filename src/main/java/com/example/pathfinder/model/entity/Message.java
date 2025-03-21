package com.example.pathfinder.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message extends BaseEntity {
  private LocalDateTime dateTime;
  private String textContent;
  private UserEntity author;
  private UserEntity recipient;

  public Message() {
  }

  @Column(name = "date_time", nullable = false)
  public LocalDateTime getDateTime() {
    return dateTime;
  }

  @Lob
  @Column(name = "text_content")
  public String getTextContent() {
    return textContent;
  }

  @ManyToOne
  public UserEntity getAuthor() {
    return author;
  }

  @ManyToOne
  public UserEntity getRecipient() {
    return recipient;
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  public void setTextContent(String textContent) {
    this.textContent = textContent;
  }

  public void setAuthor(UserEntity author) {
    this.author = author;
  }

  public void setRecipient(UserEntity recipient) {
    this.recipient = recipient;
  }

  @PrePersist
  private void setMessageDateTime() {
    this.dateTime = LocalDateTime.now();
  }
}
