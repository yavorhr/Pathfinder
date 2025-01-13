package com.example.pathfinder.service.impl;

import com.example.pathfinder.model.entity.Comment;
import com.example.pathfinder.model.service.CommentServiceModel;
import com.example.pathfinder.model.view.CommentViewModel;
import com.example.pathfinder.repository.CommentRepository;
import com.example.pathfinder.service.CommentService;
import com.example.pathfinder.service.RouteService;
import com.example.pathfinder.service.UserService;
import com.example.pathfinder.web.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
  private final CommentRepository commentRepository;
  private final RouteService routeService;
  private final UserService userService;


  public CommentServiceImpl(CommentRepository commentRepository, RouteService routeService, UserService userService) {
    this.commentRepository = commentRepository;
    this.routeService = routeService;
    this.userService = userService;
  }

  @Override
  @Transactional
  public List<CommentViewModel> getAllComments(Long routeId) {
    var routeOpt = routeService.
            findRouteById(routeId);

    if (routeOpt.isEmpty()) {
      throw new ObjectNotFoundException("Route with id " + routeId + " was not found!");
    }

    return routeOpt
            .get()
            .getComments()
            .stream().map(this::mapToViewComment)
            .collect(Collectors.toList());
  }

  @Override
  public CommentViewModel createComment(CommentServiceModel serviceModel) {
    //TODO - add NullPointerException Handler
    Objects.requireNonNull(serviceModel.getCreatorEmail());

    var route = routeService.
            findRouteById(serviceModel.getRouteId()).
            orElseThrow(() -> new ObjectNotFoundException("Route with id " + serviceModel.getRouteId() + " not found!"));

    var author = userService.
            findByEmail(serviceModel.getCreatorEmail()).
            orElseThrow(() -> new ObjectNotFoundException("User with email " + serviceModel.getCreatorEmail() + " not found!"));

    Comment newComment = new Comment();

    newComment.setApproved(true);
    //TODO: Admin to approve comment
    newComment.setTextContent(serviceModel.getMessage());
    newComment.setRoute(route);
    newComment.setAuthor(author);

    Comment savedComment = commentRepository.save(newComment);

    return mapToViewComment(savedComment);
  }

  // Helpers
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
