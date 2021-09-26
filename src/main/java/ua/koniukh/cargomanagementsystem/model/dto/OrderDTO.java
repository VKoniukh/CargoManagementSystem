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
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private CargoDTO cargoDTO;

    @Size(min = 3, max = 30, message = "Please write what exactly will be in the parcel")
    private String type;

    private boolean packing;

    private double declaredValue;

    private LocalDate possibleDeliveryDate;

    private BigDecimal price;

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