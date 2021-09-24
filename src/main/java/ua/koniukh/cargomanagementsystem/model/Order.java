package ua.koniukh.cargomanagementsystem.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "orders")
public class Order {

    @OneToOne
    private Cargo cargo;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private BigDecimal price;

    //todo create custom validator
    private LocalDate date;

    @Size(min = 3, max = 30, message = "Please write what exactly will be in the parcel")
    private String type;

    private double declaredValue;

    private boolean packing;

    @Enumerated(EnumType.STRING)
    private OrderRate orderRate;

    @Enumerated(EnumType.STRING)
    private Route routeFrom;

    @Enumerated(EnumType.STRING)
    private Route routeTo;

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getDeclaredValue() {
        return declaredValue;
    }

    public void setDeclaredValue(double declaredValue) {
        this.declaredValue = declaredValue;
    }

    public boolean isPacking() {
        return packing;
    }

    public void setPacking(boolean packing) {
        this.packing = packing;
    }

    public OrderRate getOrderRate() {
        return orderRate;
    }

    public void setOrderRate(OrderRate orderRate) {
        this.orderRate = orderRate;
    }

    public Route getRouteFrom() {
        return routeFrom;
    }

    public void setRouteFrom(Route routeFrom) {
        this.routeFrom = routeFrom;
    }

    public Route getRouteTo() {
        return routeTo;
    }

    public void setRouteTo(Route routeTo) {
        this.routeTo = routeTo;
    }
}