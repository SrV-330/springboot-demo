package com.springboot.demo.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Target(ElementType.METHOD)
public @interface TestAnno {
    String value() default "TEST ANNOTATION";
}
