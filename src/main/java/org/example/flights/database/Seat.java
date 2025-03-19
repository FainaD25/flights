package org.example.flights.database;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Seat {
    private final int row;
    private final String column;
    private final String id;
    private final boolean windowSeat;
    private final boolean legSpace;
    private final boolean nearExit;
    @Setter
    private boolean taken;
    public Seat(int row, String column) {
        this.row = row;
        this.column = column;
        this.id = row + column;
        this.windowSeat = (column.equals("A") || column.equals("F")) && row % 2 == 1;
        this.legSpace = row == 1 || row == 11;
        this.nearExit = row == 1 || row == 10 || row == 11 || row == 20;
        this.taken = false;
    }
}
