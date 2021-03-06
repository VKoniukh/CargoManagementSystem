package ua.koniukh.cargomanagementsystem.service.impl;

import org.springframework.stereotype.Service;
import ua.koniukh.cargomanagementsystem.model.OrderRate;
import ua.koniukh.cargomanagementsystem.model.Route;
import ua.koniukh.cargomanagementsystem.model.dto.OrderDTO;
import ua.koniukh.cargomanagementsystem.service.CalculationService;

import java.math.BigDecimal;

/**
 * <h1>Calculation service</h1>
 * The Calculation service implements a possibility
 * of calculating the values necessary for the program needs.
 * <p>
 *
 * @author  VKoniukh
 * @version 1.0
 * @since   2021-10-05
 */

@Service
public class CalculationServiceImpl implements CalculationService {

    /**
     * This method generates the part of the price for delivery
     * that depends on the weight measure that is passed to this function
     * @param weightValue = the weight measured to that function
     * @return the big decimal part of price
     */
    @Override
    public BigDecimal weightToPrice(double weightValue) {
        double counter;
        double interimResult;
        BigDecimal priceResult = BigDecimal.valueOf(0);
        if (weightValue <= 2) {
            return priceResult = BigDecimal.valueOf(35);
        } else if (weightValue <= 5) {
            return priceResult = BigDecimal.valueOf(40);
        } else if (weightValue <= 15) {
            return priceResult = BigDecimal.valueOf(45);
        } else if (weightValue <= 25) {
            return priceResult = BigDecimal.valueOf(50);
        } else if (weightValue < 35) {
            return priceResult = BigDecimal.valueOf(55);
        } else if (weightValue > 40) {
            interimResult = 55;
            counter = 41;
            do {
                interimResult += 1.5;
                counter++;
            } while (counter != weightValue || counter > 400);
            return priceResult = BigDecimal.valueOf(interimResult);
        } else
            return priceResult = BigDecimal.valueOf(1001);
    }

    /**
     * This method generates a price for the order.
     * Accepts the OrderDTO argument and calculates the weight index
     * by comparing the overall weight and the actual weight. By adding
     * the calculation results of other methods, returns the actual order price.
     * @param orderDTO = information about order from User
     * @return the big decimal actual order price
     */
    @Override
    public BigDecimal calculatePrice(OrderDTO orderDTO) {

        double length;
        double height;
        double width;
        double weight;
        double dimensionalWeight;
        BigDecimal price = BigDecimal.valueOf(0);

        if (orderDTO.getOrderRate() == OrderRate.BASE) {
            length = orderDTO.getCargoDTO().getLength();
            height = orderDTO.getCargoDTO().getHeight();
            width = orderDTO.getCargoDTO().getWidth();
            weight = orderDTO.getCargoDTO().getWeight();
            dimensionalWeight = length * width * height / 5000;

            if (dimensionalWeight > weight) {
                return price = routeToPrice(orderDTO).add(weightToPrice(dimensionalWeight).add(packingCheck(orderDTO).add(declaredValueCheck(orderDTO))));
            } else if (weight > dimensionalWeight || weight == dimensionalWeight) {
                return price = routeToPrice(orderDTO).add(weightToPrice(weight).add(packingCheck(orderDTO).add(declaredValueCheck(orderDTO))));
            }

        } else if (orderDTO.getOrderRate() == OrderRate.CORRESPONDENCE) {
            return price = routeToPrice(orderDTO).add(packingCheck(orderDTO).add(declaredValueCheck(orderDTO)));
        } else
            return price = BigDecimal.valueOf(1001);
        return price;
    }

    /**
     * This method generates a port of the
     * total order price that depends on whether the order is to be packed.
     * @param orderDTO = information about order from User
     * @return the big decimal part of price
     */
    @Override
    public BigDecimal packingCheck(OrderDTO orderDTO) {
        BigDecimal priceResult = BigDecimal.valueOf(0);
        if (orderDTO.isPacking()) {
            return priceResult = BigDecimal.valueOf(10);
        } else
            return priceResult;
    }

    /**
     * This method generates a port of the
     * total order price that depends on whether the order has declared Value.
     * @param orderDTO = information about order from User
     * @return the big decimal part of price
     */
    @Override
    public BigDecimal declaredValueCheck(OrderDTO orderDTO) {
        BigDecimal priceResult = BigDecimal.valueOf(0);
        if (orderDTO.getDeclaredValue() != 0) {
            return priceResult = BigDecimal.valueOf(orderDTO.getDeclaredValue() * 0.005);
        } else
            return priceResult;
    }

    /**
     * This method generates a port of the
     * total order price that depends on order route and zone of the delivery.
     * @param orderDTO = information about order from User
     * @return the big decimal part of price
     */
    @Override
    public BigDecimal routeToPrice(OrderDTO orderDTO) {
        BigDecimal priceFrom = BigDecimal.valueOf(0);
        BigDecimal priceTo = BigDecimal.valueOf(0);
        BigDecimal priceResult = BigDecimal.valueOf(0);

        switch (orderDTO.getRouteFrom().getDeliveryZone()) {
            case 1:
                priceFrom = BigDecimal.valueOf(10);
                break;
            case 2:
                priceFrom = BigDecimal.valueOf(15);
                break;
            case 3:
                priceFrom = BigDecimal.valueOf(20);
                break;
            default:
                break;
        }

        switch (orderDTO.getRouteTo().getDeliveryZone()) {
            case 1:
                priceTo = BigDecimal.valueOf(10);
                break;
            case 2:
                priceTo = BigDecimal.valueOf(15);
                break;
            case 3:
                priceTo = BigDecimal.valueOf(20);
                break;
            default:
                break;
        }
        return priceResult = priceFrom.add(priceTo);
    }

    /**
     * This method generates a delivery date that depends on
     * the parameters (FROM and TO delivery points).
     * Based on the delivery zones, the actual delivery date is calculated and returned
     * @param routeFrom = the delivery points where the package was sent
     * @param routeTo = the delivery points where the package will be received
     * @return = the long value which represents the number of days needed to deliver the order.
     */
    @Override
    public long routeToDate (Route routeFrom, Route routeTo) {
        float dayFrom = 0;
        float dayTo = 0;
        long finalResult = 0;

        if (routeFrom.name().equals(routeTo.name())) {
            finalResult = 1;
        } else {
            switch (routeFrom.getDeliveryZone()) {
                case 1:
                    dayFrom = 1;

                case 2:
                    dayFrom = 1.5F;
                    break;
                case 3:
                    dayFrom = 2;
                    break;
                default:
                    break;
            }

            switch (routeTo.getDeliveryZone()) {
                case 1:
                    dayTo = 1;

                case 2:
                    dayTo = 1.5F;
                    break;
                case 3:
                    dayTo = 2;
                    break;
                default:
                    break;
            }
            float floatResult = dayFrom + dayTo;
            finalResult = Math.round(floatResult);
        }
        return finalResult;
    }
}
