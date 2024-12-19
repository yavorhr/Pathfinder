package com.example.pathfinder.web;

import com.example.pathfinder.model.binding.CommentBindingModel;
import com.example.pathfinder.model.service.CommentServiceModel;
import com.example.pathfinder.model.view.CommentViewModel;
import com.example.pathfinder.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentRestController {
  private final ModelMapper modelMapper;
  private final CommentService commentService;

  public CommentRestController(ModelMapper modelMapper, CommentService commentService) {
    this.modelMapper = modelMapper;
    this.commentService = commentService;
  }

  @GetMapping("/api/{routeId}/comments")
  public ResponseEntity<List<CommentViewModel>> getComments(@PathVariable Long routeId) {
    return ResponseEntity.ok(commentService.getAllComments(routeId));
  }
}
