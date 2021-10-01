package ua.koniukh.cargomanagementsystem.service.impl;

import com.sun.istack.NotNull;
import org.springframework.stereotype.Service;
import ua.koniukh.cargomanagementsystem.model.*;
import ua.koniukh.cargomanagementsystem.model.dto.OrderDTO;
import ua.koniukh.cargomanagementsystem.repository.OrderRepository;
import ua.koniukh.cargomanagementsystem.service.OrderService;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CalculationServiceImpl calculationService;
    private final CargoServiceImpl cargoService;

    public OrderServiceImpl(OrderRepository orderRepository, CalculationServiceImpl calculationService, CargoServiceImpl cargoService) {

        this.orderRepository = orderRepository;
        this.calculationService = calculationService;
        this.cargoService = cargoService;
    }

    @Override
    public List<Order> findByProcessedIsFalse() {
        return orderRepository.findByProcessedIsFalse();
    }

    @Override
    public List<Order> findByProcessedIsTrueAndArchivedIsFalse() {
        return orderRepository.findByProcessedIsTrueAndArchivedIsFalse();
    }

    @Override
    public List<Order> getByArchivedAndOrderPaidAndRouteFromAndRouteTo(Boolean bool, Boolean orderPaid, Route routeFrom, Route routeTo){
        return orderRepository.findByArchivedAndOrderPaidAndRouteFromAndRouteTo(bool,orderPaid,routeFrom,routeTo);
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.getById(id);
    }

    @Override
    public List<Order> findAllByArchived(Boolean bool) {
        return orderRepository.findAllByArchived(bool);
    }

    @Override
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void createOrder(User user, OrderDTO orderDTO) {
        if (orderDTO.getOrderRate() == OrderRate.BASE) {
            Cargo cargo = new Cargo(orderDTO.getCargoDTO());
            cargoService.saveCargo(cargo);
            createBaseOrder(cargo, user, orderDTO);
        } else if (orderDTO.getOrderRate() == OrderRate.CORRESPONDENCE) {
            createCorrespondenceOrder(user, orderDTO);
        }
    }

    @Override
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

    @Override
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

    @Override
    public void deleteNotProcessedOrder(Long id) {
        try {
            if (findById(id).getCargo() == null) {
                orderRepository.existsById(id);
                orderRepository.deleteById(id);
            }
            orderRepository.existsById(id);
            cargoService.deleteById(findById(id).getCargo().getId());
        }catch (EntityNotFoundException exception) {
            System.out.println("Unfortunately, there was an error:"+ exception.getMessage());
        }
    }

    @Override
    public void archiveOrders(Long id) {
        Order order = findById(id);
        order.setArchived(true);
        saveOrder(order);
    }

    @Override
    public void unzipOrders(Long id) {
        Order order = findById(id);
        order.setArchived(false);
        saveOrder(order);
    }

    @Override
    @Transactional
    public void processedOrder(Long id) {
        Order order = findById(id);
        order.setProcessed(true);
        saveOrder(order);
    }

    @Override
    @Transactional
    public void orderInvoiceWasPaid(Long id) {
        Order order = findById(id);
        order.setOrderPaid(true);
        order.setDeliveryDate(LocalDate.now().plusDays(calculationService.routeToDate(order.getRouteFrom(), order.getRouteTo())));
        saveOrder(order);
    }

    @Override
    public List<Order> findAllByProcessedAndUserId (Boolean bool, long id) {
        return orderRepository.findAllByProcessedAndUserId(bool, id);
    }
}

