package com.example.hookahshop_diploma.service;

import com.example.hookahshop_diploma.model.ShopUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private ShopService service;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ShopUser user = service.findUserByEmail(email);
        if (user == null){
            throw new UsernameNotFoundException("Could not find that email: " + email);
        }
        List<GrantedAuthority> listRoles = Arrays.asList(new SimpleGrantedAuthority(user.getRole().toString()));
        return new User(user.getEmail(), user.getPassword(), listRoles);
    }
}
