package com.petd.taskservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {

    INVALID_KEY(4000, "Uncategorized error", HttpStatus.BAD_REQUEST),
    NOT_NULL(4001, "This field is not null", HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_EXCEPTION(5000, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    FORBIDDEN_ACTION(4003, "You do not have permission to perform this action", HttpStatus.FORBIDDEN),

    INVALID_PRIORITY_NAME(1002, "The priority name must be greater than 6 characters", HttpStatus.CONFLICT ),
    PRIORITY_NAME_EXISTED(1001, "Priority name already exists", HttpStatus.CONFLICT),

    INVALID_TASK_NAME(2002, "The task name must be greater than 6 characters", HttpStatus.CONFLICT ),
    TASK_EXISTED(2003, "Task not exist", HttpStatus.NOT_FOUND ),
    USER_EXISTED(3002, "User not exist", HttpStatus.BAD_REQUEST),

    PROJECT_NOT_EXIST(4003, "Project not exist", HttpStatus.BAD_REQUEST),
    ASS_NOT_EXIST(5003, "Assign not exist", HttpStatus.BAD_REQUEST),

    NOT_FOUND_STATUS(1003, "not found status", HttpStatus.BAD_REQUEST),
    INVALID_STATUS_NAME(1002, "The status name must be greater than 6 characters", HttpStatus.CONFLICT ),
    STATUS_NAME_EXISTED(1001, "Status name already exists", HttpStatus.CONFLICT);




    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
