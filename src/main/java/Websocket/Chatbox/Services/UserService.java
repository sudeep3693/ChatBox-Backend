package Websocket.Chatbox.Services;


import Websocket.Chatbox.Modals.User;
import Websocket.Chatbox.Repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtService jwtService;

    public User registerUser(User user){

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));


        log.info("success");

        return userRepo.save(user);
    }

    public String validateLoginDetail(User user){

        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword())
            );

            if (authentication.isAuthenticated()) {
                return jwtService.generateJwt(user);
            }
        } catch (Exception e) {
            log.error("Authentication failed: {}", e.getMessage());
        }
        return "Authentication failed";
    }
}
