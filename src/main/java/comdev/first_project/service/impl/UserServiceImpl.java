package comdev.first_project.service.impl;

import comdev.first_project.constant.SystemConstant.*;
import comdev.first_project.dto.UserDto;
import comdev.first_project.exception.ErrorMessages;
import comdev.first_project.exception.NotFoundException;
import comdev.first_project.model.Provider;
import comdev.first_project.entity.User;
import comdev.first_project.repository.UserRepository;
import comdev.first_project.service.JwtService;
import comdev.first_project.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    @Lazy
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService,
                           AuthenticationManager authenticationManager){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDto findByUserName(String userName) {
        User user = this.findUserEntityByUserName(userName) ;
        return new UserDto(user);
    }

    @Override
    public void registerUser(UserDto registerUser) {
        //Todo: Validate information of User
        User user = User.builder()
                            .id(UUID.randomUUID())
                            .userName(registerUser.getUserName())
                            .passWord(this.passwordEncoder.encode(registerUser.getPassWord()))
                            .email(registerUser.getEmail())
                            .fullName(registerUser.getFullName())
                            .gender(registerUser.getGender())
                            .provider(Provider.LOCAL)
                            .build();
        this.userRepository.save(user);
        logger.info(LOG_MESSAGE.REGISTER_USER(registerUser.getUserName()));
    }

    @Override
    public UUID processOAuthAfterLogin(User loggedUser) {
        Optional<User> findByEmail = this.userRepository.findByEmail(loggedUser.getEmail());
        if(findByEmail.isPresent()){
            return findByEmail.get().getId();
        }

        loggedUser.setId(UUID.randomUUID());
        logger.info("new user sign up by oauth2 with email: " + loggedUser.getEmail());
        return this.userRepository.save(loggedUser).getId();
    }

    @Override
    public String authenticateUser(UserDto authenticateUser) {
        if(ObjectUtils.isEmpty(authenticateUser.getUserName()) || ObjectUtils.isEmpty(authenticateUser.getPassWord()))
            throw new NotFoundException(ErrorMessages.USER_NOT_FOUND);

        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticateUser.getUserName(), authenticateUser.getPassWord()));
        }catch (AuthenticationException e){
            logger.error("Authenticate exception" ,e);
        }
        return this.jwtService.generateToken(this.findUserEntityByUserName(authenticateUser.getUserName()));
    }

    private User findUserEntityByUserName(String userName){
        return this.userRepository
                .findByUserName(userName)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.USER_NOT_FOUND));
    }
}