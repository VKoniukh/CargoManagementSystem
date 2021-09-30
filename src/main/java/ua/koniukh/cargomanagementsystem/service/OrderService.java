package ua.koniukh.cargomanagementsystem.service;

import com.sun.istack.NotNull;
import ua.koniukh.cargomanagementsystem.model.*;
import ua.koniukh.cargomanagementsystem.model.dto.OrderDTO;

import java.util.List;

public interface OrderService {

    List<Order> findByProcessedIsFalse();

    List<Order> findByProcessedIsTrueAndArchivedIsFalse();

    List<Order> findByRouteAndOrderPaid(Route routeFrom, Route routeTo, Boolean orderPaid);

    Order findById(Long id);

    List<Order> findAllByArchived(Boolean bool);

    void saveOrder(Order order);

    void createOrder(User user, OrderDTO orderDTO);

    void createBaseOrder(@NotNull Cargo cargo, User user, OrderDTO orderDTO);

    void createCorrespondenceOrder(@NotNull User user, OrderDTO orderDTO);

    void deleteNotProcessedOrder(Long id);

    void archiveOrders(Long id);

    void unzipOrders(Long id);

    void processedOrder(Long id);

    void orderInvoiceWasPaid(Long id);
}