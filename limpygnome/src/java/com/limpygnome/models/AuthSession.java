package com.limpygnome.models;

/**
 * The data representing an authenticated session.
 * 
 * Non-JPA model.
 * 
 * @author limpygnome
 */
public class AuthSession
{
   private String username;
   
   public AuthSession(String username)
   {
       this.username = username;
   }
   
   public String getUsername()
   {
       return username;
   }
}
