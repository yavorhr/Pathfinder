package com.example.pathfinder.model.view;

public class PageViewDto {
  private String path;
  private Long views;
  private double percentage;

  public PageViewDto() {
  }

  public PageViewDto(String value, long longValue) {
    this.path = value;
    this.views = longValue;
  }

  public double getPercentage() {
    return percentage;
  }

  public String getPath() {
    return path;
  }

  public Long getViews() {
    return views;
  }

  public PageViewDto setPath(String path) {
    this.path = path;
    return this;
  }

  public PageViewDto setViews(Long views) {
    this.views = views;
    return this;
  }

  public PageViewDto setPercentage(double percentage) {
    this.percentage = percentage;
    return this;
  }
}
