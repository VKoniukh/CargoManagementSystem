package ua.koniukh.cargomanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.koniukh.cargomanagementsystem.model.Order;
import ua.koniukh.cargomanagementsystem.model.User;

public interface OrderRepository extends JpaRepository<Order, Long> {

}