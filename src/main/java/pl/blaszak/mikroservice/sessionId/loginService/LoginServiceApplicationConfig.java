package pl.blaszak.mikroservice.sessionId.loginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import pl.blaszak.mikroservice.sessionId.loginService.database.UsersRepository;
import pl.blaszak.mikroservice.sessionId.loginService.service.LoginService;
import pl.blaszak.mikroservice.sessionId.loginService.service.SessionService;
import pl.blaszak.mikroservice.sessionId.loginService.util.LoginServiceResponseToLoginResponseMapper;

@Configuration
public class LoginServiceApplicationConfig extends WsConfigurerAdapter {

        @Autowired
        private UsersRepository repository;

        @Bean(name = "loginObjectFactory")
        public pl.blaszak.microservice.login.ObjectFactory loginApiObjectFactory() {
                return new pl.blaszak.microservice.login.ObjectFactory();
        }

        @Bean(name = "sessionObjectFactory")
        public pl.blaszak.microservice.session.ObjectFactory sessionObjectFactory() { return new pl.blaszak.microservice.session.ObjectFactory(); }

        @Bean
        public LoginServiceResponseToLoginResponseMapper loginServiceResponseToLoginResponseMapper(pl.blaszak.microservice.login.ObjectFactory loginApiObjectFactory) {
                return new LoginServiceResponseToLoginResponseMapper(loginApiObjectFactory);
        }

        /*@Bean
        public SessionService getSessionService(pl.blaszak.microservice.session.ObjectFactory sessionObjectFactory) {
                return new SessionService(sessionObjectFactory);
        }*/

        @Bean
        public LoginService getLoginService(SessionService sessionService) {
                return new LoginService(repository, sessionService);
        }

}
