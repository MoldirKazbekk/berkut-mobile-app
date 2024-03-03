package kz.sdu.edu.berkutapp.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-connection")
                .setAllowedOrigins("*")
                .setHandshakeHandler(new DefaultHandshakeHandler());
    }
    //1 - connection https://berkut-.../ws-register
    //2- subscribe  /user/1/child-geo

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/user"); //in-memory based message broker to carry the messages back to the client on dest prefix
//        registry.setUserDestinationPrefix("/user"); // user destination prefix
        registry.setApplicationDestinationPrefixes("/app"); // prefix for handling messages in message-mapping controller
    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        DefaultContentTypeResolver contentTypeResolver = new DefaultContentTypeResolver();
        contentTypeResolver.setDefaultMimeType(MimeTypeUtils.APPLICATION_JSON);
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(new ObjectMapper());
        converter.setContentTypeResolver(contentTypeResolver);
        messageConverters.add(converter);
        return false;
    }
}
