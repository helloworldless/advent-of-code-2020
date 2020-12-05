package com.davidagood.aoc2020;

import lombok.Value;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class Day5 {

    public static void main(String[] args) {
        Day5 app = new Day5();
        app.solve();
    }

    private void solve() {
        List<String> rawSeatCodes = FileUtil.readFileLines("day5.txt");
        List<Seat> seats = rawSeatCodes.stream().map(Seat::fromRawSeatCode).collect(Collectors.toList());
        int highestSeatId = getHighestSeatId(seats);
        System.out.println("Part 1: Highest seat id: " + highestSeatId);

        seats.sort(Comparator.comparingInt(Seat::getSeatId));

        int prevSeatId = seats.get(0).getSeatId();
        for (int i = 1; i < seats.size(); i++) {
            int currSeatId = seats.get(i).getSeatId();

            if (currSeatId - prevSeatId == 2) {
                System.out.println("Part 2: Your seat id: " + (currSeatId - 1));
                return;
            }

            prevSeatId = currSeatId;
        }

        System.out.println("Part 2: Failed to find your seat id");
    }

    public int getHighestSeatId(List<Seat> seats) {
        if (isNull(seats) || seats.isEmpty()) {
            throw new RuntimeException("List must not be null or empty");
        }
        return seats.stream().map(Seat::getSeatId).max(Comparator.comparingInt(s -> s)).get();
    }

    @Value
    public static class Seat {
        int col;
        int row;

        static int binaryPartition(String rawSeatCodePart, char lowerChar, char upperChar) {
            int min = 0;
            int max = (1 << rawSeatCodePart.length()) - 1;

            for (int i = 0; i < rawSeatCodePart.length(); i++) {
                char nextChar = rawSeatCodePart.charAt(i);
                if (nextChar == lowerChar) {
                    max = min + ((max - min) / 2);
                } else if (nextChar == upperChar) {
                    min = min + ((max - min) / 2) + 1;
                } else {
                    throw new RuntimeException("Unexpected character: " + nextChar);
                }
            }

            return rawSeatCodePart.charAt(rawSeatCodePart.length() - 1) == 'L' ? min : max;
        }

        public static Seat from(int col, int row) {
            return new Seat(col, row);
        }

        public static Seat fromRawSeatCode(String rawSeatCode) {
            int col = Seat.binaryPartition(rawSeatCode.substring(0, 7), 'F', 'B');
            int row = Seat.binaryPartition(rawSeatCode.substring(7), 'L', 'R');
            return new Seat(col, row);
        }

        public int getSeatId() {
            return (this.col * 8) + this.row;
        }
    }

}
