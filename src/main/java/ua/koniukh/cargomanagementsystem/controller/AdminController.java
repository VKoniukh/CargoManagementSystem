package ua.koniukh.cargomanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ua.koniukh.cargomanagementsystem.model.Order;
import ua.koniukh.cargomanagementsystem.model.dto.OrderDTO;
import ua.koniukh.cargomanagementsystem.service.InvoiceService;
import ua.koniukh.cargomanagementsystem.service.OrderService;

import java.util.List;


@Controller
public class AdminController {

    @Autowired
    public AdminController(OrderService orderService, InvoiceService invoiceService) {
        this.orderService = orderService;
        this.invoiceService = invoiceService;
    }

    private final OrderService orderService;
    private final InvoiceService invoiceService;

    @GetMapping("/applications")
    public String getAllNotProcessedOrders(Model model) {
        List<Order> processedIsFalseOrders = orderService.findByProcessedIsFalse();
        List<Order> processedIsTrueOrders = orderService.findByProcessedIsTrueAndArchivedIsFalse();
        model.addAttribute("NotProcessedOrders", processedIsFalseOrders);
        model.addAttribute("ProcessedOrders", processedIsTrueOrders);
        return "orders_processed_page";
    }

    @PostMapping("/applications/{id}/processed")
    public String processed(@PathVariable(value = "id") Long id) {
        Order order = orderService.findById(id);
        invoiceService.createInvoice(order);
        orderService.processedOrder(id);
        return "redirect:/applications";
    }

    @PostMapping("/applications/{id}/delete")
    public String deleteNotProcessedOrder(@PathVariable(value = "id") Long id) {
        orderService.deleteNotProcessedOrder(id);
        return "redirect:/applications";
    }

    @PostMapping("/applications/{id}/archive")
    public String archiveOrders(@PathVariable(value = "id") Long id) {
        orderService.archiveOrders(id);
        return "redirect:/applications";
    }

    @PostMapping("/filter_page/{id}/unzip")
    public String unzipOrders(@PathVariable(value = "id") Long id) {
        orderService.unzipOrders(id);
        return "redirect:/filter_page";
    }


    @GetMapping("/filter_page")
    public String filterPage(OrderDTO orderDTO, Model model) {
        List<Order> nonArchivedOrderList = orderService.findAllByArchived(false);
        List<Order> archivedOrderList =orderService.findAllByArchived(true);
        model.addAttribute("nonArchivedOrder", nonArchivedOrderList);
        model.addAttribute("archivedOrder", archivedOrderList);
        return "filter_page";
    }

    @PostMapping("/filter_page/filtered")
    public String filter(OrderDTO orderDTO, Model model) {
        List<Order> filteredByRouteAndOrderPaid = orderService.findByRouteAndOrderPaid(orderDTO.getRouteFrom(), orderDTO.getRouteTo(), orderDTO.isPaid());
        model.addAttribute("orders", filteredByRouteAndOrderPaid);
        return "filter_page";
    }
}
