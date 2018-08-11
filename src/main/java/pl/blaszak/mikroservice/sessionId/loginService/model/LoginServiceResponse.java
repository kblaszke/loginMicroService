package pl.blaszak.mikroservice.sessionId.loginService.model;

public class LoginServiceResponse {

    private final LoginServiceResponseState state;
    private final Long sessionId;

    private LoginServiceResponse(LoginServiceResponseState state, Long sessionId) {
        this.state = state;
        this.sessionId = sessionId;
    }

    public static LoginServiceResponse withSessionId(Long sessionId) {
        return new LoginServiceResponse(LoginServiceResponseState.OK, sessionId);
    }

    public static LoginServiceResponse emptyUsername() {
        return new LoginServiceResponse(LoginServiceResponseState.EMPTY_USERNAME, null);
    }

    public static LoginServiceResponse emptyPassword() {
        return new LoginServiceResponse(LoginServiceResponseState.EMPTY_PASSWORD, null);
    }

    public static LoginServiceResponse userNotFound() {
        return new LoginServiceResponse(LoginServiceResponseState.USER_NOT_FOUND, null);
    }

    public static LoginServiceResponse wrongPassword() {
        return new LoginServiceResponse(LoginServiceResponseState.WRONG_PASSWORD, null);
    }

    public LoginServiceResponseState getState() {
        return state;
    }

    public Long getSessionId() {
        return sessionId;
    }
}
