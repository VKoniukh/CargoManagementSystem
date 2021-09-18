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

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column(name= "first_name")
    private String firstname;

    @Column(name= "last_name")
    private String lastname;
}
