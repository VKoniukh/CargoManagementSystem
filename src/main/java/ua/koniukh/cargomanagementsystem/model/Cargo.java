package ua.koniukh.cargomanagementsystem.model;

import javax.persistence.Column;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Data
@Entity
@Table(name = "cargos")
public class Cargo {

    @OneToOne(mappedBy = "cargo", cascade = CascadeType.ALL)
    private Order order;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "type")
    private String type;

    @Column(name = "weight")
    private int weight;

    @Column(name = "length")
    private int length;

    @Column(name = "height")
    private int height;

    @Column(name = "width")
    private int width;
}
