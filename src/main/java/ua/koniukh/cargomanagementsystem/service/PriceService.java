package ua.koniukh.cargomanagementsystem.service;

import org.springframework.stereotype.Service;
import ua.koniukh.cargomanagementsystem.model.OrderRate;
import ua.koniukh.cargomanagementsystem.model.Route;
import ua.koniukh.cargomanagementsystem.model.dto.OrderDTO;

@Service
public class PriceService {

    public double weightToPrice(double weightValue) {
        double interimResult;
        double result;
        if (weightValue <= 2) {
            return result = 40;
        } else if (weightValue <= 5) {
            return result = 50;
        } else if (weightValue <= 10) {
            return result = 60;
        } else if (weightValue <= 20) {
            return result = 70;
        } else if (weightValue < 35) {
            return result = 80;
        } else if (weightValue > 36) {
            interimResult = 80;
            do {
                interimResult = +1.5;
                weightValue++;
            } while (weightValue < 100);
            return result = interimResult;
        } else
            return result = 1000;
    }

    public double calculatePrice(OrderDTO orderDTO) {

        double length;
        double height;
        double width;
        double weight;
        double price = 0;

        if (orderDTO.getOrderRate() == OrderRate.BASE) {
            length = orderDTO.getCargoDTO().getLength();
            height = orderDTO.getCargoDTO().getHeight();
            width = orderDTO.getCargoDTO().getWidth();
            weight = orderDTO.getCargoDTO().getWeight();
            double dimensionalWeight = length * width * height / 5000;

            if (dimensionalWeight > weight) {
                return price = routeToPrice(orderDTO) + weightToPrice(dimensionalWeight) + packingCheck(orderDTO) + declaredValueCheck(orderDTO);
            } else if (weight > dimensionalWeight || weight == dimensionalWeight) {
                return price = routeToPrice(orderDTO) + weightToPrice(weight) + packingCheck(orderDTO) + declaredValueCheck(orderDTO);
            }

        } else if (orderDTO.getOrderRate() == OrderRate.CORRESPONDENCE) {
            return price = routeToPrice(orderDTO) + packingCheck(orderDTO) + declaredValueCheck(orderDTO);
        } else
            return price = 1000;

        return price;
    }

    public double packingCheck(OrderDTO orderDTO) {
        double price = 0;
        if (orderDTO.isPacking()) {
            return price + 10;
        } else
            return price;
    }


    public double declaredValueCheck(OrderDTO orderDTO) {
        double price = 0;
        if (orderDTO.getDeclaredValue() != 0) {
            return price + orderDTO.getDeclaredValue() * 0.01;
        } else
            return price;
    }

    public double routeToPrice(OrderDTO orderDTO) {
        Route routeFrom = orderDTO.getRouteFrom();
        Route routeTo = orderDTO.getRouteTo();
        int from = routeFrom.getDeliveryZone();
        int to = routeTo.getDeliveryZone();
        double routeFromPrice = 0;
        double routeToPrice = 0;
        double routePrice = 0;

        switch (from) {
            case 1:
                routeFromPrice = 5;
                break;
            case 2:
                routeFromPrice = 10;
                break;
            case 3:
                routeFromPrice = 15;
                break;
            default:
                break;
        }

        switch (to) {
            case 1:
                routeToPrice += 5;
                break;
            case 2:
                routeToPrice = 10;
                break;
            case 3:
                routeToPrice = 15;
                break;
            default:
                break;
        }
        return routePrice = routeFromPrice + routeToPrice;
    }
}
