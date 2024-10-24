package com.example.pathfinder.validation.route;

import com.example.pathfinder.validation.register.UniqueUsernameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueRouteNameValidator.class)
public @interface UniqueRouteName {

  String message() default "Route with this name already exists";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
