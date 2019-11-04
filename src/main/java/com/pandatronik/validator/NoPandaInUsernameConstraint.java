package com.pandatronik.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NoPandaInUsernameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoPandaInUsernameConstraint {
    String message() default "Your username cannot starts with panda";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
