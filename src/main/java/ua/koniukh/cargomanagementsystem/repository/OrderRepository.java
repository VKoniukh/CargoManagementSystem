package ua.koniukh.cargomanagementsystem.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.koniukh.cargomanagementsystem.model.Order;
import ua.koniukh.cargomanagementsystem.model.Route;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

  List<Order> findAll();

  List<Order> findByDeliveryDate(LocalDate localDate);

  List<Order> findByRouteFrom(Route route);

  List<Order> findByRouteTo(Route route);

  List<Order> findByOrderPaidIsTrueOrOrderPaidIsFalse();
}
