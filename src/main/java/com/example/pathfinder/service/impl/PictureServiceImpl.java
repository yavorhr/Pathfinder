package com.example.pathfinder.service.impl;

import com.example.pathfinder.model.entity.Picture;
import com.example.pathfinder.model.service.PictureAddServiceModel;
import com.example.pathfinder.repository.PictureRepository;
import com.example.pathfinder.service.PictureService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {
  private final PictureRepository pictureRepository;
  private final ModelMapper modelMapper;

  public PictureServiceImpl(PictureRepository pictureRepository, ModelMapper modelMapper) {
    this.pictureRepository = pictureRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public List<String> findPictureUrls() {
    return this.pictureRepository.getAllPictureUrls();
  }

  @Override
  public void addPicture(PictureAddServiceModel serviceModel) {
    Picture picture = this.pictureRepository.save(this.modelMapper.map(serviceModel, Picture.class));
    System.out.println();
  }
}
