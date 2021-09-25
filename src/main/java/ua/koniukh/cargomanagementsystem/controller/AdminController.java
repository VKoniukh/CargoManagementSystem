package ua.koniukh.cargomanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.koniukh.cargomanagementsystem.model.Order;
import ua.koniukh.cargomanagementsystem.service.OrderService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
//@RequestMapping("/admin_page")
public class AdminController {

    private final OrderService orderService;

    @Autowired
    public AdminController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/applications")
    public String getAllNotApprovedOrders(Model model) {
        List<Order> allOrderListFromDB = orderService.findAll();
        List<Order> OrderListFromDB = allOrderListFromDB.stream()
                .filter(order -> !order.isApproved())
                .collect(Collectors.toList());
        model.addAttribute("OrderListFromDB", OrderListFromDB);
        return "not_approved_orders";
    }

    @GetMapping("/approved")
    public String getAllApprovedOrders(Model model) {
        model.addAttribute("OrderListFromDB", orderService.showApprove());
        return "//todo";
    }

    @PostMapping("/applications/{id}/approve")
    public String approve(@PathVariable(value = "id") Long id, Model model) {
        orderService.approveOrder(id);
        return "redirect:/applications";
    }

    @PostMapping("/applications/{id}/delete")
    public String deleteUserOrder(@PathVariable(value = "id") Long id, Model model) {
        orderService.deleteById(id);
        return "redirect:/applications";
    }
}
