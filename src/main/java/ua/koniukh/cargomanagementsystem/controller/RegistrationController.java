package ua.koniukh.cargomanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.koniukh.cargomanagementsystem.model.User;
import ua.koniukh.cargomanagementsystem.model.dto.UserDTO;
import ua.koniukh.cargomanagementsystem.service.UserService;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration(UserDTO userDTO) {
        return "registration";
    }

    //todo checking possibility to create ac with same param!!!!

    @PostMapping("/registration")
    public String registrationNewUser(@Valid UserDTO userDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/registration";
        } else {
            User userFromDb = userService.findByUsername(userDTO.getUsername());
            if (userFromDb != null) {
                model.addAttribute("message", "User exists!");
                return "registration";
            }

            userService.signUp(userDTO);

            return "redirect:/login";
        }
    }
}
