package pl.blaszak.mikroservice.sessionId.loginService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pl.blaszak.microservice.login.LoginRequest;
import pl.blaszak.microservice.login.LoginResponse;
import pl.blaszak.microservice.login.ObjectFactory;
import pl.blaszak.mikroservice.sessionId.loginService.model.LoginServiceResponse;
import pl.blaszak.mikroservice.sessionId.loginService.service.LoginService;
import pl.blaszak.mikroservice.sessionId.loginService.util.LoginServiceResponseToLoginResponseMapper;

@Endpoint
public class LoginEndpoint {

    public static final String NAMESPACE_URI = "http://microservice.blaszak.pl/login";
    private static final Logger LOGGER = Logger.getLogger(LoginEndpoint.class);

    private final ObjectFactory loginApiObjFactory;
    private final LoginService loginService;
    private final LoginServiceResponseToLoginResponseMapper mapper;

    @Autowired
    public LoginEndpoint(ObjectFactory loginApiObjFactory, LoginService loginService, LoginServiceResponseToLoginResponseMapper mapper) {
        this.loginApiObjFactory = loginApiObjFactory;
        this.loginService = loginService;
        this.mapper = mapper;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "loginRequest")
    @ResponsePayload
    public LoginResponse login(@RequestPayload LoginRequest loginRequest) {
        try {
            LoginServiceResponse loginServiceResponse = loginService.login(loginRequest.getLoginName(), loginRequest.getPassword());
            return mapper.map(loginServiceResponse);
        } catch (Exception e) {
            LOGGER.error("LoginService Endpoint error:", e);
            LoginResponse loginResponse = loginApiObjFactory.createLoginResponse();
            loginResponse.setErrorMessage(e.getMessage());
            return loginResponse;
        }
    }
}
