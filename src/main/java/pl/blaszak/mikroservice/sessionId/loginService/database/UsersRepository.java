package pl.blaszak.mikroservice.sessionId.loginService.database;

import org.springframework.data.repository.CrudRepository;
import pl.blaszak.mikroservice.sessionId.loginService.database.model.User;


public interface UsersRepository extends CrudRepository<User, Long> {
    User findByName(String userName);
}
