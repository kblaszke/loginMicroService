package pl.blaszak.mikroservice.sessionId.loginService.util;

import pl.blaszak.microservice.login.LoginResponse;
import pl.blaszak.microservice.login.ObjectFactory;
import pl.blaszak.mikroservice.sessionId.loginService.model.LoginServiceResponse;
import pl.blaszak.mikroservice.sessionId.loginService.model.LoginServiceResponseState;

public class LoginServiceResponseToLoginResponseMapper {

    private final ObjectFactory loginApiObjFactory;

    public LoginServiceResponseToLoginResponseMapper(ObjectFactory loginApiObjFactory) {
        this.loginApiObjFactory = loginApiObjFactory;
    }

    public LoginResponse map(LoginServiceResponse serviceResponse) {
        LoginResponse response = loginApiObjFactory.createLoginResponse();
        response.setSessionId(serviceResponse.getSessionId());
        if(serviceResponse.getState() != LoginServiceResponseState.OK) {
            response.setErrorMessage(serviceResponse.getState().getMessage());
        }
        return response;
    }
}
