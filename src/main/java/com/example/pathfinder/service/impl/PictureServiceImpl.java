package com.example.pathfinder.service.impl;

import com.example.pathfinder.model.entity.Picture;
import com.example.pathfinder.model.service.PictureAddServiceModel;
import com.example.pathfinder.repository.PictureRepository;
import com.example.pathfinder.service.PictureService;
import com.example.pathfinder.service.RouteService;
import com.example.pathfinder.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {
  private final UserService userService;
  private final RouteService routeService;
  private final PictureRepository pictureRepository;
  private final ModelMapper modelMapper;

  public PictureServiceImpl(UserService userService, RouteService routeService, PictureRepository pictureRepository, ModelMapper modelMapper) {
    this.userService = userService;
    this.routeService = routeService;
    this.pictureRepository = pictureRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public List<String> findPictureUrls() {
    return this.pictureRepository.getAllPictureUrls();
  }

  @Override
  public void addPicture(PictureAddServiceModel pictureAddServiceModel) {
    Picture picture = mapToPicture(pictureAddServiceModel);

    this.pictureRepository.save(picture);
  }

  @Override
  public void deletePicture(String publicId) {
    this.pictureRepository.deleteByPublicId(publicId);
  }

  // Helpers
  private Picture mapToPicture(PictureAddServiceModel pictureAddServiceModel) {
    Picture picture = new Picture();

    picture.setUrl(pictureAddServiceModel.getUrl());
    picture.setPublicId(pictureAddServiceModel.getPublicId());
    picture.setAuthor(this.userService.findById(pictureAddServiceModel.getAuthorId()).get());
    picture.setTitle(pictureAddServiceModel.getTitle());
    picture.setRoute(this.routeService.findRouteById(pictureAddServiceModel.getRouteId()).get());

    return picture;
  }
}
