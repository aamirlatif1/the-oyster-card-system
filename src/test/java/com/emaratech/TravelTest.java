package com.emaratech;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class TravelTest {

    static final BigDecimal cardBalance = BigDecimal.valueOf(30.00);

    static final Bus bus = new Bus();
    static final Tube tube = new Tube();

    static final Station chelsea = new Station("Chelsea", new int[]{1});
    static final Station holborn = new Station("Holborn", new int[]{1});
    static final Station earlCourt = new Station("Earl's Court", new int[]{1, 2});
    static final Station hammersmith = new Station("Hammersmith", new int[]{2});

    CardSystem cardSystem;
    Card card;

    static final BigDecimal holbornToEarlsCoourt = CardSystem.getFare(1, 2);
    static final BigDecimal holbornToHammsmit = CardSystem.getFare(1, 2);
    static final BigDecimal earlsCourtToHammsmit = CardSystem.getFare(1, 2);


    @Before
    public void setUp() throws Exception {
        card = new Card("1234", BigDecimal.valueOf(30.00));
        cardSystem = new CardSystem();
    }

    @Test
    public void checkInBus() throws Exception {
        cardSystem.checkIn(card, bus, chelsea);
        assertEquals(cardBalance.subtract(bus.maxFare()), card.balance);
    }


    @Test
    public void travelWithinSameZone() throws Exception {
        cardSystem.checkIn(card, tube, holborn);
        assertEquals(cardBalance.subtract(holborn.fareOfZone()), card.balance);
        cardSystem.checkOut(card, tube, earlCourt);
        assertEquals(cardBalance.subtract(holbornToEarlsCoourt), card.balance);
    }

    @Test
    public void travelFromZoneOneToTwo() throws Exception {
        cardSystem.checkIn(card, tube, holborn);
        assertEquals(cardBalance.subtract(holborn.fareOfZone()), card.balance);
        cardSystem.checkOut(card, tube, hammersmith);
        assertEquals(cardBalance.subtract(holbornToHammsmit), card.balance);
    }

    @Test
    public void travelMultipleStations() throws Exception {
        cardSystem.checkIn(card, tube, holborn);
        cardSystem.checkOut(card, tube, earlCourt);

        cardSystem.checkIn(card, bus, chelsea);
        cardSystem.checkOut(card, tube, hammersmith);

        cardSystem.checkIn(card, tube, earlCourt);
        cardSystem.checkOut(card, bus, hammersmith);

        assertEquals(cardBalance.subtract(holbornToEarlsCoourt.add(bus.maxFare()).add(earlsCourtToHammsmit)), card.balance);
    }

    @Test(expected = InsufficentBalanceException.class)
    public void insufficientBalanceForTube() throws Exception {
        Card card = new Card("222", BigDecimal.valueOf(1.0));
        cardSystem.checkIn(card, tube, holborn);
    }

    @Test(expected = InsufficentBalanceException.class)
    public void insufficientBalanceForBus() throws Exception {
        Card card = new Card("333", BigDecimal.valueOf(1.0));
        cardSystem.checkIn(card, bus, holborn);
    }

}
