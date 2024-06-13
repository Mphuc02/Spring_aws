package comdev.first_project.rest;

import comdev.first_project.constant.SystemConstant.*;
import comdev.first_project.dto.UserDto;
import comdev.first_project.entity.User;
import comdev.first_project.exception.BadRequestException;
import comdev.first_project.exception.ErrorMessages;
import comdev.first_project.response.ResponseCustom;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(USER_API.URL)
public class UserRest {

    @GetMapping(USER_API.GET_AUTHENTICATED_USER)
    public ResponseEntity<Object> getAuthenticatedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated()){
            User authenticatedUser = (User) authentication.getPrincipal();
            return ResponseEntity.ok(new ResponseCustom<>(UserDto.builder()
                                    .id(authenticatedUser.getId())
                                    .fullName(authenticatedUser.getFullName())
                                    .build()
                                ));
        }

        throw new BadRequestException(ErrorMessages.USER_NOT_AUTHENTICATED);
    }
}