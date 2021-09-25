package ua.koniukh.cargomanagementsystem.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ua.koniukh.cargomanagementsystem.model.*;
import ua.koniukh.cargomanagementsystem.model.dto.CargoDTO;
import ua.koniukh.cargomanagementsystem.model.dto.OrderDTO;
import ua.koniukh.cargomanagementsystem.service.CargoService;
import ua.koniukh.cargomanagementsystem.service.InvoiceService;
import ua.koniukh.cargomanagementsystem.service.OrderService;
import ua.koniukh.cargomanagementsystem.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    public OrderController(UserService userService, OrderService orderService,
                           CargoService cargoService, InvoiceService invoiceService) {
        this.userService = userService;
        this.orderService = orderService;
        this.cargoService = cargoService;
        this.invoiceService = invoiceService;
    }

    private final UserService userService;
    private final OrderService orderService;
    private final CargoService cargoService;
    private final InvoiceService invoiceService;

    @GetMapping("/order")
    public String orderForm(CargoDTO cargoDTO, OrderDTO orderDTO) {
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

    @GetMapping("/invoice")
    public String paymentPage (Model model, Authentication authentication) {
        List<Invoice> invoiceList = invoiceService.getCurrentUserInvoiceList(authentication);
        model.addAttribute("invoice", invoiceList);
        return "payment_page";
    }

    @PostMapping("/invoice/{id}/payment")
    public String payment(@PathVariable(value = "id") Long id, Model model) {
        Invoice invoice = invoiceService.findById(id);
        //todo mb merge;
        orderService.payInvoice(id);
        return "redirect:/order_page";
    }

    //todo deleting functional
//    @GetMapping("/order_delete/{id}")
//    public String deleteOrder(@PathVariable("id") Long id) {
//        orderService.deleteById(id);
//        return "redirect:/profile";
//    }
}
