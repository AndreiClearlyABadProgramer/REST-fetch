package com.security.springBoot.controllers;

import com.security.springBoot.models.User;
import com.security.springBoot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api")
public class RestAPIController {

    @Autowired
    private final UserService userService;

    public RestAPIController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<User>> users(){
        return ResponseEntity.status(HttpStatus.OK)
        .body(userService.userList());
    }

    @GetMapping("/userInfo")
    public ResponseEntity<User> user(Principal principal){
        return ResponseEntity.status(HttpStatus.FOUND)
        .body(userService.getUserByName(principal.getName()));
    }

    @RequestMapping("/getUser/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") long id){
        return ResponseEntity.status(HttpStatus.OK)
        .body(userService.getUserById(id));
    }


    @PostMapping( "/add")
    public ResponseEntity<?> create(@RequestBody User user) {
        userService.addUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/edit")
    public ResponseEntity<?> update(@RequestBody User user) {
        userService.updateUser(user);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> remove(@PathVariable long id){
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
