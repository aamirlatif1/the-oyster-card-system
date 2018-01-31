package com.emaratech;

import java.math.BigDecimal;

public class Tube implements Vehicle {

    @Override
    public BigDecimal maxFare() {
        return BigDecimal.valueOf(3.20);
    }

}
