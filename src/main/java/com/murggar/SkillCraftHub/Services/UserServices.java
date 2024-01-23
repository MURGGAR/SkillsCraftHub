package com.murggar.SkillCraftHub.Services;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.murggar.SkillCraftHub.Entities.UsersEntity;
import com.murggar.SkillCraftHub.Repositiries.UsersRepository;

import jakarta.transaction.Transactional;

@Service
public class UserServices {

    private UsersRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;

    public UserServices(UsersRepository userRepository,
            PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    // register user
    public void registerUser(UsersEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }

    // login user
    public void loginUser(UsersEntity user) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

    }

    // update user role
    @Transactional
    public void updateUser(String username) {
        UsersEntity user = userRepository.findByUsername(username).get();
        user.setRole("PAID");
        userRepository.save(user);

    }

}
