package ua.koniukh.cargomanagementsystem.model;

import javax.persistence.Column;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "cargos")
public class Cargo {

    @ManyToOne
    @JoinColumn(name="order_id", nullable=false)
    private Order order;

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
