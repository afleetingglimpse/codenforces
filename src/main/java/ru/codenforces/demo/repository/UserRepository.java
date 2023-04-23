package ru.codenforces.demo.repository;

import org.springframework.stereotype.Component;
import ru.codenforces.demo.model.User;

@Component
public class UserRepository {

    private final User securityUser = new User("security", "security", "security");
    private final User techicalUser = new User("technical", "technical", "technical");

    public User findByName(String name) {
        if (name.equals(securityUser.getName()))
            return securityUser;
        if (name.equals(techicalUser.getName()))
            return techicalUser;
        return null;
    }

}
