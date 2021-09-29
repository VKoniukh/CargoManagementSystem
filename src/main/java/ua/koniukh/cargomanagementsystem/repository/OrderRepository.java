package ua.koniukh.cargomanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.koniukh.cargomanagementsystem.model.Order;
import ua.koniukh.cargomanagementsystem.model.Route;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByProcessedIsFalse();

    List<Order> findByProcessedIsTrueAndArchivedIsFalse();

    List<Order> findByRouteFromAndRouteToAndOrderPaid(Route routeFrom, Route routeTo, Boolean orderPaid);

    List<Order> findAllByArchived (Boolean bool);
}
