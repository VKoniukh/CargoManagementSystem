package ua.koniukh.cargomanagementsystem.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.koniukh.cargomanagementsystem.model.Cargo;
import ua.koniukh.cargomanagementsystem.model.Order;
import ua.koniukh.cargomanagementsystem.model.OrderRate;
import ua.koniukh.cargomanagementsystem.model.dto.CargoDTO;
import ua.koniukh.cargomanagementsystem.model.dto.OrderDTO;
import ua.koniukh.cargomanagementsystem.service.CargoService;
import ua.koniukh.cargomanagementsystem.service.OrderService;
import ua.koniukh.cargomanagementsystem.service.UserService;

import javax.validation.Valid;

@Controller
public class OrderController {

    @Autowired
    public OrderController(UserService userService, OrderService orderService,
                           CargoService cargoService) {
        this.userService = userService;
        this.orderService = orderService;
        this.cargoService = cargoService;
    }

    private UserService userService;
    private OrderService orderService;
    private CargoService cargoService;

    @GetMapping("/order")
    public String orderForm(CargoDTO cargoDTO, OrderDTO orderDTO) {
//        CargoDTO cargoDTO = new CargoDTO();
//        OrderDTO orderDTO = new OrderDTO();
//        model.addAttribute("cargo", cargoDTO);
//        model.addAttribute("order", orderDTO);
        return "order_form";
    }

    @PostMapping("/order")
    public String makeNewOrder(@ModelAttribute @Valid OrderDTO orderDTO, BindingResult bindingResult, Authentication authentication, Model model) {
        if (bindingResult.hasErrors()) {
            return "order_form";
        } else if (orderDTO.getOrderRate() == OrderRate.BASE) {
            Cargo cargo = new Cargo(orderDTO.getCargoDTO());
            cargoService.saveCargo(cargo);
            Order order = orderService.createBaseOrder(cargo, authentication, orderDTO);
        } else if (orderDTO.getOrderRate() == OrderRate.CORRESPONDENCE) {
            Order order = orderService.createOrder(authentication, orderDTO);
        }
        return "redirect:/order_page";
    }
}
