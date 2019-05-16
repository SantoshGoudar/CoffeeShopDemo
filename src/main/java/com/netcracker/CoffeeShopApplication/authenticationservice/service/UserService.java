package com.netcracker.CoffeeShopApplication.authenticationservice.service;

import com.netcracker.CoffeeShopApplication.authenticationservice.model.User;
import com.netcracker.CoffeeShopApplication.authenticationservice.repository.UserRepository;
import com.netcracker.CoffeeShopApplication.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder encoder;

    public User findbyId(String id) {
        Optional<User> byId = userRepository.findById(id);
        if (!byId.isPresent()) {
            throw new UsernameNotFoundException(id);
        }
        return byId.get();
    }

    public void save(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

}
