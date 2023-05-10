package com.thermostat.user.service.api;


import java.util.List;
import com.thermostat.user.service.model.AppUser;
import com.thermostat.user.service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserResource {

    private final UserService userService;


    @GetMapping("/users")
      public ResponseEntity<List<AppUser>> getAllUsers(){
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @PostMapping("/add/user")
    public ResponseEntity<AppUser> addUser(@RequestBody  AppUser user){
        return ResponseEntity.ok().body(userService.save(user));
    }

    @GetMapping("/user/{name}")
    public ResponseEntity<AppUser> getUser(@PathVariable String name){
        return ResponseEntity.ok().body(userService.getUser(name));
    }

}
