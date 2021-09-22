package ua.koniukh.cargomanagementsystem.service;

import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ua.koniukh.cargomanagementsystem.model.Cargo;
import ua.koniukh.cargomanagementsystem.model.Order;
import ua.koniukh.cargomanagementsystem.model.dto.OrderDTO;
import ua.koniukh.cargomanagementsystem.repository.OrderRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final CargoService cargoService;

    @Autowired
    public OrderService(OrderRepository orderRepository, UserService userService, CargoService cargoService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.cargoService = cargoService;
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

    public Order createOrder1(@NotNull Cargo cargo, Authentication authentication, OrderDTO orderDTO) {
        Order order = Order.builder()
                .user(userService.getCurrentUser(authentication))
                .cargo(cargo)
                .date(LocalDate.parse(orderDTO.getDate()))
                .type(orderDTO.getType())
                .orderRate(orderDTO.getOrderRate())
                .routeFrom(orderDTO.getRouteFrom())
                .routeTo(orderDTO.getRouteTo())
//               .active(true)
                .build();

//        order.setUser(userService.getCurrentUser(authentication));
//        order.setCargo(cargo);
//        order.setDate(LocalDate.parse(orderDTO.getDate()));
//
        saveOrder(order);

        return order;
    }

//    public Order createOrder2(Order order, Cargo cargo, Authentication authentication, OrderDTO orderDTO) {
//        order.setUser(userService.getCurrentUser(authentication));
//        order.setCargo(cargo);
//        order.setDate(LocalDate.parse(orderDTO.getDate()));
//
//        saveOrder(order);
//        return order;
//    }

    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }
}
