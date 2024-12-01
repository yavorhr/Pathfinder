package com.example.pathfinder.web;

import com.example.pathfinder.model.binding.PictureBindingModel;
import com.example.pathfinder.model.entity.Picture;
import com.example.pathfinder.repository.PictureRepository;
import com.example.pathfinder.service.CloudinaryImage;
import com.example.pathfinder.service.CloudinaryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class PicturesController {
  private final PictureRepository pictureRepository;
  private final CloudinaryService cloudinaryService;

  public PicturesController(PictureRepository pictureRepository, CloudinaryService cloudinaryService) {
    this.pictureRepository = pictureRepository;
    this.cloudinaryService = cloudinaryService;
  }

  @PostMapping("/pictures/add")
  public String addPicture(PictureBindingModel bindingModel) throws IOException {

    var picture = createPictureEntity(bindingModel.getPicture(),
            bindingModel.getTitle());

    pictureRepository.save(picture);

    return "redirect:/pictures/all";
  }

  private Picture createPictureEntity(MultipartFile file, String title) throws IOException {
    final CloudinaryImage uploaded = this.cloudinaryService.upload(file);

    return new Picture().
            setPublicId(uploaded.getPublicId()).
            setUrl(uploaded.getUrl())
            .setTitle(title);
  }
}
