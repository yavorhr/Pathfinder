package com.example.pathfinder.web;

import com.example.pathfinder.model.binding.NewCommentBindingModel;
import com.example.pathfinder.model.service.CommentServiceModel;
import com.example.pathfinder.model.view.CommentViewModel;
import com.example.pathfinder.service.CommentService;
import com.example.pathfinder.validation.ApiError;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
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

  @PostMapping("/api/{routeId}/add-comment")
  public ResponseEntity<CommentViewModel> newComment(
          @AuthenticationPrincipal UserDetails principal,
          @PathVariable Long routeId,
          @RequestBody @Valid NewCommentBindingModel newCommentBindingModel) {

    CommentServiceModel serviceModel =
            modelMapper.map(newCommentBindingModel, CommentServiceModel.class);

    serviceModel.setCreatorEmail(principal.getUsername());
    serviceModel.setRouteId(routeId);

    CommentViewModel newComment =
            commentService.createComment(serviceModel);

    URI locationOfNewComment =
            URI.create(String.format("/api/%s/comments/%s", routeId, newComment.getCommentId()));

    return ResponseEntity.
            created(locationOfNewComment).
            body(newComment);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> onValidationFailure(MethodArgumentNotValidException exc) {
    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);

    exc.getFieldErrors()
            .forEach(fe ->
            apiError.addFieldWithError(fe.getField()));

    return ResponseEntity.badRequest().body(apiError);
  }
}
