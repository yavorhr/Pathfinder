package com.example.pathfinder.web;

import com.example.pathfinder.model.binding.PictureAddBindingModel;
import com.example.pathfinder.model.service.PictureAddServiceModel;
import com.example.pathfinder.service.UserService;
import com.example.pathfinder.service.impl.principal.PathfinderUser;
import com.example.pathfinder.util.cloudinary.CloudinaryImage;
import com.example.pathfinder.util.cloudinary.CloudinaryService;
import com.example.pathfinder.service.PictureService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Controller
public class PicturesController {
  private final PictureService pictureService;
  private final CloudinaryService cloudinaryService;
  private final ModelMapper modelMapper;
  private final UserService userService;

  public PicturesController(PictureService pictureService, CloudinaryService cloudinaryService, ModelMapper modelMapper, UserService userService) {
    this.pictureService = pictureService;
    this.cloudinaryService = cloudinaryService;
    this.modelMapper = modelMapper;
    this.userService = userService;
  }

  @PreAuthorize("@routeServiceImpl.isOwnerOrIsAdmin(#principal.username, #bindingModel.routeId)")
  @PostMapping("/pictures/add")
  public String addPicture(PictureAddBindingModel bindingModel,
                           @AuthenticationPrincipal PathfinderUser principal) throws IOException {

    CloudinaryImage uploaded = this.cloudinaryService.upload(bindingModel.getPicture());

    PictureAddServiceModel serviceModel =
            mapToPictureServiceModel(uploaded, bindingModel);

    this.pictureService.addPicture(serviceModel, principal.getId());

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

  // User Profile Image Change
  @ResponseBody
  @PostMapping("/api/profile/image-upload")
  public ResponseEntity<Map<String, String>> uploadProfilePicture(@RequestParam("file") MultipartFile file,
  @AuthenticationPrincipal UserDetails principal) {
    try {
      CloudinaryImage uploadedImage = cloudinaryService.upload(file);

      this.userService.updateUsersProfilePicture(principal.getUsername(),uploadedImage.getUrl());

      return ResponseEntity.ok(Map.of(
              "url", uploadedImage.getUrl(),
              "publicId", uploadedImage.getPublicId()
      ));
    } catch (IOException e) {
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Upload failed"));
    }
  }

  // User Profile Old Image Delete after Change
  @DeleteMapping("/api/profile/image-delete")
  public ResponseEntity<?> deleteProfileImage(@RequestBody Map<String, String> request) {
    try {
      String publicId = request.get("publicId");
      cloudinaryService.delete(publicId);
      return ResponseEntity.ok(Map.of("message", "Image deleted successfully"));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to delete image"));
    }
  }

  // Helpers
  private PictureAddServiceModel mapToPictureServiceModel(CloudinaryImage image, PictureAddBindingModel bindingModel) {
    PictureAddServiceModel serviceModel =
            this.modelMapper.map(bindingModel, PictureAddServiceModel.class);

/*    serviceModel.setAuthorId(this.currentUser.getId());*/
    serviceModel.setPublicId(image.getPublicId());
    serviceModel.setUrl(image.getUrl());

    return serviceModel;
  }
}
