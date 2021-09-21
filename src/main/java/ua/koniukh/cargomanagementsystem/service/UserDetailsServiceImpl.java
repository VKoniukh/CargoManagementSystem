package ua.koniukh.cargomanagementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.koniukh.cargomanagementsystem.config.UserDetailsImpl;
import ua.koniukh.cargomanagementsystem.model.User;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.findByUsername(s);
        UserDetails userDetails = new UserDetailsImpl(user);
        return userDetails;
    }
}
