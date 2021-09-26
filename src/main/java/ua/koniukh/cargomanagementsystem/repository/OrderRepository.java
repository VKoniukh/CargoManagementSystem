package ua.koniukh.cargomanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.koniukh.cargomanagementsystem.model.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

//    @Query(value = "select * from orders e where e.processed like %:keyword% or e.delivery_date like %:keyword% or e.order_rate like %:keyword% or e.route_from like %:keyword% or e.order_paid like %:keyword%", nativeQuery = true)
//    List<Order> findByKeyword(@Param ("keyword") String keyword);
}
