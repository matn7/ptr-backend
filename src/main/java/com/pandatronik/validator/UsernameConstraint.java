package com.pandatronik.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameConstraint {

    String message() default "invalid username lol";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
