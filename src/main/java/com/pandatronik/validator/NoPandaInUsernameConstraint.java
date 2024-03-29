package com.pandatronik.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NoPandaInUsernameValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoPandaInUsernameConstraint {
    String message() default "Username cannot starts with panda";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
