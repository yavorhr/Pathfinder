package com.example.pathfinder.web;

import com.example.pathfinder.model.view.PageViewDto;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Set;

@Controller
public class PageViewsController {
  private final ZSetOperations<String, String> zset;

  public PageViewsController(StringRedisTemplate redis) {
    this.zset = redis.opsForZSet();
  }

  @GetMapping("/admin/statistics")
  public String mostViewed(Model model) {

    Set<ZSetOperations.TypedTuple<String>> entries =
            zset.reverseRangeWithScores("views:sorted", 0, 9);

    List<PageViewDto> topPages = entries.stream()
            .map(e -> new PageViewDto(e.getValue(), e.getScore().longValue()))
            .toList();

    System.out.println(topPages);

    model.addAttribute("topPages", topPages);

    return "most-viewed";
  }
}
