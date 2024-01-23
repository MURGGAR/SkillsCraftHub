package com.murggar.SkillCraftHub.SecurityConfiguration;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.murggar.SkillCraftHub.Entities.UsersEntity;
import com.murggar.SkillCraftHub.Repositiries.UsersRepository;

@Service
public class CustomeUserServiceDetails implements UserDetailsService {

    private UsersRepository userRepository;

    public CustomeUserServiceDetails(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UsersEntity user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " does not exist"));

        if (userRepository.findByUsername(username).isPresent()) {
            return new User(user.getUsername(), user.getPassword(), extendsToGrantedAuthority(List.of(user.getRole())));
        }

        // if (user.getEmail().equals("monamodi68@gmail.com")) {
        // return new User(user.getUsername(), user.getPassword(), user.getRole());
        // }

        // if (user.getRoles().equals("USER")) {
        // return new User(user.getUsername(),
        // user.getPassword(),
        // USER.getGrantedAuthorities());
        // }

        throw new UsernameNotFoundException("Username " + username + " does not exist");

    }

    public Collection<GrantedAuthority> extendsToGrantedAuthority(List<String> roles) {

        return roles.stream().map(rolss -> new SimpleGrantedAuthority(rolss))
                .collect(Collectors.toList());
    }

}
