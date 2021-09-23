package ua.koniukh.cargomanagementsystem.model;

import javax.persistence.Column;

import lombok.*;
import ua.koniukh.cargomanagementsystem.model.dto.CargoDTO;

import javax.persistence.*;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Data
@Table(name = "cargos")
public class Cargo {

    @OneToOne(mappedBy = "cargo", cascade = CascadeType.ALL)
    private Order order;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double weight;

    private double length;

    private double height;

    private double width;

    public Cargo(CargoDTO cargoDTO) {
        this.weight = cargoDTO.getWeight();
        this.length = cargoDTO.getLength();
        this.height = cargoDTO.getHeight();
        this.width = cargoDTO.getWidth();
    }
}
