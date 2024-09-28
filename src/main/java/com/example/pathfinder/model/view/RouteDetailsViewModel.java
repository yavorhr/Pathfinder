package com.example.pathfinder.model.view;

import com.example.pathfinder.model.entity.Category;
import com.example.pathfinder.model.entity.Picture;
import com.example.pathfinder.model.entity.enums.LevelEnum;

import java.util.Set;

public class RouteDetailsViewModel {
    private String description;
    private String gpxCoordinates;
    private LevelEnum level;
    private String name;
    private String videoUrl;
    private Set<Category> categories;
    private Set<Picture> pictures;
    private String authorFullName;

    public RouteDetailsViewModel() {
    }

    public String getAuthorFullName() {
        return authorFullName;
    }

    public String getDescription() {
        return description;
    }

    public RouteDetailsViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getGpxCoordinates() {
        return gpxCoordinates;
    }

    public RouteDetailsViewModel setGpxCoordinates(String gpxCoordinates) {
        this.gpxCoordinates = gpxCoordinates;
        return this;
    }

    public LevelEnum getLevel() {
        return level;
    }

    public RouteDetailsViewModel setLevel(LevelEnum level) {
        this.level = level;
        return this;
    }

    public String getName() {
        return name;
    }

    public RouteDetailsViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public RouteDetailsViewModel setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public RouteDetailsViewModel setCategories(Set<Category> categories) {
        this.categories = categories;
        return this;
    }

    public Set<Picture> getPictures() {
        return pictures;
    }

    public RouteDetailsViewModel setPictures(Set<Picture> pictures) {
        this.pictures = pictures;
        return this;
    }

    public RouteDetailsViewModel setAuthorFullName(String authorFullName) {
        this.authorFullName = authorFullName;
        return this;
    }
}
