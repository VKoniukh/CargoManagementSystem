package ua.koniukh.cargomanagementsystem.model.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.koniukh.cargomanagementsystem.model.OrderRate;
import ua.koniukh.cargomanagementsystem.model.Route;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private CargoDTO cargoDTO;

    private String date;

    private String type;

    private boolean packing;

    private double declaredValue;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderRate orderRate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Route routeFrom;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Route routeTo;
}