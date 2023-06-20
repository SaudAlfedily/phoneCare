package com.example.phonecaresecure.Service;

import com.example.phonecaresecure.ApiException.ApiException;
import com.example.phonecaresecure.Model.User;
import com.example.phonecaresecure.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void addUser(User user){
        String hash= new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hash);
        userRepository.save(user);
    }
    public void updateUser(User user,Integer id){
        User olduser= userRepository.findUserById(id);
        if(olduser==null){
            throw new ApiException("User not found");
        }
        olduser.setRole(user.getRole());
        olduser.setUsername(user.getUsername());
        String hash= new BCryptPasswordEncoder().encode(user.getPassword());
        olduser.setPassword(hash);
        userRepository.save(olduser);
    }



    public void deleteUser(Integer id){
        User user= userRepository.findUserById(id);
        if(user==null){
            throw new ApiException("User not found");
        }

        userRepository.delete(user);
    }



}
