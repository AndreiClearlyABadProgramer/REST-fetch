package com.security.springBoot.controllers;

import com.security.springBoot.models.User;
import com.security.springBoot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class RestAPIController {

    private long id;

    @Autowired
    private final UserService userService;

    public RestAPIController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("api/list")
    public List<User> users(){
        return userService.userList();
    }

    @GetMapping("api/userInfo")
    public User user(Principal principal){
        return userService.getUserByName(principal.getName());
    }

    @RequestMapping("/getUser/{id}")
    public User getUser(@PathVariable("id") long id){
        User user = userService.getUserById(id);
        this.id = id;
        return user;
    }


    @PostMapping( "api/add")
    public void create(@RequestBody User user) {
        userService.addUser(user);
    }

    @PutMapping("api/edit")
    public User update(@RequestBody User user) {
        user.setId(id);
        userService.updateUser(user);
        return user;

    }

    @DeleteMapping("/api/delete")
    public void remove(){
        userService.deleteUser(this.id);
    }
}
