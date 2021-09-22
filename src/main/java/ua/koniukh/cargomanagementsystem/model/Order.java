package ua.koniukh.cargomanagementsystem.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

//    @Column(name = "price")
//    private int price;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "type")
    private String type;

//    @Column(name = "active")
//    private boolean active;
//
//    @Column(name = "packing")
//    private boolean packing;

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