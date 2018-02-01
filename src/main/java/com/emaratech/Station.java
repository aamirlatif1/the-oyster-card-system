package com.emaratech;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Station {
    public final String station;
    public final int[] zones;

    public Station(String station, int[] zones) {
        this.station = station;
        this.zones = zones;
    }

    public BigDecimal fareOfZone() {
        List<BigDecimal> allFares = new ArrayList();
        for (int i : zones)
            allFares.add(CardSystem.getFare(i, i));

        Optional<BigDecimal> output =  allFares.stream().max(Comparator.naturalOrder());
        BigDecimal fare = BigDecimal.ZERO;
        if(output.isPresent())
            fare = output.get();
        return fare;
    }
}
