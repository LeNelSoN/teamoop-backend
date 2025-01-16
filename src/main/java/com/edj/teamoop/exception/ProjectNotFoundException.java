package com.edj.teamoop.exception;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(String message) {
        super(message);
    }
    public ProjectNotFoundException() {
        super("The project does not exist.");
    }
}
