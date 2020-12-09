package com.davidagood.aoc2020;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

class Day9Test {

    @Test
    void isValid() {
        Day9 app = new Day9();
        List<Long> values = getTestValues();
        int window = 5;
        for (int i = 5; i < values.size(); i++) {
            if (values.get(i).equals(127L)) {
                assertThat(app.isValid(values, window, i)).isFalse();

            } else {
                assertThat(app.isValid(values, window, i)).isTrue();
            }
        }
    }

    @Test
    void findContiguousRange() {
        Day9 app = new Day9();
        List<Long> values = getTestValues();
        Pair<Integer, Integer> contiguousRange = Pair.of(2, 5);
        assertThat(app.findContiguousRange(values, 127L)).contains(contiguousRange);
        assertThat(app.part2AnswerFromContiguousRange(values, contiguousRange)).isEqualTo(62L);
    }

    private List<Long> getTestValues() {
        String[] lines = ("35\n" +
                "20\n" +
                "15\n" +
                "25\n" +
                "47\n" +
                "40\n" +
                "62\n" +
                "55\n" +
                "65\n" +
                "95\n" +
                "102\n" +
                "117\n" +
                "150\n" +
                "182\n" +
                "127\n" +
                "219\n" +
                "299\n" +
                "277\n" +
                "309\n" +
                "576").split("\n");

        return Arrays.stream(lines).map(Long::valueOf).collect(toList());
    }

}