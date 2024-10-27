package com.example.pathfinder.web;

import com.example.pathfinder.model.binding.CommentBindingModel;
import com.example.pathfinder.model.service.CommentServiceModel;
import com.example.pathfinder.repository.CommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommentController {
  private final ModelMapper modelMapper;
  private final CommentRepository commentRepository;

  public CommentController(ModelMapper modelMapper, CommentRepository commentRepository) {
    this.modelMapper = modelMapper;
    this.commentRepository = commentRepository;
  }

  @PostMapping("/add/comment")
  public void addCommentToRoute(@RequestParam("id") Long id, CommentBindingModel bindingModel) {
    CommentServiceModel map = this.modelMapper.map(bindingModel, CommentServiceModel.class);
    System.out.println();
  }
}
