package com.example.pathfinder.util.cloudinary;

import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

  private static final String TEMP_FILE = "temp-file";
  private static final String URL = "url";
  private static final String PUBLIC_ID = "public_id";

  private final Cloudinary cloudinary;

  public CloudinaryServiceImpl(Cloudinary cloudinary) {
    this.cloudinary = cloudinary;
  }

  @Override
  public CloudinaryImage upload(MultipartFile multipartFile, String folder) throws IOException {

    File tempFile = File.createTempFile(TEMP_FILE, multipartFile.getOriginalFilename());

    multipartFile.transferTo(tempFile);

    try {

      Map<String, Object> uploadOptions = new HashMap<>();
      uploadOptions.put("folder", "pathfinder/" + folder);

      @SuppressWarnings("unchecked")
      Map<String, String> uploadResult = cloudinary.uploader().upload(tempFile, uploadOptions);

      String url = uploadResult.getOrDefault(URL,
          "https://i.pinimg.com/originals/c5/21/64/c52164749f7460c1ededf8992cd9a6ec.jpg");
      String publicId = uploadResult.getOrDefault(PUBLIC_ID, "");

      return new CloudinaryImage().
          setPublicId(publicId).
          setUrl(url);

    } finally {
      tempFile.delete();
    }
  }

  @Override
  public boolean delete(String publicId) {
    try {
      cloudinary.uploader().destroy(publicId, Map.of());
    } catch (IOException e) {
      return false;
    }
    return true;
  }
}
