package comdev.first_project.model;

import comdev.first_project.constant.SystemConstant.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import java.util.Collection;
import java.util.Map;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OAuth2UserCustom implements OAuth2User {
    private Map<String, Object> attributes;
    private OAuth2User oAuth2User;
    private Provider provider;
    public OAuth2UserCustom(OAuth2User oAuth2User, String registrationID){
        this.oAuth2User = oAuth2User;
        this.attributes = oAuth2User.getAttributes();
        this.provider = Provider.valueOf(registrationID.toUpperCase());
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.oAuth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return this.attributes.get(OAUTH2_USER.NAME).toString();
    }

    public String getEmail(){
        return this.attributes.get(OAUTH2_USER.EMAIL).toString();
    }
}