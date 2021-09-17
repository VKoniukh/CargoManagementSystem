package ua.koniukh.cargomanagementsystem.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name= "first_name")
    private String firstname;
    @Column(name= "last_name")
    private String lastname;
    @Column(name= "email")
    private String email;
    @Column(name= "password")
    private String password;
}
