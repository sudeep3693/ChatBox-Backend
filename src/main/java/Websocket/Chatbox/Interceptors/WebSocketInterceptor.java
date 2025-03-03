package Websocket.Chatbox.Interceptors;

import Websocket.Chatbox.Services.JwtService;
import jakarta.servlet.http.HttpServletRequest;

public class WebSocketInterceptor {

    private JwtService jwtService;

    public WebSocketInterceptor(JwtService jwtService){
        this.jwtService = jwtService;
    }

    public void beforeHandShake(HttpServletRequest request)
}
