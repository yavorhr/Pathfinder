package com.example.pathfinder.util.cloudinary;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {

  CloudinaryImage upload(MultipartFile file) throws IOException;

  boolean delete(String publicId);

}
