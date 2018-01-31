package com.emaratech;

import java.math.BigDecimal;

public class Card {
    public final String number;
    public BigDecimal balance;

    public Card(String number, BigDecimal balance) {
        this.number = number;
        this.balance = balance;
    }

    @Override
    public int hashCode() {
        return number != null ? number.hashCode() : 0;
    }
}
