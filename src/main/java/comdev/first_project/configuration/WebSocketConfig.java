package comdev.first_project.configuration;

import comdev.first_project.constant.SystemConstant.*;
import comdev.first_project.socket.HandShakeInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final HandShakeInterceptor handShakeInterceptor;
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint(WEB_SOCKET.END_POINT)
                .setAllowedOrigins("http://localhost:5500")
                .addInterceptors(handShakeInterceptor)
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes(WEB_SOCKET.APPLICATION_DESTINATION_PREFIXES);
        registry.enableSimpleBroker(WEB_SOCKET.SIMPLE_BROKER);
    }
}
