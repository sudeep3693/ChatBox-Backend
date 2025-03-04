package Websocket.Chatbox.Services;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import Websocket.Chatbox.Modals.User;

@Service
public class JwtService {

    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final JwtParser jwtParser = Jwts.parser().setSigningKey(key).build();

    public String generateJwt(User user) {
        return Jwts.builder()
                .setSubject(user.getUserName())
                .setIssuer("Chat Application")
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 2)) // 2 days
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(key)  // No need to specify algorithm again
                .compact();
    }

    public String getUsernameFromJwt(String token) {
        try {
            String username = jwtParser.parseClaimsJws(token).getBody().getSubject();
            System.out.println(username);
            return username;
        } catch (Exception e) {
            System.out.println(e + " error");
            return null;
        }
    }

    private Date extractExpiration(String token) {
        return jwtParser.parseClaimsJws(token).getBody().getExpiration();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token, String username) {
        return (username.equals(getUsernameFromJwt(token)) && !isTokenExpired(token));
    }
}
