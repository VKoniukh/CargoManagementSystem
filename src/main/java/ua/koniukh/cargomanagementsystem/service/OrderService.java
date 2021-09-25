package ua.koniukh.cargomanagementsystem.service;

import com.sun.istack.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ua.koniukh.cargomanagementsystem.model.Cargo;
import ua.koniukh.cargomanagementsystem.model.Order;
import ua.koniukh.cargomanagementsystem.model.User;
import ua.koniukh.cargomanagementsystem.model.dto.OrderDTO;
import ua.koniukh.cargomanagementsystem.repository.CargoRepository;
import ua.koniukh.cargomanagementsystem.repository.OrderRepository;
import ua.koniukh.cargomanagementsystem.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final CargoRepository cargoRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final PriceService priceService;
    private final CargoService cargoService;

    public OrderService(CargoRepository cargoRepository, UserRepository userRepository, OrderRepository orderRepository, UserService userService, PriceService priceService, CargoService cargoService) {
        this.cargoRepository = cargoRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.priceService = priceService;
        this.cargoService = cargoService;
    }

    public List<Order> showApprove() {
        return orderRepository.findAllByApprovedIsTrue();
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
                .approved(false)
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

    //todo write own exeption
    @Transactional
    public void deleteById(Long id) {
        try {
           if (cargoRepository.existsById(findById(id).getCargo().getId())) {
               cargoService.deleteById(findById(id).getCargo().getId());
           }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        if (orderRepository.existsById(id))
        orderRepository.deleteById(id);
    }

    @Transactional
    public void deleteUserById(Long id) {
        User user = userService.findById(id);
        List<Order> list = new ArrayList<>(user.getOrders());
        if (list.isEmpty()) {
            userRepository.deleteById(id);
        }
        for (Order order : list) {
            deleteById(order.getId());
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public void approveOrder(Long id) {
        Order order = findById(id);
        order.setApproved(true);
        saveOrder(order);
    }
}
