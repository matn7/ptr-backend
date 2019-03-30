package com.pandatronik.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, TYPE })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = ImportantEntityUniqueValidator.class)
public @interface ImportantEntityUnique {
    String message() default "{important.unique}";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
