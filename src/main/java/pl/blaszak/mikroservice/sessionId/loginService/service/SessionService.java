package pl.blaszak.mikroservice.sessionId.loginService.service;

import org.springframework.util.StringUtils;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import pl.blaszak.microservice.session.CreateSessionRequest;
import pl.blaszak.microservice.session.CreateSessionResponse;
import pl.blaszak.microservice.session.ObjectFactory;

public class SessionService extends WebServiceGatewaySupport {


    public static final String ERROR_MESSAGE = "Can not create session. SessionService Error";
    public static final String ENDPOINT= "http://localhost:8081/ws";
    public static final String NAMESPACE = "http://microservice.blaszak.pl/session";

    private final ObjectFactory sessionObjectFactory;

    public SessionService(ObjectFactory sessionObjectFactory) {
        this.sessionObjectFactory = sessionObjectFactory;
    }


    public CreateSessionResponse create() throws Exception {
        CreateSessionRequest request = sessionObjectFactory.createCreateSessionRequest();
        CreateSessionResponse response = (CreateSessionResponse) getWebServiceTemplate()
                .marshalSendAndReceive(ENDPOINT, request, new SoapActionCallback(NAMESPACE));
        if(StringUtils.isEmpty(response.getSessionId())) throw new Exception(ERROR_MESSAGE);
        return response;
    }
}
