package br.com.akross.akrossapi.validations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ FIELD, METHOD, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AllowedContentTypesValidator.class)
@Documented
public @interface AllowedContentTypes {
	
	String message() default "{AllowedContentTypesValidator.message}";

	String[] value() default {};

	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
	
}