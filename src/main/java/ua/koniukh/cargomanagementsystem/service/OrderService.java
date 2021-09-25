package ua.koniukh.cargomanagementsystem.service;

import com.sun.istack.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ua.koniukh.cargomanagementsystem.model.Cargo;
import ua.koniukh.cargomanagementsystem.model.Order;
import ua.koniukh.cargomanagementsystem.model.User;
import ua.koniukh.cargomanagementsystem.model.dto.OrderDTO;
import ua.koniukh.cargomanagementsystem.repository.CargoRepository;
import ua.koniukh.cargomanagementsystem.repository.InvoiceRepository;
import ua.koniukh.cargomanagementsystem.repository.OrderRepository;
import ua.koniukh.cargomanagementsystem.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final InvoiceRepository invoiceRepository;
    private final CargoRepository cargoRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final PriceService priceService;
    private final CargoService cargoService;
    private final InvoiceService invoiceService;

    public OrderService(InvoiceRepository invoiceRepository, CargoRepository cargoRepository, UserRepository userRepository,
                        OrderRepository orderRepository, UserService userService,
                        PriceService priceService, CargoService cargoService, InvoiceService invoiceService) {
        this.invoiceRepository = invoiceRepository;
        this.cargoRepository = cargoRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.priceService = priceService;
        this.cargoService = cargoService;
        this.invoiceService = invoiceService;
    }

//    public List<Order> showApprove() {
////        return orderRepository.findAllByApprovedIsTrue();
//    }

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
                .processed(false)
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
                .processed(false)
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

        try {
            if (invoiceRepository.existsById(invoiceService.findByOrderId(id).getId())) {
                invoiceService.deleteById(invoiceService.findByOrderId(id).getId());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            if (orderRepository.existsById(id))
                orderRepository.deleteById(id);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void deleteUserById(Long id) {
        User user = userService.findById(id);
        List<Order> list =  new ArrayList<>(user.getOrders());
        if (list.isEmpty() & userRepository.existsById(userService.findById(id).getId())) {
            try {
                userRepository.deleteById(id);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        for (Order order : list) {
            deleteById(order.getId());
        }
        if (userRepository.existsById(userService.findById(id).getId())) {
            try {
                userRepository.deleteById(id);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    @Transactional
    public void processedOrder(Long id) {
        Order order = findById(id);
        order.setProcessed(true);
        saveOrder(order);
    }

    @Transactional
    public void payInvoice(Long id) {
        Order order = findById(id);
        order.setPaid(true);
        saveOrder(order);
    }
}

