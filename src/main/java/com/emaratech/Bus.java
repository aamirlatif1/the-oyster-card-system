package com.emaratech;

import java.math.BigDecimal;

public class Bus implements Vehicle {

    public Bus(String b12) {
    }

    @Override
    public BigDecimal maxFare() {
        return BigDecimal.valueOf(1.80);
    }
}
