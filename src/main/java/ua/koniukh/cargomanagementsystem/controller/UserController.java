package ua.koniukh.cargomanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ua.koniukh.cargomanagementsystem.model.User;
import ua.koniukh.cargomanagementsystem.service.UserService;

import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    private UserService userService;

//    @GetMapping("/users")
//    public String findAll(Model model, Authentication authentication) {
//        UserDetails user = ((UserDetails) authentication.getPrincipal());
//        List<User> userList = userService.findAll();
//        model.addAttribute("users", userList);
//        return "user_list";
//    }

    @GetMapping("/profile")
    public String userProfile(Model model, Authentication authentication) {
        UserDetails user = ((UserDetails) authentication.getPrincipal());
        User user1 = userService.findByUsername(user.getUsername());
        model.addAttribute("user", user1);
        return "profile";
    }

//    @GetMapping("/user_create")
//    public String createUserForm(User user) {
//        return ("user_create");
//    }
//
//    @PostMapping("/user_create")
//    public String createUser(User user) {
//        userService.saveUser(user);
//        return "redirect:/users";
//    }

    @GetMapping("/user_delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/login";
    }

    @GetMapping("/user_update/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "user_update";
    }

    @PostMapping("/user_update")
    public String updateUserForm(User user) {
        userService.saveUser(user);
        return "redirect:/profile";
    }

}
