package com.example.pathfinder.web;

import com.example.pathfinder.service.PictureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
  private final PictureService pictureService;

  public HomeController(PictureService pictureService) {
    this.pictureService = pictureService;
  }

  @GetMapping("/")
  public String homePage(Model model) {
    List<String> urls = this.pictureService.findPictureUrls();

    model.addAttribute("pictures", urls);
    return "index";
  }

  @GetMapping("/about")
  public String aboutPage() {
    return "about";
  }


}
