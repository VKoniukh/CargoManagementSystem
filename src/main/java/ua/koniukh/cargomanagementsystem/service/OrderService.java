package ua.koniukh.cargomanagementsystem.service;

import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ua.koniukh.cargomanagementsystem.model.Cargo;
import ua.koniukh.cargomanagementsystem.model.Order;
import ua.koniukh.cargomanagementsystem.model.dto.OrderDTO;
import ua.koniukh.cargomanagementsystem.repository.OrderRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final PriceService priceService;

    public OrderService(OrderRepository orderRepository, UserService userService, PriceService priceService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.priceService = priceService;
    }

    public Order findById(Long id) {
        return orderRepository.getById(id);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    public Order createBaseOrder(@NotNull Cargo cargo, Authentication authentication, OrderDTO orderDTO) {
        Order order = Order.builder()
                .user(userService.getCurrentUser(authentication))
                .cargo(cargo)
                .date(LocalDate.parse(orderDTO.getDate()))
                .packing(orderDTO.isPacking())
                .declaredValue(orderDTO.getDeclaredValue())
                .price(priceService.calculatePrice(orderDTO))
                .type(orderDTO.getType())
                .orderRate(orderDTO.getOrderRate())
                .routeFrom(orderDTO.getRouteFrom())
                .routeTo(orderDTO.getRouteTo())
                .build();

        saveOrder(order);

        return order;
    }

    public Order createOrder(@NotNull Authentication authentication, OrderDTO orderDTO) {
        Order order = Order.builder()
                .user(userService.getCurrentUser(authentication))
                .cargo(null)
                .date(LocalDate.parse(orderDTO.getDate()))
                .packing(orderDTO.isPacking())
                .declaredValue(orderDTO.getDeclaredValue())
                .price(priceService.calculatePrice(orderDTO))
                .type(orderDTO.getType())
                .orderRate(orderDTO.getOrderRate())
                .routeFrom(orderDTO.getRouteFrom())
                .routeTo(orderDTO.getRouteTo())
                .build();

        saveOrder(order);

        return order;
    }


    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }
}
