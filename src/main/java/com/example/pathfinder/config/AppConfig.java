package com.example.pathfinder.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import java.util.Map;

@Configuration
@EnableTransactionManagement
public class AppConfig {

  private final CloudinaryConfig config;

  public AppConfig(CloudinaryConfig config) {
    this.config = config;
  }

  @Bean
  public Cloudinary cloudinary() {
    return new Cloudinary(
        Map.of(
            "cloud_name", config.getCloudName(),
            "api_key", config.getApiKey(),
            "api_secret", config.getApiSecret()
        )
    );
  }

  @Bean
  public LettuceConnectionFactory redisConnectionFactory(
          RedisStandaloneConfiguration cfg) {
    return new LettuceConnectionFactory(cfg);
  }

  @Bean
  public RedisStandaloneConfiguration redisStandaloneConfiguration() {
    return new RedisStandaloneConfiguration("localhost", 6379);
  }

  @Bean
  public StringRedisTemplate stringRedisTemplate(
          LettuceConnectionFactory connectionFactory) {
    return new StringRedisTemplate(connectionFactory);
  }
}
