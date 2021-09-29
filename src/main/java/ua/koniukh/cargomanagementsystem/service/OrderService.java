package ua.koniukh.cargomanagementsystem.service;

import com.sun.istack.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ua.koniukh.cargomanagementsystem.model.*;
import ua.koniukh.cargomanagementsystem.model.dto.CargoDTO;
import ua.koniukh.cargomanagementsystem.model.dto.OrderDTO;
import ua.koniukh.cargomanagementsystem.repository.OrderRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;


@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final CalculationService calculationService;
    private final CargoService cargoService;

    public OrderService(OrderRepository orderRepository, UserService userService, CalculationService calculationService, CargoService cargoService) {

        this.orderRepository = orderRepository;
        this.userService = userService;
        this.calculationService = calculationService;
        this.cargoService = cargoService;
    }

    public List<Order> findByProcessedIsFalse() {
        return orderRepository.findByProcessedIsFalse();
    }

    public List<Order> findByProcessedIsTrueAndArchivedIsFalse() {
        return orderRepository.findByProcessedIsTrueAndArchivedIsFalse();
    }

    public List<Order> findByRouteAndOrderPaid(Route routeFrom, Route routeTo, Boolean orderPaid) {
        List<Order> resultList = orderRepository.findByRouteFromAndRouteToAndOrderPaid(routeFrom, routeTo, orderPaid);
        return resultList;
    }


    public Order findById(Long id) {
        return orderRepository.getById(id);
    }

    public List<Order> findAllByArchived(Boolean bool) {
        return orderRepository.findAllByArchived(bool);
    }

    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    public void createOrder(User user, OrderDTO orderDTO) {
        if (orderDTO.getOrderRate() == OrderRate.BASE) {
            Cargo cargo = new Cargo(orderDTO.getCargoDTO());
            cargoService.saveCargo(cargo);
            createBaseOrder(cargo, user, orderDTO);
        } else if (orderDTO.getOrderRate() == OrderRate.CORRESPONDENCE) {
            createCorrespondenceOrder(user, orderDTO);
        }
    }


    public void createBaseOrder(@NotNull Cargo cargo, User user, OrderDTO orderDTO) {
        Order order = Order.builder()
                .user(user)
                .cargo(cargo)
                .orderDate(LocalDate.now())
                .packing(orderDTO.isPacking())
                .processed(false)
                .deliveryDate(null)
                .declaredValue(orderDTO.getDeclaredValue())
                .price(calculationService.calculatePrice(orderDTO))
                .type(orderDTO.getType())
                .deliveryAddress(orderDTO.getDeliveryAddress())
                .orderRate(orderDTO.getOrderRate())
                .routeFrom(orderDTO.getRouteFrom())
                .routeTo(orderDTO.getRouteTo())
                .build();

        saveOrder(order);
    }

    public void createCorrespondenceOrder(@NotNull User user, OrderDTO orderDTO) {
        Order order = Order.builder()
                .user(user)
                .cargo(null)
                .orderDate(LocalDate.now())
                .packing(orderDTO.isPacking())
                .deliveryDate(null)
                .processed(false)
                .declaredValue(orderDTO.getDeclaredValue())
                .price(calculationService.calculatePrice(orderDTO))
                .type(orderDTO.getType())
                .deliveryAddress(orderDTO.getDeliveryAddress())
                .orderRate(orderDTO.getOrderRate())
                .routeFrom(orderDTO.getRouteFrom())
                .routeTo(orderDTO.getRouteTo())
                .build();

        saveOrder(order);
    }

    public void deleteNotProcessedOrder(Long id) {
        orderRepository.existsById(findById(id).getId());
        orderRepository.deleteById(id);
    }

    public void archiveOrders(Long id) {
        Order order = findById(id);
        order.setArchived(true);
        saveOrder(order);
    }

    public void unzipOrders(Long id) {
        Order order = findById(id);
        order.setArchived(false);
        saveOrder(order);
    }


    @Transactional
    public void processedOrder(Long id) {
        Order order = findById(id);
        order.setProcessed(true);
        saveOrder(order);
    }

    @Transactional
    public void orderInvoiceWasPaid(Long id) {
        Order order = findById(id);
        order.setOrderPaid(true);
        order.setDeliveryDate(LocalDate.now().plusDays(calculationService.routeToDate(order.getRouteFrom(), order.getRouteTo())));
        saveOrder(order);
    }
}

