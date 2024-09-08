package com.lioneats.lioneats_backend.exception;

public class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException(String resourceNotFound) {
        super(resourceNotFound);
    }
}

