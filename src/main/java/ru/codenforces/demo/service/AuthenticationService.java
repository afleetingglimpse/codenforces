package ru.codenforces.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.codenforces.demo.auth.AuthenticationRequest;
import ru.codenforces.demo.auth.AuthenticationResponse;
import ru.codenforces.demo.auth.RegisterRequest;
import ru.codenforces.demo.model.User;
import ru.codenforces.demo.repository.UserRepository;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthenticationResponse register(RegisterRequest request) {
        var user = new User();
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("technical");
        var jwtToken = jwtService.generateToken(user);
        AuthenticationResponse ar = new AuthenticationResponse();
        ar.setToken(jwtToken);
        return ar;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getName(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByName(request.getName());
        var jwtToken = jwtService.generateToken(user);

        AuthenticationResponse authResp =new AuthenticationResponse();
        authResp.setToken(jwtToken);
        return authResp;
    }
}
