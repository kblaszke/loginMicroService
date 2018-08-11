package pl.blaszak.mikroservice.sessionId.loginService.model;

public enum LoginServiceResponseState {

    OK("OK"),
    EMPTY_USERNAME("The user name is empty"),
    EMPTY_PASSWORD("The password is empty"),
    USER_NOT_FOUND("User not found"),
    WRONG_PASSWORD("Wrong password");

    private String message;

    LoginServiceResponseState(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
