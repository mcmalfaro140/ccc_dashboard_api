package com.ccc.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;

import com.ccc.api.model.User;
//import edu.csula.scicafe.model.User;
//import edu.csula.scicafe.util.JwtUtils;

@ControllerAdvice
public class JwtAdvice {

  @Autowired
  private JwtUtils jwtUtils;

  @ModelAttribute
  public User jwtToUser(@RequestHeader("Authorization") String token) {
    return token != null ? jwtUtils.toUser(token) : null;
  }
}
