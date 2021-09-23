package ua.koniukh.cargomanagementsystem.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CargoDTO {

    private double weight;

    private double length;

    private double height;

    private double width;
}