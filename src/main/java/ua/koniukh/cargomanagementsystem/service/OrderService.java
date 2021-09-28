package ua.koniukh.cargomanagementsystem.service;

import com.sun.istack.NotNull;
import java.time.LocalDate;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ua.koniukh.cargomanagementsystem.model.*;
import ua.koniukh.cargomanagementsystem.model.dto.OrderDTO;
import ua.koniukh.cargomanagementsystem.repository.CargoRepository;
import ua.koniukh.cargomanagementsystem.repository.InvoiceRepository;
import ua.koniukh.cargomanagementsystem.repository.OrderRepository;
import ua.koniukh.cargomanagementsystem.repository.UserRepository;

@Service
public class OrderService {

  private final InvoiceRepository invoiceRepository;
  private final CargoRepository cargoRepository;
  private final UserRepository userRepository;
  private final OrderRepository orderRepository;
  private final UserService userService;
  private final CalculationService calculationService;
  private final CargoService cargoService;
  private final InvoiceService invoiceService;

  public OrderService(
      InvoiceRepository invoiceRepository,
      CargoRepository cargoRepository,
      UserRepository userRepository,
      OrderRepository orderRepository,
      UserService userService,
      CalculationService calculationService,
      CargoService cargoService,
      InvoiceService invoiceService) {
    this.invoiceRepository = invoiceRepository;
    this.cargoRepository = cargoRepository;
    this.userRepository = userRepository;
    this.orderRepository = orderRepository;
    this.userService = userService;
    this.calculationService = calculationService;
    this.cargoService = cargoService;
    this.invoiceService = invoiceService;
  }

  public List<Order> findByDeliveryDate(LocalDate localDate) {
    return orderRepository.findByDeliveryDate(localDate);
  }

  public List<Order> findByRouteFrom(Route route) {
    return orderRepository.findByRouteFrom(route);
  }

  public List<Order> findByRouteTo(Route route) {
    return orderRepository.findByRouteTo(route);
  }

  public List<Order> findByOrderPaidIsTrueOrOrderPaidIsFalse() {
    return orderRepository.findByOrderPaidIsTrueOrOrderPaidIsFalse();
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

  public Order createBaseOrder(
      @NotNull Cargo cargo, Authentication authentication, OrderDTO orderDTO) {
    Order order =
        Order.builder()
            .user(userService.getCurrentUser(authentication))
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

    return order;
  }

  public Order createOrder(@NotNull Authentication authentication, OrderDTO orderDTO) {
    Order order =
        Order.builder()
            .user(userService.getCurrentUser(authentication))
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

    return order;
  }

  // todo write own exeption + CONFIGURATE DELETION FUNCTIONAL
  //    @Transactional
  //    public void deleteById(Long orderId, User user) {
  //        List<Invoice> invoiceList = new ArrayList<>(user.getInvoices());
  //        if (userRepository.existsById(user.getId())) {
  //            try {
  //                userRepository.deleteById(user.getId());
  //            } catch (NullPointerException e) {
  //                e.printStackTrace();
  //            }
  //        }
  //        try {
  //            if (orderRepository.existsById(orderId) & findById(orderId).getCargo().getId() == 0)
  // {
  //                orderRepository.deleteById(orderId);
  //            } else if (cargoRepository.existsById(findById(orderId).getCargo().getId())) {
  //                cargoService.deleteById(findById(orderId).getCargo().getId());
  //            }
  //        } catch (NullPointerException e) {
  //            e.printStackTrace();
  //        }
  //        if (!invoiceList.isEmpty()) {
  //            for (Invoice invoice : invoiceList) {
  //                if (invoiceRepository.existsById(invoice.getId())) {
  //                    invoiceRepository.deleteById(invoice.getId());
  //                }
  //            }
  //        }
  //
  ////        try {
  ////            if (invoiceRepository.existsById(invoiceService.findByOrderId(id).getId())) {
  ////                invoiceService.deleteById(invoiceService.findByOrderId(id).getId());
  ////            }
  ////        } catch (NullPointerException e) {
  ////            e.printStackTrace();
  ////        }
  //    }
  //
  //    @Transactional
  //    public void deleteUserById(Long id) {
  //        User user = userService.findById(id);
  //        List<Order> orderList = new ArrayList<>(user.getOrders());
  //        if (orderList.isEmpty() & userRepository.existsById(userService.findById(id).getId())) {
  //            try {
  //                userRepository.deleteById(id);
  //            } catch (NullPointerException e) {
  //                e.printStackTrace();
  //            }
  //        }
  //        for (Order order : orderList) {
  //            deleteById(order.getId(), user);
  //        }
  //    }

  @Transactional
  public void processedOrder(Long id) {
    Order order = findById(id);
    order.setProcessed(true);
    saveOrder(order);
  }

  @Transactional
  public void invoiceWasPaid(Long id) {
    Order order = findById(id);
    order.setOrderPaid(true);
    order.setDeliveryDate(
        LocalDate.now()
            .plusDays(calculationService.routeToDate(order.getRouteFrom(), order.getRouteTo())));
    saveOrder(order);
  }
}
