package ua.koniukh.cargomanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ua.koniukh.cargomanagementsystem.model.User;
import ua.koniukh.cargomanagementsystem.model.dto.UserDTO;
import ua.koniukh.cargomanagementsystem.service.impl.UserServiceImpl;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    public RegistrationController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    private final UserServiceImpl userServiceImpl;

    @GetMapping("/registration")
    public String registration(UserDTO userDTO) {
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationNewUser(@Valid UserDTO userDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/registration";
        } else {
            User userFromDb = userServiceImpl.findByUsername(userDTO.getUsername());
            if (userFromDb != null) {
                model.addAttribute("message", "User exists!");
                return "registration";
            }

            userServiceImpl.signUp(userDTO);

            return "redirect:/login";
        }
    }
}
