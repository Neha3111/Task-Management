package com.example.auth.controller;
import com.example.auth.entity.User;
import com.example.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins="*")
public class AuthController {
    @Autowired private UserRepository repo;

    @PostMapping("/signup")

    public ResponseEntity<?> signup(@RequestBody Map<String,String> body){
        String u = body.get("username");
         String p = body.get("password");
        if(u==null||p==null) return ResponseEntity.badRequest().body(Map.of("error","username and password required"));
        if(repo.findByUsername(u).isPresent()) return ResponseEntity.badRequest().body(Map.of("error","username exists"));
        User user = new User(); 
        user.setUsername(u); 
        user.setPassword(p); 
        repo.save(user);
        return ResponseEntity.ok(Map.of("status","ok"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String,String> body){
        String u = body.get("username"); 
        String p = body.get("password");
        return repo.findByUsername(u).map(user -> {
            if(user.getPassword().equals(p)) return ResponseEntity.ok(Map.of("status","ok"));
            else return ResponseEntity.status(401).body(Map.of("status","invalid"));
        }).orElse(ResponseEntity.status(401).body(Map.of("status","invalid")));
    }
}
