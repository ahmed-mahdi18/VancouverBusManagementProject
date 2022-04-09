package com.ahmed.busmanagement;

public class BusStops {

    int stopId;
    String stopName;

    BusStops(int id, String name) {
        this.stopId = id;
        this.stopName = name;
    }

    public String getStopName() {
        return stopName;
    }

    public int getStopId() {
        return stopId;
    }
}

