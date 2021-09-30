package ua.koniukh.cargomanagementsystem.service;


import ua.koniukh.cargomanagementsystem.model.Route;
import ua.koniukh.cargomanagementsystem.model.dto.OrderDTO;

import java.math.BigDecimal;

public interface CalculationService {

    BigDecimal weightToPrice (double weightValue);

    BigDecimal calculatePrice(OrderDTO orderDTO);

    BigDecimal packingCheck(OrderDTO orderDTO);

    BigDecimal declaredValueCheck(OrderDTO orderDTO);

    BigDecimal routeToPrice(OrderDTO orderDTO);

    long routeToDate (Route routeFrom, Route routeTo);
}
