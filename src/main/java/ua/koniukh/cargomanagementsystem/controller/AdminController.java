package ua.koniukh.cargomanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ua.koniukh.cargomanagementsystem.model.Order;
import ua.koniukh.cargomanagementsystem.model.dto.OrderDTO;
import ua.koniukh.cargomanagementsystem.service.impl.InvoiceServiceImpl;
import ua.koniukh.cargomanagementsystem.service.impl.OrderServiceImpl;

import java.util.List;


@Controller
public class AdminController {

    @Autowired
    public AdminController(OrderServiceImpl orderServiceImpl, InvoiceServiceImpl invoiceServiceImpl) {
        this.orderServiceImpl = orderServiceImpl;
        this.invoiceServiceImpl = invoiceServiceImpl;
    }

    private final OrderServiceImpl orderServiceImpl;
    private final InvoiceServiceImpl invoiceServiceImpl;

    @GetMapping("/applications")
    public String getAllNotProcessedOrders(Model model) {
        List<Order> processedIsFalseOrders = orderServiceImpl.findByProcessedIsFalse();
        List<Order> processedIsTrueOrders = orderServiceImpl.findByProcessedIsTrueAndArchivedIsFalse();
        model.addAttribute("NotProcessedOrders", processedIsFalseOrders);
        model.addAttribute("ProcessedOrders", processedIsTrueOrders);
        return "orders_processed_page";
    }

    @PostMapping("/applications/{id}/processed")
    public String processed(@PathVariable(value = "id") Long id) {
        Order order = orderServiceImpl.findById(id);
        invoiceServiceImpl.createInvoice(order);
        orderServiceImpl.processedOrder(id);
        return "redirect:/applications";
    }

    @PostMapping("/applications/{id}/delete")
    public String deleteNotProcessedOrder(@PathVariable(value = "id") Long id) {
        orderServiceImpl.deleteNotProcessedOrder(id);
        return "redirect:/applications";
    }

    @PostMapping("/applications/{id}/archive")
    public String archiveOrders(@PathVariable(value = "id") Long id) {
        orderServiceImpl.archiveOrders(id);
        return "redirect:/applications";
    }

    @PostMapping("/filter_page/{id}/unzip")
    public String unzipOrders(@PathVariable(value = "id") Long id) {
        orderServiceImpl.unzipOrders(id);
        return "redirect:/filter_page";
    }


    @GetMapping("/filter_page")
    public String filterPage(OrderDTO orderDTO, Model model) {
        List<Order> nonArchivedOrderList = orderServiceImpl.findAllByArchived(false);
        List<Order> archivedOrderList = orderServiceImpl.findAllByArchived(true);
        model.addAttribute("nonArchivedOrder", nonArchivedOrderList);
        model.addAttribute("archivedOrder", archivedOrderList);
        return "filter_page";
    }

    @PostMapping("/filter_page/filtered")
    public String filter(OrderDTO orderDTO, Model model) {
        List<Order> filteredByRouteAndOrderPaidAndArchived1 = orderServiceImpl.getByArchivedAndOrderPaidAndRouteFromAndRouteTo(false, orderDTO.isPaid(), orderDTO.getRouteFrom(), orderDTO.getRouteTo());
        List<Order> filteredByRouteAndOrderPaidAndArchived2 = orderServiceImpl.getByArchivedAndOrderPaidAndRouteFromAndRouteTo(true, orderDTO.isPaid(), orderDTO.getRouteFrom(), orderDTO.getRouteTo());
        model.addAttribute("nonArchivedOrder", filteredByRouteAndOrderPaidAndArchived1);
        model.addAttribute("archivedOrder", filteredByRouteAndOrderPaidAndArchived2);
        return "filter_page";
    }
}
