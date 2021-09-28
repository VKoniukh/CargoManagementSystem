package ua.koniukh.cargomanagementsystem.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ua.koniukh.cargomanagementsystem.model.Order;
import ua.koniukh.cargomanagementsystem.model.Role;
import ua.koniukh.cargomanagementsystem.model.User;
import ua.koniukh.cargomanagementsystem.service.CargoService;
import ua.koniukh.cargomanagementsystem.service.OrderService;
import ua.koniukh.cargomanagementsystem.service.UserService;

@Controller
public class UserController {

  @Autowired
  public UserController(
      UserService userService, OrderService orderService, CargoService cargoService) {
    this.userService = userService;
    this.orderService = orderService;
    this.cargoService = cargoService;
  }

  private final UserService userService;
  private final OrderService orderService;
  private final CargoService cargoService;

  @GetMapping("/order_page")
  public String showUserOrders(Model model, Authentication authentication) {
    List<Order> orderList = userService.getCurrentUserOrderList(authentication);
    model.addAttribute("orders", orderList);
    return "order_page";
  }

  @GetMapping("/profile")
  public String userProfile(Model model, Authentication authentication) {
    UserDetails user = ((UserDetails) authentication.getPrincipal());
    User currentUser = userService.findByUsername(user.getUsername());
    if (currentUser.getRole().equals(Role.ADMIN) && currentUser.getUsername().equals("admin")) {
      model.addAttribute("admin", currentUser);
      return "redirect:/applications";
    } else {
      model.addAttribute("user", currentUser);
      return "profile";
    }
  }

  @GetMapping("/user_delete/{id}")
  public String deleteUser(@PathVariable("id") Long id) {
    //        orderService.deleteUserById(id);
    return "redirect:/login";
  }

  // todo mb add DTO function
  @GetMapping("/user_update/{id}")
  public String updateUserForm(@PathVariable("id") Long id, Model model) {
    User user = userService.findById(id);
    model.addAttribute("user", user);
    return "user_update";
  }

  @PostMapping("/user_update")
  public String updateUserForm(@Valid User user, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "/user_update";
    }
    userService.updateUser(user);
    return "redirect:/profile";
  }
}
