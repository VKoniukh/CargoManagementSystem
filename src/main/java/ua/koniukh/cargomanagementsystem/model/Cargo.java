package ua.koniukh.cargomanagementsystem.model;


import javax.persistence.*;
import lombok.*;
import ua.koniukh.cargomanagementsystem.model.dto.CargoDTO;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "cargos")
public class Cargo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @OneToOne(mappedBy = "cargo", cascade = CascadeType.ALL)
  private Order order;

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

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public double getWeight() {
    return weight;
  }

  public void setWeight(double weight) {
    this.weight = weight;
  }

  public double getLength() {
    return length;
  }

  public void setLength(double length) {
    this.length = length;
  }

  public double getHeight() {
    return height;
  }

  public void setHeight(double height) {
    this.height = height;
  }

  public double getWidth() {
    return width;
  }

  public void setWidth(double width) {
    this.width = width;
  }
}
