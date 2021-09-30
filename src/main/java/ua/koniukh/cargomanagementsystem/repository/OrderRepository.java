package ua.koniukh.cargomanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.koniukh.cargomanagementsystem.model.Order;
import ua.koniukh.cargomanagementsystem.model.Route;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByProcessedIsFalse();

    List<Order> findByProcessedIsTrueAndArchivedIsFalse();

    List<Order> findByRouteFromAndRouteToAndOrderPaid(Route routeFrom, Route routeTo, Boolean orderPaid);

    List<Order> findAllByArchived (Boolean bool);

    List<Order> findAllByProcessedAndUserId(Boolean bool, long id);
}
