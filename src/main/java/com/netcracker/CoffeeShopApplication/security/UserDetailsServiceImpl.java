package com.netcracker.CoffeeShopApplication.security;

import com.netcracker.CoffeeShopApplication.authenticationservice.model.User;
import com.netcracker.CoffeeShopApplication.authenticationservice.repository.UserRepository;
import com.netcracker.CoffeeShopApplication.authenticationservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserService userService;

    @Override

    public UserDetails loadUserByUsername(String username) {
        User user = userService.findbyId(username);
        logger.info("find user by name " + username);
        if (user == null) {
            logger.error("User Not found with name " + username);
            throw new UsernameNotFoundException(username);
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), grantedAuthorities);
    }
}
