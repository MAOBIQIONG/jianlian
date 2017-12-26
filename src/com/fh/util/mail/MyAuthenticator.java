 package com.fh.util.mail;
 
 import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
 
 public class MyAuthenticator extends Authenticator{
	 
   String userName ="";
   String password ="";
 
   public MyAuthenticator() {
	   
   }
   
   public MyAuthenticator(String username, String password) {
     this.userName = username;
     this.password = password;
   }
   
   protected PasswordAuthentication getPasswordAuthentication() {
     return new PasswordAuthentication(this.userName, this.password);
   }
   
 }