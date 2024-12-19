package com.example.pathfinder.service.impl;

import com.example.pathfinder.model.entity.Comment;
import com.example.pathfinder.model.view.CommentViewModel;
import com.example.pathfinder.repository.CommentRepository;
import com.example.pathfinder.service.CommentService;
import com.example.pathfinder.service.RouteService;
import com.example.pathfinder.web.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
  private final CommentRepository commentRepository;
  private final RouteService routeService;
  private final ModelMapper modelMapper;

  public CommentServiceImpl(CommentRepository commentRepository, RouteService routeService, ModelMapper modelMapper) {
    this.commentRepository = commentRepository;
    this.routeService = routeService;
    this.modelMapper = modelMapper;
  }

  @Override
  @Transactional
  public List<CommentViewModel> getAllComments(Long routeId) {
    var routeOpt = routeService.
            findById(routeId);

    if (routeOpt.isEmpty()) {
      throw new ObjectNotFoundException("Route with id " + routeId + " was not found!");
    }

    return routeOpt
            .get()
            .getComments()
            .stream().map(this::mapToViewComment)
            .collect(Collectors.toList());
  }

  private CommentViewModel mapToViewComment(Comment commentEntity) {
    CommentViewModel commentViewModel = new CommentViewModel();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String formattedCreationDate = commentEntity.getCreated().format(formatter);

    commentViewModel.
            setCommentId(commentEntity.getId()).
            setApproved(commentEntity.isApproved()).
            setCreated(formattedCreationDate).
            setTextContent(commentEntity.getTextContent()).
            setAuthor(commentEntity.getAuthor().getFullName());

    return commentViewModel;
  }
}
