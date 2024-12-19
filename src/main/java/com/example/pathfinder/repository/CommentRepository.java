package com.example.pathfinder.repository;

import com.example.pathfinder.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

  List<Comment> findAllByRoute_Id(Long routeId);
}
