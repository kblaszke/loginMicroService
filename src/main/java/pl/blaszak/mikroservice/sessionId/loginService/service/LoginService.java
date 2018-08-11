package pl.blaszak.mikroservice.sessionId.loginService.service;

import org.springframework.util.StringUtils;
import pl.blaszak.mikroservice.sessionId.loginService.database.UsersRepository;
import pl.blaszak.mikroservice.sessionId.loginService.database.model.User;
import pl.blaszak.mikroservice.sessionId.loginService.model.LoginServiceResponse;

public class LoginService {

    private final UsersRepository repository;
    private final SessionService sessionService;

    public LoginService(UsersRepository repository, SessionService sessionService) {
        this.repository = repository;
        this.sessionService = sessionService;
    }

    public LoginServiceResponse login(String username, String password) throws Exception {
        if(StringUtils.isEmpty(username)) return LoginServiceResponse.emptyUsername();
        if(StringUtils.isEmpty(password)) return LoginServiceResponse.emptyPassword();
        User user = repository.findByName(username);
        if(user == null) return LoginServiceResponse.userNotFound();
        if(!password.equals(user.getPassword())) return LoginServiceResponse.wrongPassword();
        Long sessionId = sessionService.create().getSessionId();
        return LoginServiceResponse.withSessionId(sessionId);
    }

}
