package com.example.pathfinder.web;

import com.example.pathfinder.model.binding.PictureAddBindingModel;
import com.example.pathfinder.model.service.PictureAddServiceModel;
import com.example.pathfinder.service.CloudinaryImage;
import com.example.pathfinder.service.CloudinaryService;
import com.example.pathfinder.service.PictureService;
import com.example.pathfinder.util.CurrentUser;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class PicturesController {
  private final PictureService pictureService;
  private final CloudinaryService cloudinaryService;
  private final CurrentUser currentUser;
  private final ModelMapper modelMapper;

  public PicturesController(PictureService pictureService, CloudinaryService cloudinaryService, CurrentUser currentUser, ModelMapper modelMapper) {
    this.pictureService = pictureService;
    this.cloudinaryService = cloudinaryService;
    this.currentUser = currentUser;
    this.modelMapper = modelMapper;
  }

  @PostMapping("/pictures/add")
  public String addPicture(PictureAddBindingModel bindingModel) throws IOException {

    CloudinaryImage uploaded = this.cloudinaryService.upload(bindingModel.getPicture());

    PictureAddServiceModel serviceModel =
            mapToPictureServiceModel(uploaded, bindingModel);

    this.pictureService.addPicture(serviceModel);

    return "redirect:/routes/details/" + bindingModel.getRouteId();
  }

  @Transactional
  @DeleteMapping("/pictures/delete")
  public String delete(@RequestParam("public_id") String publicId, @RequestParam("routeId") String routeId) {
    if (cloudinaryService.delete(publicId)) {
      pictureService.deletePicture(publicId);
    }

    return "redirect:/routes/details/" + routeId;
  }

  // Helpers
  private PictureAddServiceModel mapToPictureServiceModel(CloudinaryImage image, PictureAddBindingModel bindingModel) {
    PictureAddServiceModel serviceModel =
            this.modelMapper.map(bindingModel, PictureAddServiceModel.class);

    serviceModel.setAuthorId(this.currentUser.getId());
    serviceModel.setPublicId(image.getPublicId());
    serviceModel.setUrl(image.getUrl());

    return serviceModel;
  }
}
