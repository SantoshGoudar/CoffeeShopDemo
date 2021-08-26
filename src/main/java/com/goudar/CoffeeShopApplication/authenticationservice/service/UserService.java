package com.goudar.CoffeeShopApplication.authenticationservice.service;

import com.goudar.CoffeeShopApplication.authenticationservice.model.User;
import com.goudar.CoffeeShopApplication.authenticationservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder encoder;

    public User findbyId(String id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException(id));
    }

    public void save(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

}
