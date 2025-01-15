package com.sd.stackoverflow.controller;


import com.sd.stackoverflow.entity.User;
import com.sd.stackoverflow.entity.UserScoreDTO;
import com.sd.stackoverflow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getAll")
    @ResponseBody
    public List<User> getAllUsers() {
        List<User> users =this.userService.retrieveUsers();
        return users;
    }

    @GetMapping("/getUsersWithScores")
    @ResponseBody
    public List<UserScoreDTO> getAllUsersWithScores() {
        List<UserScoreDTO> usersWithScores =this.userService.userWithScores();
        return usersWithScores;
    }

    @GetMapping("/findUser")
    @ResponseBody
    public User getUser(@RequestParam String username,@RequestParam String password) {
        return this.userService.findUser(username,password);

    }

    @PostMapping("/addUser")
    @ResponseBody
    public String addUser(@RequestBody User user){
        return  this.userService.addUser(user);
    }

    @PutMapping("/updateUser")
    public String updateUser(@RequestBody User user){
        return  this.userService.updateUser(user);
    }


    @DeleteMapping("/deleteById")
    @ResponseBody
    public String deleteUserById(@RequestParam Long userId){
        return this.userService.deleteUserById(userId);
    }

    @PutMapping("/banUser")
    public String banUser(@RequestParam Long userId){
        return  this.userService.banUser(userId);
    }

    @PutMapping("/unbanUser")
    public String unbanUser(@RequestParam Long userId){
        return  this.userService.unbanUser(userId);
    }
}
