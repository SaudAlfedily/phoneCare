package com.example.phonecaresecure.Controller;


import com.example.phonecaresecure.ApiResponse.ApiResponse;
import com.example.phonecaresecure.Model.User;
import com.example.phonecaresecure.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/user")
public class UserController {


    private final UserService userService;
    @GetMapping("/get")
    public ResponseEntity getAllUsers(){
        List<User> userList=userService.getAllUsers();
        return ResponseEntity.status(200).body(userList);
    }


    @PostMapping("/add")
    public ResponseEntity addUser(@Valid @RequestBody User user){
        userService.addUser(user);
        return ResponseEntity.status(200).body(new ApiResponse("User added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateUser(@Valid @RequestBody User user, @PathVariable Integer id){
        userService.updateUser(user,id);
        return ResponseEntity.status(200).body("User Updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id){
        userService.deleteUser(id);
        return ResponseEntity.status(200).body("User deleted");

    }
}
