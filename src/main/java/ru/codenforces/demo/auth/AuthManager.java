//package ru.codenforces.demo.auth;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.DisabledException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import ru.codenforces.demo.model.User;
//import ru.codenforces.demo.repository.UserRepository;
//@Component
//public class AuthManager implements AuthenticationManager {
//
//    @Autowired
//    private UserRepository userRepository;
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String username = authentication.getPrincipal() + "";
//        String password = authentication.getCredentials() + "";
//
//        User user = userRepository.findByName(username);
//        if (user == null) {
//            throw new BadCredentialsException("User not found");
//        }
//        if (!user.isEnabled()) {
//            throw new DisabledException("User is disabled");
//        }
//        if (password.equals(user.getPassword())) {
//            SecurityContext sc = SecurityContextHolder.getContext();
//            sc.setAuthentication(authentication);
//        }
//        return new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());
//    }
//}
