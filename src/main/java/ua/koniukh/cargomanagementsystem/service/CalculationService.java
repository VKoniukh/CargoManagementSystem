package ua.koniukh.cargomanagementsystem.service;

import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import ua.koniukh.cargomanagementsystem.model.OrderRate;
import ua.koniukh.cargomanagementsystem.model.Route;
import ua.koniukh.cargomanagementsystem.model.dto.OrderDTO;

@Service
public class CalculationService {

  public BigDecimal weightToPrice(double weightValue) {
    double interimResult;
    BigDecimal priceResult = BigDecimal.valueOf(0);
    if (weightValue <= 2) {
      return priceResult = BigDecimal.valueOf(30);
    } else if (weightValue <= 10) {
      return priceResult = BigDecimal.valueOf(35);
    } else if (weightValue <= 15) {
      return priceResult = BigDecimal.valueOf(40);
    } else if (weightValue <= 25) {
      return priceResult = BigDecimal.valueOf(45);
    } else if (weightValue < 40) {
      return priceResult = BigDecimal.valueOf(50);
    } else if (weightValue > 41) {
      interimResult = 50;
      do {
        interimResult += 1.5;
        weightValue++;
      } while (weightValue < 200);
      return priceResult = BigDecimal.valueOf(interimResult);
    } else return priceResult = BigDecimal.valueOf(1000);
  }

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
        return price =
            routeToPrice(orderDTO)
                .add(
                    weightToPrice(dimensionalWeight)
                        .add(packingCheck(orderDTO).add(declaredValueCheck(orderDTO))));
      } else if (weight > dimensionalWeight || weight == dimensionalWeight) {
        return price =
            routeToPrice(orderDTO)
                .add(
                    weightToPrice(weight)
                        .add(packingCheck(orderDTO).add(declaredValueCheck(orderDTO))));
      }

    } else if (orderDTO.getOrderRate() == OrderRate.CORRESPONDENCE) {
      return price =
          routeToPrice(orderDTO).add(packingCheck(orderDTO).add(declaredValueCheck(orderDTO)));
    } else return price = BigDecimal.valueOf(1000);

    return price;
  }

  public BigDecimal packingCheck(OrderDTO orderDTO) {
    BigDecimal priceResult = BigDecimal.valueOf(0);
    if (orderDTO.isPacking()) {
      return priceResult = BigDecimal.valueOf(10);
    } else return priceResult;
  }

  public BigDecimal declaredValueCheck(OrderDTO orderDTO) {
    BigDecimal priceResult = BigDecimal.valueOf(0);
    if (orderDTO.getDeclaredValue() != 0) {
      return priceResult = BigDecimal.valueOf(orderDTO.getDeclaredValue() * 0.005);
    } else return priceResult;
  }

  public BigDecimal routeToPrice(OrderDTO orderDTO) {
    BigDecimal priceFrom = BigDecimal.valueOf(0);
    BigDecimal priceTo = BigDecimal.valueOf(0);
    BigDecimal priceResult = BigDecimal.valueOf(0);

    switch (orderDTO.getRouteFrom().getDeliveryZone()) {
      case 1:
        priceFrom = BigDecimal.valueOf(5);
        break;
      case 2:
        priceFrom = BigDecimal.valueOf(10);
        break;
      case 3:
        priceFrom = BigDecimal.valueOf(15);
        break;
      default:
        break;
    }

    switch (orderDTO.getRouteTo().getDeliveryZone()) {
      case 1:
        priceTo = BigDecimal.valueOf(5);
        break;
      case 2:
        priceTo = BigDecimal.valueOf(10);
        break;
      case 3:
        priceTo = BigDecimal.valueOf(15);
        break;
      default:
        break;
    }
    return priceFrom.add(priceTo);
  }

  public long routeToDate(Route routeFrom, Route routeTo) {
    float dayFrom = 0;
    float dayTo = 0;
    long finalResult = 0;

    if (routeFrom.name().equals(routeTo.name())) {
      finalResult = 1;
    } else {
      switch (routeFrom.getDeliveryZone()) {
        case 1:
          dayFrom = 1;
          break;
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
          break;
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
