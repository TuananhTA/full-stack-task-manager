package com.petd.profileservice.customSercurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

public class PasswordUtil {
  private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  public static String hashPassword(String rawPassword) {
    return encoder.encode(rawPassword);
  }
  public static boolean verifyPassword(String rawPassword, String encodedPassword) {
    return encoder.matches(rawPassword, encodedPassword);
  }
}