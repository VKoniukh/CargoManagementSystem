package ua.koniukh.cargomanagementsystem.service.impl;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
class CalculationServiceImplTest {

    @Autowired
    private CalculationServiceImpl calculationService;
//
//    @Test
//    public void weightToPrice() {
//        BigDecimal bigDecimal1 = BigDecimal.valueOf(281);
//        BigDecimal bigDecimal2 = calculationService.weightToPrice(200);
//        assertEquals(bigDecimal1);
//    }
}