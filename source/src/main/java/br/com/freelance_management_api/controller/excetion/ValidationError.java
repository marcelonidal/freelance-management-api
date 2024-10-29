package br.com.freelance_management_api.controller.excetion;

import java.time.Instant;
import java.util.Map;

public class ValidationError {

    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private Map<String, String> fieldErrors;

    public ValidationError(Instant timestamp, Integer status, String error, String message, Map<String, String> fieldErrors) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.fieldErrors = fieldErrors;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }
}
