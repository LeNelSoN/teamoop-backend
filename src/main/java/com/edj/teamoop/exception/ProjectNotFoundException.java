package com.edj.teamoop.exception;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(String message) {
        super(message);
    }
    public ProjectNotFoundException(Long id) {
        super(String.format("The project with ID %s does not exist.", id));
    }
}
