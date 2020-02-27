package com.kingwsi.bs.util.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description: <br>
 * Comments Name: Debug<br>
 * Date: 2019/7/12 11:46<br>
 * Author: wangshu<br>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Debug {
    boolean security() default true;
}
