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

    private int weight;

    private int length;

    private int height;

    private int width;
}