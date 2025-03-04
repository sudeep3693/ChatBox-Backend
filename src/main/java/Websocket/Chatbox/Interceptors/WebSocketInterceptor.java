package Websocket.Chatbox.Interceptors;

import Websocket.Chatbox.Services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

public class WebSocketInterceptor implements HandshakeInterceptor {

    private final JwtService jwtService;

    public WebSocketInterceptor(JwtService jwtService){
        this.jwtService = jwtService;
    }



    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) {
        if (request instanceof ServletServerHttpRequest) {
            HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
            String token = servletRequest.getHeader("token");

            System.out.println("token received is: "+ token);
            if (token != null) {
                if (jwtService.validateToken(token, jwtService.getUsernameFromJwt(token))) {
                    attributes.put("user", jwtService.getUsernameFromJwt(token));
                    System.out.println("auth success");
                    return true; // Allow WebSocket connection
                }
                else{
                    System.out.println("auth failed");
                }
            }
        }
        response.setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
        System.out.println("unauthorized");
        return false; // Reject connection
    }



    @Override
    public void afterHandshake(org.springframework.http.server.ServerHttpRequest request, org.springframework.http.server.ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
