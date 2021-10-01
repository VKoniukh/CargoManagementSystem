package ua.koniukh.cargomanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.koniukh.cargomanagementsystem.model.dto.CargoDTO;
import ua.koniukh.cargomanagementsystem.model.dto.OrderDTO;
import ua.koniukh.cargomanagementsystem.service.impl.CalculationServiceImpl;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
public class MainController {

    @Autowired
    public MainController(CalculationServiceImpl calculationService) {
        this.calculationService = calculationService;
    }

    private CalculationServiceImpl calculationService;

    @GetMapping("/")
    public String homePage() {
        return "home_page";
    }

    @GetMapping("/info")
    public String info() {
        return "info_page";
    }

    @GetMapping("/estimation")
    public String estimationForm(CargoDTO cargoDTO, OrderDTO orderDTO) {
        return "estimation_form";
    }

    @PostMapping("/estimation")
    public String estimation(@ModelAttribute @Valid OrderDTO orderDTO, BindingResult bindingResult, Authentication authentication, Model model) {
        if (bindingResult.hasErrors()) {
            return "estimation_form";
        }
        orderDTO.setPrice(calculationService.calculatePrice(orderDTO));
        orderDTO.setPossibleDeliveryDate(LocalDate.now().plusDays(calculationService.routeToDate(orderDTO.getRouteFrom(), orderDTO.getRouteTo())));
        model.addAttribute("order", orderDTO);
            return "evaluated_page";
    }
}
