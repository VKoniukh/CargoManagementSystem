package ua.koniukh.cargomanagementsystem.model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;


@Data
@Entity
@Table(name = "orders")
public class Order {

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private Order primaryUser;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "CARGO_ID")
    private Cargo primaryCargo;

    @OneToMany(mappedBy = "primaryOrder", fetch = FetchType.EAGER)
    private Collection<User> usersCollection;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "price")
    private int price;

    @Column(name = "date")
    private int date;

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

//    @Column(name = "cargo")
//    @ElementCollection(targetClass = Cargo.class, fetch = FetchType.EAGER)
//    @CollectionTable(name = "cargo", joinColumns = @JoinColumn(name =))
//    private Set<Cargo> cargo;

// TODO connection with user

//    @Column(name= "roles")
//    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
//    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
//    @Enumerated(EnumType.STRING)
//    private Set<Role> roles;

}