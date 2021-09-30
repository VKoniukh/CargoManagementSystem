package ua.koniukh.cargomanagementsystem.controller;

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
import ua.koniukh.cargomanagementsystem.service.impl.OrderServiceImpl;
import ua.koniukh.cargomanagementsystem.service.impl.UserServiceImpl;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    public UserController(UserServiceImpl userServiceImpl, OrderServiceImpl orderServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.orderServiceImpl = orderServiceImpl;
    }

    private final UserServiceImpl userServiceImpl;
    private final OrderServiceImpl orderServiceImpl;

    @GetMapping("/order_page")
    public String showUserOrders (Model model, Authentication authentication) {
        User user = userServiceImpl.getCurrentUser(authentication);
        List<Order> unProcessedOrderList = orderServiceImpl.findAllByProcessedAndUserId(false, user.getId());
        List<Order> processedOrderList = orderServiceImpl.findAllByProcessedAndUserId(true, user.getId());

        model.addAttribute("unProcessedOrder", unProcessedOrderList);
        model.addAttribute("processedOrders", processedOrderList);
        return "order_page";
    }

    @PostMapping("/order_page/{id}/delete")
    public String deleteNotProcessedOrder(@PathVariable(value = "id") Long id) {
        orderServiceImpl.deleteNotProcessedOrder(id);
        return "redirect:/order_page";
    }

    @GetMapping("/profile")
    public String userProfile(Model model, Authentication authentication) {
        UserDetails user = ((UserDetails) authentication.getPrincipal());
        User currentUser = userServiceImpl.findByUsername(user.getUsername());
        if (currentUser.getRole().equals(Role.ADMIN)) {
            model.addAttribute("admin", currentUser);
            return "redirect:/applications";
        } else {
            model.addAttribute("user", currentUser);
            return "profile";
        }
    }

    @GetMapping("/user_update/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model) {
        User user = userServiceImpl.findById(id);
        model.addAttribute("user", user);
        return "user_update";
    }

    @PostMapping("/user_update")
    public String updateUserForm(@Valid User user, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "/user_update";
        } else {
            userServiceImpl.updateUser(user);
        }
        return "redirect:/profile";
    }

}
