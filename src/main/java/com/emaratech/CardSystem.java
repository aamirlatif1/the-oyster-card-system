package com.emaratech;


import javax.naming.InsufficientResourcesException;

import java.math.BigDecimal;
import java.util.*;

public class CardSystem {

    public static Map<ZoneKey, BigDecimal> zoneFares = Collections.unmodifiableMap(
            new HashMap<ZoneKey, BigDecimal>() {{
            put(new ZoneKey(1, 1), BigDecimal.valueOf(2.50));
            put(new ZoneKey(1, 2), BigDecimal.valueOf(3.00));
            put(new ZoneKey(1, 3), BigDecimal.valueOf(3.20));

            put(new ZoneKey(2, 1), BigDecimal.valueOf(3.00));
            put(new ZoneKey(2, 2), BigDecimal.valueOf(2.00));
            put(new ZoneKey(2, 3), BigDecimal.valueOf(2.25));

            put(new ZoneKey(3, 1), BigDecimal.valueOf(3.20));
            put(new ZoneKey(3, 2), BigDecimal.valueOf(2.25));
            put(new ZoneKey(3, 3), BigDecimal.valueOf(2.00));
        }
    });

    private Map<Card, Travel> travels = new HashMap<>();

    public void checkIn(Card card, Vehicle vehicle) {
        card.balance = card.balance.subtract(vehicle.maxFare());
    }

    public void checkIn(Card card, Vehicle vehicle, Station station) {
        if(card.balance.compareTo(station.fareOfZone()) < 0)
            throw new InsufficentBalanceException("You have not sufficent balance");
        card.balance = card.balance.subtract(station.fareOfZone());
        Travel travel = new Travel();
        travel.inStation = station;
        travels.put(card, travel);
    }

    public void checkOut(Card card, Vehicle vehicle, Station outStation) {
        Travel travel = travels.get(card);
        BigDecimal fare = calculateFare(travel.inStation, outStation);
        card.balance = card.balance.add(travel.inStation.fareOfZone().subtract(fare));
    }

    private BigDecimal calculateFare(Station inStation, Station outStation) {
        List<BigDecimal> allFares = new ArrayList();
        for (int i : inStation.zones){
            for (int j : outStation.zones){
                allFares.add(getFare(i, j));
            }
        }
        Optional<BigDecimal> output =  allFares.stream().max(Comparator.naturalOrder());
        BigDecimal fare = BigDecimal.ZERO;
        if(output.isPresent())
            fare = output.get();
        return fare;
    }

    public static BigDecimal getFare(int from, int to) {
        return zoneFares.get(new ZoneKey(from, to));
    }


}
