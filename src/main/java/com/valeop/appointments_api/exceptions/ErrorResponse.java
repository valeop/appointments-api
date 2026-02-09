package com.valeop.appointments_api.exceptions;

import java.time.LocalDateTime;

public class ErrorResponse {
    private String message;
    private int statusCode;
    private LocalDateTime timestamp;
    private String errorDetails;

    public ErrorResponse(String message, int statusCode, LocalDateTime timestamp, String errorDetails) {
        this.message = message;
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.errorDetails = errorDetails;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(String errorDetails) {
        this.errorDetails = errorDetails;
    }
}
