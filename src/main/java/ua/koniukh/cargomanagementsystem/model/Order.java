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

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private BigDecimal price;

    private LocalDate orderDate;

    private LocalDate deliveryDate;

    @Size(min = 3, max = 30, message = "Please write what exactly will be in the parcel")
    private String type;

    @Size(min = 4, max = 60, message = "Please write delivery address")
    private String deliveryAddress;

    private double declaredValue;

    private boolean packing;

    private boolean orderPaid;

    private boolean processed;

    @Enumerated(EnumType.STRING)
    private OrderRate orderRate;

    @Enumerated(EnumType.STRING)
    private Route routeFrom;

    @Enumerated(EnumType.STRING)
    private Route routeTo;

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public boolean isOrderPaid() {
        return orderPaid;
    }

    public void setOrderPaid(boolean orderPaid) {
        this.orderPaid = orderPaid;
    }

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

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate date) {
        this.orderDate = date;
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

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean approved) {
        this.processed = approved;
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