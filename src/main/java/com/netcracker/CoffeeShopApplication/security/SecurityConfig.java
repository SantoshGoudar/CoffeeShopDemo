package com.netcracker.CoffeeShopApplication.security;

import com.netcracker.CoffeeShopApplication.authenticationservice.controllers.AuthenticationFilter;
import com.netcracker.CoffeeShopApplication.authenticationservice.controllers.AuthenticationTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    AuthenticationTokenProvider authenticationTokenProvider;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests().antMatchers("/products", "/products**", "/products/{name:\\w+}").hasRole("ADMIN").antMatchers(/**/"/reports/orders", "/reports/orders**", "/reports/orders/{orderId:\\w*\\d*}", "/orders", "/orders**", "/orders/{orderId:\\w*\\d*}", "/customers", "/customers**", "/customers/{id\\d*}").hasAnyRole("ADMIN", "USER").antMatchers("/**/login","/swagger-ui").permitAll().and().addFilterBefore(new AuthenticationFilter(authenticationTokenProvider), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
