package com.example.phonecaresecure.Service;

import com.example.phonecaresecure.Model.User;
import com.example.phonecaresecure.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository myUserRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=myUserRepository.findUserByUsername(username);

        if(user==null){
            throw new UsernameNotFoundException("Wrong username or password");
        }

        return user;
    }
}