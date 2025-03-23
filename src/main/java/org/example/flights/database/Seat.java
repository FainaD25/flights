package org.example.flights.database;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Schema(description = "Data transfer object for seat")
public class Seat {
    @Schema(description = "Seat row", example = "1")
    private final int row;
    @Schema(description = "Seat column", example = "B")
    private final String column;
    @Schema(description = "Unique combination of row and column", example = "1B")
    private final String id;
    @Schema(description = "True if the seat is near the window", example = "true")
    private final boolean windowSeat;
    @Schema(description = "True if the seat has extra leg space", example = "true")
    private final boolean legSpace;
    @Schema(description = "True if the seat is near the exit", example = "true")
    private final boolean nearExit;
    @Setter
    @Schema(description = "True if the seat is booked", example = "true")

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
