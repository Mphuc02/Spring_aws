package comdev.first_project.security;

import comdev.first_project.constant.SystemConstant.*;
import comdev.first_project.entity.User;
import comdev.first_project.exception.ErrorMessages;
import comdev.first_project.exception.NotFoundException;
import comdev.first_project.model.Provider;
import comdev.first_project.repository.UserRepository;
import comdev.first_project.service.JwtService;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String requestPath = request.getServletPath();
        if(requestPath.startsWith(AUTH.AUTH_URL)){
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt;
        final String id;

        if(requestPath.startsWith(WEB_SOCKET.END_POINT))
            jwt = this.getJwtFromCookie(request);
        else
            jwt = this.getJwtFromHeader(request);

        if(ObjectUtils.isEmpty(jwt)){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
             id = this.jwtService.extractID(jwt);
        }
        catch (MalformedJwtException e){
            logger.error("Jwt exception", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if(id != null){
            System.out.println("Lần 1: " + request.getServletPath());
            User user = this.userRepository.findById(UUID.fromString(id)).orElseThrow(() -> new NotFoundException(ErrorMessages.USER_NOT_FOUND));

            if(this.jwtService.isTokenValid(jwt, user)){
                //If the fields below have null values, it may cause an exception when hashing User
                if(!user.getProvider().equals(Provider.LOCAL)){
                    user.setUserName("");
                    user.setGender(0);
                    user.setPassWord("");
                }

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user,
                                                                              null,
                                                                                        user.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                filterChain.doFilter(request, response);
                return;
            }
        }

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }

    private String getJwtFromHeader(HttpServletRequest request){
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith(AUTH.BEARER)) {
            return null;
        }

        String jwt = authHeader.substring(AUTH.INDEX_OF_JWT);
        return jwt.equals("null") ? null : jwt;
    }

    private String getJwtFromCookie(HttpServletRequest request){
        Cookie[] cookiesOfRequest = request.getCookies();

        if(!ObjectUtils.isEmpty(cookiesOfRequest)){
            for(Cookie cookie: cookiesOfRequest){
                if(cookie.getName().equals("jwt")){
                    return cookie.getValue();
                }
            }
        }

        return null;
    }
}