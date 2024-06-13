package comdev.first_project.service;

import comdev.first_project.dto.UserDto;
import comdev.first_project.entity.User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface UserService {
    UserDto findByUserName(String userName);
    void registerUser(UserDto registerUser);
    UUID processOAuthAfterLogin(User loggedUser);
    String authenticateUser(UserDto authenticateUser);
}