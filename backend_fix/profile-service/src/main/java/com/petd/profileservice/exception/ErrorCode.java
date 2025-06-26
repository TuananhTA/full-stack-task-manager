package com.petd.profileservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
  UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),

  INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
  FORBIDDEN_ACTION(4003, "You do not have permission to perform this action", HttpStatus.FORBIDDEN),


  USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
  ACCOUNT_NOT_EXISTED(1003, "Account not existed", HttpStatus.BAD_REQUEST),
  ACCOUNT_IS_LOCKED(1004, "Account is locked", HttpStatus.CONFLICT),

  EMAIL_EXISTED(1009, "Email existed", HttpStatus.CONFLICT),
  // Email
  EMAIL_INVALID(10010, "Email is invalid", HttpStatus.BAD_REQUEST),
  EMAIL_REQUIRED(10011, "Email must not be blank", HttpStatus.BAD_REQUEST),
  // Password
  PASSWORD_REQUIRED(10012, "Password must not be blank", HttpStatus.BAD_REQUEST),
  // First Name
  FIRST_NAME_REQUIRED(10013, "First name must not be blank", HttpStatus.BAD_REQUEST),
  // Last Name
  LAST_NAME_REQUIRED(10014, "Last name must not be blank", HttpStatus.BAD_REQUEST),
  // Date of Birth
  DOB_REQUIRED(10015, "Date of birth must not be null", HttpStatus.BAD_REQUEST),
  DOB_PAST(10016, "Date of birth must be in the past", HttpStatus.BAD_REQUEST),
  // City
  CITY_REQUIRED(10017, "City must not be blank", HttpStatus.BAD_REQUEST),

  DEPARTMENT_NOTFOUND(10018, "Department not found", HttpStatus.BAD_REQUEST),


  TOKEN_EXPIRED(10018, "Token has expired", HttpStatus.BAD_REQUEST),
  TOKEN_INVALID(10019, "Token has invalid format", HttpStatus.BAD_REQUEST),

  ;

  private final int code;
  private final String message;
  private final HttpStatusCode statusCode;
  ErrorCode(int code, String message, HttpStatusCode statusCode) {
    this.code = code;
    this.message = message;
    this.statusCode = statusCode;
  }
}
