package ua.koniukh.cargomanagementsystem.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
import ua.koniukh.cargomanagementsystem.service.InvoiceService;
import ua.koniukh.cargomanagementsystem.service.OrderService;
import ua.koniukh.cargomanagementsystem.service.UserService;

import javax.validation.Valid;
import java.util.List;


@Controller
public class OrderController {

    @Autowired
    public OrderController(OrderService orderService, InvoiceService invoiceService, UserService userService) {
        this.orderService = orderService;
        this.invoiceService = invoiceService;
        this.userService = userService;
    }

    private final OrderService orderService;
    private final InvoiceService invoiceService;
    private final UserService userService;

    @GetMapping("/order")
    public String orderForm(CargoDTO cargoDTO, OrderDTO orderDTO) {
        return "order_form";
    }

    @PostMapping("/order")
    public String makeNewOrder(@ModelAttribute @Valid OrderDTO orderDTO, BindingResult bindingResult, Authentication authentication, Model model) {
        if (bindingResult.hasErrors()) {
            return "order_form";
        }
        User user = userService.getCurrentUser(authentication);
        orderService.createOrder(user, orderDTO);
        return "redirect:/order_page";
    }

    @GetMapping("/invoice")
    public String paymentPage(Model model, Authentication authentication) {
        User user = userService.getCurrentUser(authentication);
        List<Invoice> paidInvoiceList= invoiceService.getUserInvoiceListByPaidStatus(true, user);
        List<Invoice> unpaidInvoiceList= invoiceService.getUserInvoiceListByPaidStatus(true, user);
        model.addAttribute("invoice", paidInvoiceList);
        model.addAttribute("paidInvoice", unpaidInvoiceList);
        return "payment_page";
    }

    @PostMapping("/invoice/{id}/payment")
    public String payment(@PathVariable(value = "id") Long id, Model model) {
        orderService.invoiceWasPaid(id);
        invoiceService.payInvoice(id);
        return "redirect:/invoice";
    }
}
