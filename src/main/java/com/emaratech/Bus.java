package com.emaratech;

import java.math.BigDecimal;

public class Bus implements Vehicle {

    @Override
    public BigDecimal maxFare() {
        return BigDecimal.valueOf(1.80);
    }
}
