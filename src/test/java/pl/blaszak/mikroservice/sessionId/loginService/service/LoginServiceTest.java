package pl.blaszak.mikroservice.sessionId.loginService.service;

import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.blaszak.microservice.session.CreateSessionResponse;
import pl.blaszak.mikroservice.sessionId.loginService.database.UsersRepository;
import pl.blaszak.mikroservice.sessionId.loginService.database.model.User;
import pl.blaszak.mikroservice.sessionId.loginService.model.LoginServiceResponse;
import pl.blaszak.mikroservice.sessionId.loginService.model.LoginServiceResponseState;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest implements WithAssertions {

    @Mock
    private UsersRepository userRepository;

    @Mock
    private SessionService sessionService;

    private LoginService loginService;

    @Before
    public void setUp() {
        loginService = new LoginService(userRepository, sessionService);
    }

    @Test
    public void shouldReturnEmptyUsernameResponse() throws Exception {
        // given
        String username = null;
        String password = "Admin1";
        // when
        LoginServiceResponse loginResponse = loginService.login(username, password);
        // then
        assertThat(loginResponse.getState()).isEqualTo(LoginServiceResponseState.EMPTY_USERNAME);
        assertThat(loginResponse.getSessionId()).isNull();
    }

    @Test
    public void shouldReturnEmptyPassportResponse() throws Exception {
        // given
        String username = "admin";
        String password = null;
        // when
        LoginServiceResponse loginResponse = loginService.login(username, password);
        // then
        assertThat(loginResponse.getState()).isEqualTo(LoginServiceResponseState.EMPTY_PASSWORD);
        assertThat(loginResponse.getSessionId()).isNull();
    }


    @Test
    public void shouldReturnUserNotFoundResponseForUserOutOfRepository() throws Exception {
        // given
        String username = "admin";
        String password = "Admin1";
        given(userRepository.findByName(any())).willReturn(null);
        // when
        LoginServiceResponse loginResponse = loginService.login(username, password);
        // then
        assertThat(loginResponse.getState()).isEqualTo(LoginServiceResponseState.USER_NOT_FOUND);
        assertThat(loginResponse.getSessionId()).isNull();
    }


    @Test
    public void shouldReturnWrongPasswordResponseForWrongPassword() throws Exception {
        // given
        String username = "admin";
        String password = "Admin1";
        String sessionId = "123456";
        User user = new User();
        user.setName(username);
        user.setPassword(password);
        given(userRepository.findByName(username)).willReturn(user);
        // when
        LoginServiceResponse loginResponse = loginService.login(username, "654321");
        // then
        assertThat(loginResponse.getState()).isEqualTo(LoginServiceResponseState.WRONG_PASSWORD);
        assertThat(loginResponse.getSessionId()).isNull();
    }


    @Test
    public void shouldReturnSessionIdForUserInRepository() throws Exception {
        // given
        String username = "admin";
        String password = "Admin1";
        Long sessionId = 123456L;
        User user = new User();
        user.setName(username);
        user.setPassword(password);
        CreateSessionResponse response = new CreateSessionResponse();
        response.setSessionId(sessionId);
        given(userRepository.findByName(username)).willReturn(user);
        given(sessionService.create()).willReturn(response);
        // when
        LoginServiceResponse loginResponse = loginService.login(username, password);
        // then
        assertThat(loginResponse.getState()).isEqualTo(LoginServiceResponseState.OK);
        assertThat(loginResponse.getSessionId()).isEqualTo(sessionId);
    }


    @Test(expected = Exception.class)
    public void shouldThrowExceptionIfProblemsWithSessionService() throws Exception {
        // given
        String username = "admin";
        String password = "Admin1";
        User user = new User();
        user.setName(username);
        user.setPassword(password);
        given(userRepository.findByName(username)).willReturn(user);
        given(sessionService.create()).willThrow(new Exception());
        // when
        loginService.login(username, password);
        // then
    }

    @Test
    public void shouldThrowExceptionWithSessionServiceErrorMessage() throws Exception {
        // given
        String username = "admin";
        String password = "Admin1";
        String errorMessage = "Can not create session. SessionService Error";
        User user = new User();
        user.setName(username);
        user.setPassword(password);
        given(userRepository.findByName(username)).willReturn(user);
        given(sessionService.create()).willThrow(new Exception(errorMessage));
        // when
        Throwable throwable = catchThrowable(() -> loginService.login(username, password));
        // then
        assertThat(throwable).isInstanceOf(Exception.class);
        assertThat(throwable.getMessage()).isEqualTo(errorMessage);
    }
}