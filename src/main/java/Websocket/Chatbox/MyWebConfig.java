package Websocket.Chatbox;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;


public class MyWebConfig extends TextWebSocketHandler {

    private ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        sessions.put(session.getId(), session);
        session.sendMessage(new TextMessage("New Connection Established: " + session.getId()));
        System.out.println("Connected: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        System.out.println("Received from " + session.getId() + ": " + message.getPayload());

        for (WebSocketSession s : sessions.values()) {
            if (s.isOpen()) {
                s.sendMessage(new TextMessage("User " + session.getId() + " says: " + message.getPayload()));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws IOException {
        sessions.remove(session.getId());
        System.out.println("Disconnected: " + session.getId());

        // Notify remaining users
        for (WebSocketSession s : sessions.values()) {
            if (s.isOpen()) {
                s.sendMessage(new TextMessage("User " + session.getId() + " left the chat"));
            }
        }
    }
}
