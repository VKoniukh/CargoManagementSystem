package ua.koniukh.cargomanagementsystem.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Data
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

    private double price;

    private LocalDate date;

    private String type;

    private double declaredValue;

    private boolean packing;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderRate orderRate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Route routeFrom;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Route routeTo;
}