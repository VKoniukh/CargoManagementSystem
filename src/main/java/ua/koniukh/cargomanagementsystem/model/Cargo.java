package ua.koniukh.cargomanagementsystem.model;

import javax.persistence.Column;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "cargos")
public class Cargo {

    @OneToMany(mappedBy = "primaryCargo", fetch = FetchType.EAGER)
    private Collection<Order> ordersCollection;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "type")
    private String type;

    @Column(name = "weight")
    private int weight;

    @Column(name = "lenght")
    private int length;

    @Column(name = "height")
    private int height;

    @Column(name = "width")
    private int width;

    @Column(name = "active")
    private boolean active;
//
//    @Column(name= "roles")
//    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
//    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
//    @Enumerated(EnumType.STRING)
//    private Set<Role> roles;

}
