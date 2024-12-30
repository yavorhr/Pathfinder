package com.example.pathfinder.repository;

import com.example.pathfinder.model.entity.Category;
import com.example.pathfinder.model.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

  Optional<Route> findByName(String name);

  Optional<List<Route>> findAllByCategories(Set<Category> categories);
}
