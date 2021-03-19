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
public class RestAPIController {

    private ResponseEntity<?> re;

    @Autowired
    private final UserService userService;

    public RestAPIController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("api/list")
    public ResponseEntity<List<User>> users(){
        return ResponseEntity.status(HttpStatus.OK)
        .body(userService.userList());
    }

    @GetMapping("api/userInfo")
    public ResponseEntity<User> user(Principal principal){
        return ResponseEntity.status(HttpStatus.OK)
        .body(userService.getUserByName(principal.getName()));
    }

    @RequestMapping("/getUser/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") long id){
        return ResponseEntity.status(HttpStatus.OK)
        .body(userService.getUserById(id));
    }


    @PostMapping( "api/add")
    public ResponseEntity<?> create(@RequestBody User user) {
        try {
            userService.addUser(user);
            re = new ResponseEntity<>("User added", HttpStatus.OK);
        } catch (Exception e) {
            re = new ResponseEntity<>("Unable to add user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return re;
    }

    @PutMapping("api/edit")
    public ResponseEntity<?> update(@RequestBody User user) {
        try {
            userService.updateUser(user);
            re = new ResponseEntity<>("User added", HttpStatus.OK);
        } catch (Exception e) {
            re = new ResponseEntity<>("Unable to add user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return re;

    }

    @DeleteMapping("/api/delete/{id}")
    public ResponseEntity<?> remove(@PathVariable long id){
        try {
            userService.deleteUser(id);
            re = new ResponseEntity<>("User Deleted", HttpStatus.OK);
        } catch (Exception e) {
            re = new ResponseEntity<>("Unable to delete user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return re;
    }
}
