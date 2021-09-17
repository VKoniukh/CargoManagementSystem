package ua.koniukh.cargomanagementsystem.model;

import lombok.Data;
import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name= "user_name")
    private String username;

    @Column(name= "password")
    private String password;

    @Column(name= "email")
    private String email;

    @Column(name= "active")
    private boolean active;

    @Column(name= "roles")
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Column(name= "first_name")
    private String firstname;

    @Column(name= "last_name")
    private String lastname;
}
