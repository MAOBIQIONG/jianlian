package com.fh.util;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

/** 
 *  
 * @author zandezhang
 * @since 2017.01.17
 */  
@Target({ElementType.PARAMETER, ElementType.METHOD})  
@Retention(RetentionPolicy.RUNTIME)  
@Documented 
public @interface SystemLog {
    String module()  default "";  
    String methods()  default ""; 
    String type()  default ""; 
}
