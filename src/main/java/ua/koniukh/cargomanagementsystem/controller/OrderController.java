package ua.koniukh.cargomanagementsystem.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String orderForm(Model model) {
        CargoDTO cargoDTO = new CargoDTO();
        OrderDTO orderDTO = new OrderDTO();
        model.addAttribute("cargo", cargoDTO);
        model.addAttribute("order", orderDTO);
        return "order_form";
    }

    @PostMapping("/order")
    public String makeNewOrder(@ModelAttribute OrderDTO orderDTO, Authentication authentication, Model model) {
        Cargo cargo = new Cargo(orderDTO.getCargoDTO());
        if (orderDTO.getOrderRate() == OrderRate.CORRESPONDENCE) {
            cargo.setCorrespondence(true);
        } else {
            cargo.setBase(true);
        }
        cargoService.saveCargo(cargo);
        Order order = orderService.createOrder1(cargo, authentication, orderDTO);

//        Order order = orderService.createOrder2(orderService.createOrder1(orderDTO), cargo, authentication, orderDTO);
        return "redirect:/order_page";
    }
}
