package com.example.pizza_project.details;

import com.example.pizza_project.entity.User;
import com.example.pizza_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class SecurityDetails implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username);
        if(user == null)
            throw  new UsernameNotFoundException("Couldn't find a user " + username);
        return new MyUserDetails(user);
    }
}
