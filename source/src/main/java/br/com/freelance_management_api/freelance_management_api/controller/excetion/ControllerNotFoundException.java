package br.com.freelance_management_api.freelance_management_api.controller.excetion;


public class ControllerNotFoundException extends RuntimeException {
    public ControllerNotFoundException(String message) {

      super(message);
    }
}
