package com.ping;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class PingConfig {

	private static final String BUNDLE_NAME = "ping";
	   private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("ping");
	 
	   public static String getString(String key)
	   {
	     try
	     {
	       return RESOURCE_BUNDLE.getString(key); } catch (MissingResourceException e) {
	     }
	     return '!' + key + '!';
	   }
	
}
