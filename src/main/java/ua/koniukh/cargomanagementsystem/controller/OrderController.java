package ua.koniukh.cargomanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ua.koniukh.cargomanagementsystem.model.Cargo;
import ua.koniukh.cargomanagementsystem.model.Order;
import ua.koniukh.cargomanagementsystem.model.Role;
import ua.koniukh.cargomanagementsystem.model.User;
import ua.koniukh.cargomanagementsystem.repository.CargoRepository;
import ua.koniukh.cargomanagementsystem.repository.OrderRepository;
import ua.koniukh.cargomanagementsystem.repository.UserRepository;
import ua.koniukh.cargomanagementsystem.service.CargoService;
import ua.koniukh.cargomanagementsystem.service.OrderService;
import ua.koniukh.cargomanagementsystem.service.UserService;

@Controller
public class OrderController {

    @Autowired
    public OrderController(UserService userService, OrderService orderService,
                           CargoService cargoService, UserRepository userRepository,
                           OrderRepository orderRepository, CargoRepository cargoRepository) {
        this.userService = userService;
        this.orderService = orderService;
        this.cargoService = cargoService;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.cargoRepository = cargoRepository;
    }

    private UserService userService;
    private OrderService orderService;
    private CargoService cargoService;

    private UserRepository userRepository;
    private OrderRepository orderRepository;
    private CargoRepository cargoRepository;


    @GetMapping("/order")
    public String orderForm(Cargo cargщ) {
        return "order_form";
    }
//, Order order, Authentication authentication, Model model
    @PostMapping("/order")
    public String makeNewOrder(Cargo cargo, Model model) {
        cargoRepository.save(cargo);
//        orderRepository.save(order);
//        UserDetails userDetails = ((UserDetails) authentication.getPrincipal());
//        User user = userService.findByUsername(userDetails.getUsername());
//        userRepository.save(user);
//        model.addAttribute("user", user);
        model.addAttribute("cargo", cargo);
//        model.addAttribute("order",order);
        return "redirect:/order_page";
    }
}
