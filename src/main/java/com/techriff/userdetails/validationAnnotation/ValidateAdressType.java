package com.techriff.userdetails.validationAnnotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Target({ElementType.METHOD,ElementType.FIELD,ElementType.CONSTRUCTOR,ElementType.PARAMETER,ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = AddressTypeValidator.class)
public @interface ValidateAdressType {

    public String message() default "invalid addressTypeId ";
    Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
    
}
