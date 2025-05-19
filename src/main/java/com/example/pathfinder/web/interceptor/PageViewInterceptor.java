package com.example.pathfinder.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class PageViewInterceptor implements HandlerInterceptor {
  private final StringRedisTemplate redis;

  public PageViewInterceptor(StringRedisTemplate redis) {
    this.redis = redis;
  }

  @Override
  public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object h) {

    String uri = req.getRequestURI();
    String key = "views:" + uri;

    // Skip static resources
    if (uri.matches(".*\\.(css|js|png|jpg|jpeg|gif|svg|ico|map)$")) {
      return true;
    }

    redis.opsForValue().increment(key);

    // ALSO update sorted set for top pages
    redis.opsForZSet().incrementScore("views:sorted", uri, 1);

    System.out.println("PageViewInterceptor hit URI=" + uri);
    return true;
  }
}
