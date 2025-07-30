package com.example.pathfinder.service.impl;

import com.example.pathfinder.model.entity.Picture;
import com.example.pathfinder.model.service.PictureAddServiceModel;
import com.example.pathfinder.repository.PictureRepository;
import com.example.pathfinder.service.PictureService;
import com.example.pathfinder.service.RouteService;
import com.example.pathfinder.service.UserService;
import com.example.pathfinder.web.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {
  private final UserService userService;
  private final RouteService routeService;
  private final PictureRepository pictureRepository;


  public PictureServiceImpl(UserService userService, RouteService routeService, PictureRepository pictureRepository) {
    this.userService = userService;
    this.routeService = routeService;
    this.pictureRepository = pictureRepository;
  }

  @Override
  public List<String> findPictureUrls() {
    return this.pictureRepository.getAllPictureUrls();
  }

  @Override
  public void addPicture(PictureAddServiceModel pictureAddServiceModel, Long id) {
    Picture picture = mapToPicture(pictureAddServiceModel, id);

    this.pictureRepository.save(picture);
  }

  @Override
  public void deletePicture(String publicId) {
    this.pictureRepository.deleteByPublicId(publicId);
  }

  // Helpers
  private Picture mapToPicture(PictureAddServiceModel pictureAddServiceModel, Long id) {
    Picture picture = new Picture();

    picture.setUrl(pictureAddServiceModel.getUrl());
    picture.setPublicId(pictureAddServiceModel.getPublicId());
    picture.setTitle(pictureAddServiceModel.getTitle());
    picture.setRoute(this.routeService
            .findRouteById(pictureAddServiceModel.getRouteId())
            .orElseThrow(() -> new ObjectNotFoundException("Route with id: " + id + " was not found!")));

    picture.setAuthor(this.userService
            .findById(id)
            .orElseThrow(() -> new ObjectNotFoundException("User with id: " + id + " was not found!")));

    return picture;
  }
}
