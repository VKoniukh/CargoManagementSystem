package ua.koniukh.cargomanagementsystem.service;

import com.sun.istack.NotNull;
import org.springframework.security.core.Authentication;
import ua.koniukh.cargomanagementsystem.model.Order;
import ua.koniukh.cargomanagementsystem.model.User;
import ua.koniukh.cargomanagementsystem.model.dto.UserDTO;

import java.util.List;


public interface UserService {

    User findById(Long id);

    User saveUser(User user);

    User findByUsername(String username);

    void updateUser(User user);

    void signUp(@NotNull UserDTO userDTO);

    User getCurrentUser(Authentication authentication);

    List<Order> getCurrentUserOrderList(Authentication authentication);
}
