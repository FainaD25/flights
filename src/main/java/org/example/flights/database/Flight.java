package org.example.flights.database;

public class Flight {
    private static int flightId = 0;
    private int id;
    private String from;
    private String to;
    private double duration;
    private String date;
    private double price;

    public int getId() {
        return id;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public double getDuration() {
        return duration;
    }

    public String getDate() {
        return date;
    }

    public double getPrice() {
        return price;
    }

    public Flight (String from, String to, double duration, String date, double price) {
        this.id = flightId++;
        this.from = from;
        this.to = to;
        this.duration = duration;
        this.date = date;
        this.price = price;
    }
}
