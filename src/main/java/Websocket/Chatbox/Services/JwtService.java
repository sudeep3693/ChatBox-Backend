package Websocket.Chatbox.Services;


import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import Websocket.Chatbox.Modals.User;

@Service
public class JwtService {

private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

private final JwtParser jwtParser = Jwts.parser().verifyWith((SecretKey) key).build();

public String generateJwt(User user){

    return Jwts.builder()
            .subject(user.getUserName())
            .issuer("Chat Application")
            .expiration(new Date(System.currentTimeMillis()+1000*60*60*24*2))//For 2 days of jwt verification
            .issuedAt(new Date((System.currentTimeMillis())))
            .signWith(key)
            .compact();
}

public String getUsernameFromJwt(String token){
try{
    return jwtParser.parseClaimsJwt(token).getPayload().getSubject();

}
catch (Exception e){

    System.out.println(e+" error");
    return null;
}
}

    private Date extractExpiration(String token) {
        return
                jwtParser.parseClaimsJwt(token).getPayload().getExpiration();
    }



    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token, String username) {
        return (username.equals(getUsernameFromJwt(token)) && !isTokenExpired(token));
    }


}
