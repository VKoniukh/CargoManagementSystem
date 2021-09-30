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
import ua.koniukh.cargomanagementsystem.service.impl.InvoiceServiceImpl;
import ua.koniukh.cargomanagementsystem.service.impl.OrderServiceImpl;
import ua.koniukh.cargomanagementsystem.service.impl.UserServiceImpl;

import javax.validation.Valid;
import java.util.List;


@Controller
public class OrderController {

    @Autowired
    public OrderController(OrderServiceImpl orderServiceImpl, InvoiceServiceImpl invoiceServiceImpl, UserServiceImpl userServiceImpl) {
        this.orderServiceImpl = orderServiceImpl;
        this.invoiceServiceImpl = invoiceServiceImpl;
        this.userServiceImpl = userServiceImpl;
    }

    private final OrderServiceImpl orderServiceImpl;
    private final InvoiceServiceImpl invoiceServiceImpl;
    private final UserServiceImpl userServiceImpl;

    @GetMapping("/order")
    public String orderForm(CargoDTO cargoDTO, OrderDTO orderDTO) {
        return "order_form";
    }

    @PostMapping("/order")
    public String makeNewOrder(@ModelAttribute @Valid OrderDTO orderDTO, BindingResult bindingResult, Authentication authentication, Model model) {
        if (bindingResult.hasErrors()) {
            return "order_form";
        }
        User user = userServiceImpl.getCurrentUser(authentication);
        orderServiceImpl.createOrder(user, orderDTO);
        return "redirect:/order_page";
    }

    @GetMapping("/invoice")
    public String paymentPage(Model model, Authentication authentication) {
        User user = userServiceImpl.getCurrentUser(authentication);
        List<Invoice> unpaidInvoiceList = invoiceServiceImpl.getUserInvoiceListByPaidStatus(false, user);
        List<Invoice> paidInvoiceList = invoiceServiceImpl.getUserInvoiceListByPaidStatus(true, user);
        model.addAttribute("invoice", unpaidInvoiceList);
        model.addAttribute("paidInvoice", paidInvoiceList);
        return "payment_page";
    }

    @PostMapping("/invoice/{id}/payment")
    public String payment(@PathVariable(value = "id") Long id) {
        orderServiceImpl.orderInvoiceWasPaid(invoiceServiceImpl.findById(id).getOrder().getId());
        invoiceServiceImpl.payInvoice(id);
        return "redirect:/invoice";
    }
}
