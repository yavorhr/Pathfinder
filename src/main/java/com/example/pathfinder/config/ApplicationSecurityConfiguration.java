package com.example.pathfinder.config;

import com.example.pathfinder.model.entity.enums.UserRoleEnum;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfiguration {
  private final UserDetailsService userDetailsService;
  private final CustomLoginFailureHandler failureHandler;
  private final CustomLoginSuccessHandler successHandler;
  private final PasswordEncoder passwordEncoder;

  public ApplicationSecurityConfiguration(UserDetailsService userDetailsService, CustomLoginFailureHandler failureHandler, CustomLoginSuccessHandler successHandler, PasswordEncoder passwordEncoder) {
    this.userDetailsService = userDetailsService;
    this.failureHandler = failureHandler;
    this.successHandler = successHandler;
    this.passwordEncoder = passwordEncoder;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(authz ->
            authz.
                    requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll().
                    requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll().
                    requestMatchers("/", "/about", "/users/login", "/users/register", "/logger/**", "/routes/details/**", "/api/*/comments").permitAll().
                    requestMatchers("/admin/**").hasRole(UserRoleEnum.ADMIN.name()).
                    requestMatchers("/**").authenticated())
            .formLogin(form -> form.
                    loginPage("/users/login").
                    usernameParameter("email").
                    passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
                    .failureHandler(failureHandler)
                    .successHandler(successHandler))
            .logout(logout -> logout.
                    logoutUrl("/users/logout").
                    logoutSuccessUrl("/").
                    invalidateHttpSession(true).
                    deleteCookies("JSESSIONID"))
            .securityContext(securityContext ->
                    securityContext.requireExplicitSave(false));

    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder);

    AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
    auth.authenticationProvider(authProvider);

    return auth.build();
  }
}
