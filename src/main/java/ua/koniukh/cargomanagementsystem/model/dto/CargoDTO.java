package ua.koniukh.cargomanagementsystem.model.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.koniukh.cargomanagementsystem.model.OrderRate;
import ua.koniukh.cargomanagementsystem.model.Route;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CargoDTO {

    private String type;

    private int weight;

    private int length;

    private int height;

    private int width;
}