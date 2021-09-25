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
import ua.koniukh.cargomanagementsystem.service.PriceService;
import ua.koniukh.cargomanagementsystem.service.UserService;

import javax.validation.Valid;

@Controller
public class MainController {

    @Autowired
    public MainController(PriceService priceService) {
        this.priceService = priceService;
    }

    private PriceService priceService;

    @GetMapping("/")
    public String homePage() {
        return "home_page";
    }

    @GetMapping("/estimation")
    public String estimationForm(CargoDTO cargoDTO, OrderDTO orderDTO) {
        return "estimation_form";
    }

    @PostMapping("/estimation")
    public String estimation(@ModelAttribute @Valid OrderDTO orderDTO, BindingResult bindingResult, Authentication authentication, Model model) {
        if (bindingResult.hasErrors()) {
            return "order_form";
        }
        orderDTO.setPrice(priceService.calculatePrice(orderDTO));
        model.addAttribute("order", orderDTO);
        return "evaluated_page";
    }
}
