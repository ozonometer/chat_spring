package com.chat.chat_spring.configuration;

import com.chat.chat_spring.service.JwtFilterRequest;
import com.chat.chat_spring.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Requirement 2, handle and process HTTP requests.
 * This configuration allows user to make authentication call with user and password, also this
 * configuration allows websocket connection for live chat instant messages
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final JwtFilterRequest jwtFilterRequest;

    public SecurityConfiguration(UserService userService, JwtFilterRequest jwtFilterRequest) {
        this.userService = userService;
        this.jwtFilterRequest = jwtFilterRequest;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/ws/info", "/ws/**").permitAll()
                .antMatchers("/authenticate", "/createUser")
                .permitAll().anyRequest().authenticated();
        http.addFilterBefore(jwtFilterRequest, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }


}
