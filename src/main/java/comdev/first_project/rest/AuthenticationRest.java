package comdev.first_project.rest;

import comdev.first_project.constant.SystemConstant.*;
import comdev.first_project.dto.UserDto;
import comdev.first_project.response.ResponseCustom;
import comdev.first_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(AUTH.AUTH_URL)
@RequiredArgsConstructor
public class AuthenticationRest {
    private final UserService userService;
    private final Map<UUID, UserDto> listUserOnline;

    @GetMapping()
    public ResponseEntity<Object> getListUserOnline(){
        return ResponseEntity.ok(listUserOnline.values());
    }

    @PostMapping(AUTH.REGISTER_URL)
    public ResponseEntity<Object> register(@RequestBody UserDto registerUser){
        this.userService.registerUser(registerUser);
        return ResponseEntity.ok(new ResponseCustom<>());
    }

    @PostMapping(AUTH.AUTHENTICATE_URL)
    public ResponseEntity<Object> authenticate(@RequestBody UserDto authenticateUser){
        String jwt = this.userService.authenticateUser(authenticateUser);
        return ResponseEntity.ok(new ResponseCustom<>(jwt));
    }
}