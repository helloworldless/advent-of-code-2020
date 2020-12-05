package com.davidagood.aoc2020;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day5Test {

    @Test
    void calculateSeatId() {
        assertThat(Day5.Seat.from(44, 5).getSeatId()).isEqualTo(357);
        assertThat(Day5.Seat.from(70, 7).getSeatId()).isEqualTo(567);
        assertThat(Day5.Seat.from(14, 7).getSeatId()).isEqualTo(119);
        assertThat(Day5.Seat.from(102, 4).getSeatId()).isEqualTo(820);
    }

    @Test
    void getHighestSeatId() {
        Day5 app = new Day5();
        List<Day5.Seat> seats = List.of(Day5.Seat.from(44, 5),
                Day5.Seat.from(70, 7),
                Day5.Seat.from(14, 7),
                Day5.Seat.from(102, 4));
        int highestSeatId = app.getHighestSeatId(seats);
        assertThat(highestSeatId).isEqualTo(820);
    }

    @Test
    void parseRow() {
        String rawSeatCode = "FBFBBFFRLR";
        String rawSeatCodeColPart = rawSeatCode.substring(0, 7);
        String rawSeatCodeRowPart = rawSeatCode.substring(7);
        assertThat(Day5.Seat.binaryPartition(rawSeatCodeColPart, 'F', 'B')).isEqualTo(44);
        assertThat(Day5.Seat.binaryPartition(rawSeatCodeRowPart, 'L', 'R')).isEqualTo(5);
    }

}