package com.example.pathfinder.web;

import com.example.pathfinder.model.view.PageViewDto;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;
import java.util.Set;

@Controller
public class PageViewsController {
  private final ZSetOperations<String, String> zset;
  private final StringRedisTemplate redis;

  public PageViewsController(StringRedisTemplate redis, StringRedisTemplate redis1) {
    this.zset = redis.opsForZSet();
    this.redis = redis1;
  }

  @GetMapping("/admin/statistics")
  public String mostViewed(Model model) {
    Set<ZSetOperations.TypedTuple<String>> entries =
            zset.reverseRangeWithScores("views:sorted", 0, 9);

    List<PageViewDto> topPages = entries.stream()
            .map(e -> new PageViewDto(e.getValue(), e.getScore().longValue()))
            .toList();

    // Prepare raw lists for the chart
    List<String> pageLabels = topPages.stream()
            .map(PageViewDto::getPath)
            .toList();

    List<Long> pageViews = topPages.stream()
            .map(PageViewDto::getViews)
            .toList();

    model.addAttribute("topPages", topPages);
    model.addAttribute("chartLabels", pageLabels);
    model.addAttribute("chartData", pageViews);

    return "most-viewed";
  }

  @PostMapping("/admin/statistics/reset")
  public String resetStats() {
    redis.delete("views:sorted");

    Set<String> keys = redis.keys("views:*");
    if (keys != null) {
      redis.delete(keys);
    }

    return "redirect:/admin/statistics";
  }
}
