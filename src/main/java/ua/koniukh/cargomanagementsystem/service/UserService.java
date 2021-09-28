package ua.koniukh.cargomanagementsystem.service;

import static ua.koniukh.cargomanagementsystem.model.Role.USER;

import com.sun.istack.NotNull;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ua.koniukh.cargomanagementsystem.model.Order;
import ua.koniukh.cargomanagementsystem.model.Role;
import ua.koniukh.cargomanagementsystem.model.User;
import ua.koniukh.cargomanagementsystem.model.dto.UserDTO;
import ua.koniukh.cargomanagementsystem.repository.UserRepository;

@Service
public class UserService {

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  private final UserRepository userRepository;

  public User findById(Long id) {
    return userRepository.getById(id);
  }

  public List<User> findAll() {
    return userRepository.findAll();
  }

  public User saveUser(User user) {
    return userRepository.save(user);
  }

  public void updateUser(User user) {
    //        todo change user update logix, get user from db and then update fields
    if (user.getRole() == Role.ADMIN) {
      user.setActive(true);
      user.setRole(Role.ADMIN);
      saveUser(user);
    }
    user.setActive(true);
    user.setRole(Role.USER);
    saveUser(user);
  }

  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
    //        User foundUser = userRepository.findByUsername(username);
    //        if (foundUser == null) {
    //            throw new UsernameNotFoundException(username);
    //        }
    //        return foundUser;
  }

  public void signUp(@NotNull UserDTO userDTO) {
    //        String hashPassword = passwordEncoder.encode(userDTO.getPassword());
    //                .hashPassword(hashPassword)
    User user =
        User.builder()
            .username(userDTO.getUsername())
            .password(userDTO.getPassword())
            .active(true)
            .role(USER)
            .build();

    saveUser(user);
  }

  public User getCurrentUser(Authentication authentication) {
    UserDetails userDetails = ((UserDetails) authentication.getPrincipal());
    User user = findByUsername(userDetails.getUsername());
    return user;
  }

  public List<Order> getCurrentUserOrderList(Authentication authentication) {
    User user = getCurrentUser(authentication);
    return user.getOrders();
  }
}
