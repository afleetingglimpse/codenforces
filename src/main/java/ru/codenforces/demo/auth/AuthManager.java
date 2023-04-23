//package ru.codenforces.demo.auth;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.stereotype.Component;
//
//@Component
//public class AuthManager implements AuthenticationManager {
//
//    AuthenticationConfiguration config;
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }
//
//    @Autowired
//    public AuthManager(AuthenticationConfiguration config) {
//        this.config = config;
//    }
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        try {
//           return config.getAuthenticationManager().authenticate(authentication);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
