package ua.koniukh.cargomanagementsystem.service.impl;

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
    public UserDetailsServiceImpl(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    private UserServiceImpl userServiceImpl;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userServiceImpl.findByUsername(s);
        UserDetails userDetails = new UserDetailsImpl(user);
        return userDetails;
    }
}
