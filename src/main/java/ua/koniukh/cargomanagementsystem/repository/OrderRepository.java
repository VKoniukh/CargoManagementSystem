package ua.koniukh.cargomanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.koniukh.cargomanagementsystem.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
