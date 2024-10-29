package br.com.freelance_management_api.controller.excetion;


public class ControllerNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ControllerNotFoundException(String message) {
        super(message);
    }
}
