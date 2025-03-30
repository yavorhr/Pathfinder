package com.example.pathfinder.validation.update;

import com.example.pathfinder.validation.register.UniqueUsernameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueUsernameByUpdateValidator.class)
public @interface UniqueUsernameByUserUpdate {

  String message() default "Username is occupied!";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
