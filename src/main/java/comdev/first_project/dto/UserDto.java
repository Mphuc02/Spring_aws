package comdev.first_project.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import comdev.first_project.model.Provider;
import comdev.first_project.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.util.UUID;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto  {
    private UUID id;
    private String userName;
    private String passWord;
    private String confirmPassWord;
    private String email;
    private String fullName;
    private Integer gender;
    private Provider provider;

    public UserDto(User entity){
        if(entity != null){
            this.id = entity.getId();
            this.userName = entity.getUsername();
            this.email = entity.getEmail();
            this.fullName = entity.getFullName();
            this.gender = entity.getGender();
        }
    }
}