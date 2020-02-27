package com.salesbox.annotation;

import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 5/8/14
 * Time: 11:31 AM
 */
@Documented
@Target( { ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface OrikaMapper
{
    Class mapClass();
    String[] excludes() default  {};
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
