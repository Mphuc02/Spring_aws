package comdev.first_project.socket;

import comdev.first_project.constant.SystemConstant.*;
import comdev.first_project.dto.UserDto;
import comdev.first_project.entity.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WebSocketHandler {
    private final Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);
    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final Map<UUID, UserDto> listUserOnline;

    @EventListener
    public void handlerConnection(SessionConnectedEvent event){
        UserDto newOnlineUser = this.getUserFromEvent(event);
        this.listUserOnline.put(newOnlineUser.getId(), newOnlineUser);
        logger.info("User online: " + newOnlineUser.getId());
        this.publishData(newOnlineUser, WEB_SOCKET.NEW_USER_ONLINE);
    }

    @EventListener
    public void handlerDisconnect(SessionDisconnectEvent event){
        UserDto offlineUser = this.getUserFromEvent(event);
        this.listUserOnline.remove(offlineUser.getId());
        logger.info("User offline: " + offlineUser.getId());
        this.publishData(offlineUser, WEB_SOCKET.USER_OFFLINE);
    }

    private UserDto getUserFromEvent(AbstractSubProtocolEvent event){
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) event.getUser();
        return new UserDto((User) (token != null ? token.getPrincipal() : null));
    }

    public void publishData(Object data, String topic) {
        this.simpMessageSendingOperations.convertAndSend(topic, data);
    }
}