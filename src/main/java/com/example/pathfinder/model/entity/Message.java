package com.example.pathfinder.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message extends BaseEntity {
  private LocalDateTime dateTime;
  private String textContent;
  private User author;
  private User recipient;

  public Message() {
  }

  @Column(name = "date_time", nullable = false)
  public LocalDateTime getDateTime() {
    return dateTime;
  }

  @Column(name = "text_content", columnDefinition = "LONGTEXT")
  public String getTextContent() {
    return textContent;
  }

  @ManyToOne
  public User getAuthor() {
    return author;
  }

  @ManyToOne
  public User getRecipient() {
    return recipient;
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  public void setTextContent(String textContent) {
    this.textContent = textContent;
  }

  public void setAuthor(User author) {
    this.author = author;
  }

  public void setRecipient(User recipient) {
    this.recipient = recipient;
  }

  @PrePersist
  private void setMessageDateTime() {
    this.dateTime = LocalDateTime.now();
  }
}
