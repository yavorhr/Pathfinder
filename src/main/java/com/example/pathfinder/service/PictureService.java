package com.example.pathfinder.service;

import com.example.pathfinder.model.service.PictureAddServiceModel;

import java.util.List;

public interface PictureService {
  List<String> findPictureUrls();

  void addPicture(PictureAddServiceModel uploaded);

  void deletePicture(String publicId);
}
