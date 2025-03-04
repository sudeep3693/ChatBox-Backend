package Websocket.Chatbox.Configurations;


import Websocket.Chatbox.Interceptors.WebSocketInterceptor;
import Websocket.Chatbox.MyWebConfig;
import Websocket.Chatbox.Services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

@EnableWebSocket

@Configuration
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private JwtService jwtService;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        registry.addHandler(new MyWebConfig(),"/ws")
                .setAllowedOrigins("*")
                .addInterceptors((HandshakeInterceptor) new WebSocketInterceptor(jwtService));
    }
}
