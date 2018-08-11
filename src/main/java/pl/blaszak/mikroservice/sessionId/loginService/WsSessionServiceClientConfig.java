package pl.blaszak.mikroservice.sessionId.loginService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import pl.blaszak.mikroservice.sessionId.loginService.service.SessionService;

@Configuration
public class WsSessionServiceClientConfig {

    @Bean
    public Jaxb2Marshaller sessionServiceMarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("session.xsd");
        marshaller.setContextPath("pl.blaszak.microservice.session");
        return marshaller;
    }

    @Bean
    public SessionService getSessionService(Jaxb2Marshaller sessionServiceMarshaller, pl.blaszak.microservice.session.ObjectFactory sessionObjectFactory) {
        SessionService sessionService = new SessionService(sessionObjectFactory);
        sessionService.setDefaultUri("http://localhost:8081/ws");
        sessionService.setMarshaller(sessionServiceMarshaller);
        sessionService.setUnmarshaller(sessionServiceMarshaller);
        return sessionService;
    }
}
