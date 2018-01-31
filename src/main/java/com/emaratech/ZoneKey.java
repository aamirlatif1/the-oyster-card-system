package com.emaratech;

public class ZoneKey {
    public int from;
    public int to;

    public ZoneKey(int from, int to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ZoneKey)) return false;

        ZoneKey zoneKey = (ZoneKey) o;

        if (from != zoneKey.from) return false;
        return to == zoneKey.to;
    }

    @Override
    public int hashCode() {
        int result = from;
        result = 31 * result + to;
        return result;
    }
}
