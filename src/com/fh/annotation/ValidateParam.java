package com.fh.annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;  
// The @Bind tag.  
@Target(ElementType.METHOD)  
public @interface ValidateParam {

	public String[] params();
	
}
