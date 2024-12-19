package com.example.pathfinder.service;

import com.example.pathfinder.model.service.CommentServiceModel;
import com.example.pathfinder.model.view.CommentViewModel;

import java.util.List;

public interface CommentService {
  List<CommentViewModel> getAllComments(Long routeId);

  CommentViewModel createComment(CommentServiceModel serviceModel);
}
