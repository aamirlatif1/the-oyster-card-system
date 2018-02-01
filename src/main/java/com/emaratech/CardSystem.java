package com.emaratech;


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

    public void checkIn(Card card, Vehicle vehicle, Station station) {
        cardHasSufficientBalance(card, station);
        if(vehicle instanceof Tube) {
            card.balance = card.balance.subtract(station.fareOfZone());
        } else {
            card.balance = card.balance.subtract(vehicle.maxFare());
        }
        Travel travel = new Travel();
        travel.inStation = station;
        travels.put(card, travel);
    }

    private void cardHasSufficientBalance(Card card, Station station) {
        if(card.balance.compareTo(station.fareOfZone()) < 0)
            throw new InsufficentBalanceException("You have not sufficient balance");
    }

    public static BigDecimal getFare(int from, int to) {
        return zoneFares.get(new ZoneKey(from, to));
    }

    public void checkOut(Card card, Vehicle vehicle, Station outStation) {
        Travel travel = travels.get(card);
        if(vehicle instanceof Bus) return;
        BigDecimal fare = calculateFare(travel.inStation, outStation);
        card.balance = card.balance.add(travel.inStation.fareOfZone().subtract(fare));
    }

    private BigDecimal calculateFare(Station inStation, Station outStation) {
        List<BigDecimal> allFares = allFareBetweenStations(inStation, outStation);
        return allFares.stream().max(Comparator.naturalOrder()).get();
    }

    private List<BigDecimal> allFareBetweenStations(Station inStation, Station outStation) {
        List<BigDecimal> allFares = new ArrayList();
        for (int i : inStation.zones)
            for (int j : outStation.zones)
                allFares.add(getFare(i, j));
        return allFares;
    }


}
