package Websocket.Chatbox.Controller;


import Websocket.Chatbox.Modals.User;
import Websocket.Chatbox.Services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
@CrossOrigin("*")
public class MessageController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user){

        log.info("register request received");
        return ResponseEntity.ok(String.valueOf(userService.registerUser(user)));

    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user){

        log.info("login detail received");
        return ResponseEntity.ok(userService.validateLoginDetail(user));
    }
}
