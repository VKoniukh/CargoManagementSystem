package ua.koniukh.cargomanagementsystem.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.koniukh.cargomanagementsystem.model.*;
import ua.koniukh.cargomanagementsystem.model.dto.CargoDTO;
import ua.koniukh.cargomanagementsystem.model.dto.OrderDTO;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
class CalculationServiceImplTest {
    @Autowired
    private CalculationServiceImpl calculationService;

    @Test
    public void weightToPrice() {
        double weightValue = 200;
        BigDecimal result = calculationService.weightToPrice(weightValue);
        assertEquals(BigDecimal.valueOf(293.5), result);
    }

    @Test
    public void calculatePrice() {
        User user = new User();
        CargoDTO cargoDTO = CargoDTO.builder()
                .height(60)
                .length(140)
                .width(20)
                .weight(50)
                .build();
        OrderDTO orderDTO1 = OrderDTO.builder()
                .cargoDTO(cargoDTO)
                .orderRate(OrderRate.BASE)
                .declaredValue(1500)
                .packing(true)
                .routeTo(Route.KYIV)
                .routeFrom(Route.ODESSA)
                .build();
        BigDecimal result1 = calculationService.calculatePrice(orderDTO1);
        assertEquals(BigDecimal.valueOf(111.00), result1);

        OrderDTO orderDTO2 = OrderDTO.builder()
                .orderRate(OrderRate.CORRESPONDENCE)
                .declaredValue(1500)
                .packing(true)
                .routeTo(Route.KYIV)
                .routeFrom(Route.ODESSA)
                .build();
        BigDecimal result2 = calculationService.calculatePrice(orderDTO2);
        assertEquals(BigDecimal.valueOf(42.50), result2);
    }

    @Test
    public void packingCheck() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setPacking(true);
        BigDecimal result = calculationService.packingCheck(orderDTO);
        assertEquals(BigDecimal.valueOf(10), result);
    }

    @Test
    public void declaredValueCheck() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setDeclaredValue(0);
        BigDecimal result1 = calculationService.declaredValueCheck(orderDTO);
        assertEquals(BigDecimal.valueOf(0), result1);

        orderDTO.setDeclaredValue(1500);
        BigDecimal result2 = calculationService.declaredValueCheck(orderDTO);
        assertEquals(BigDecimal.valueOf(7.5), result2);
    }

    @Test
    public void routeToPrice() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setRouteFrom(Route.ODESSA);
        orderDTO.setRouteTo(Route.KYIV);
        BigDecimal result1 = calculationService.routeToPrice(orderDTO);
        assertEquals(BigDecimal.valueOf(25), result1);
    }

    @Test
    public void routeToDate() {
        long check = 1;
        long result = calculationService.routeToDate(Route.KYIV, Route.KYIV);
        assertEquals(1, result);

        check = 4;
        result = calculationService.routeToDate(Route.BRODY, Route.DUBNO);
        assertEquals(4, result);
    }
}