package com.davidagood.aoc2020;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Day1Test {

    @Test
    void testFindPairSumming2020() {
        Day1 app = new Day1();

        assertTrue((app.findPairSumming2020(Collections.emptyList()).isEmpty()));
        assertArrayEquals(new int[]{1}, new int[]{1});

        var values2 = List.of(1, 2019);
        assertArrayEquals(new int[]{1, 2019}, app.findPairSumming2020(values2).get());

        var values3 = List.of(1, 2, 2018, 2020, 2021);
        assertArrayEquals(new int[]{2, 2018}, app.findPairSumming2020(values3).get());

        var values4 = List.of(1, 2, 2017, 2020, 2021);
        assertTrue(app.findPairSumming2020(values4).isEmpty());

        var values5 = List.of(1721, 979, 366, 299, 675, 1456);
        assertThat(app.findPairSumming2020(values5).get()).containsExactlyInAnyOrder(1721, 299);
    }

    @Test
    void testFindTripleSumming2020() {
        Day1 app = new Day1();

        assertThat(app.findTripleSumming2020(List.of())).isEmpty();

        var values2 = List.of(1721, 979, 366, 299, 675, 1456); // 299 366 675 979 1456 1721
        assertThat(app.findTripleSumming2020(values2).get()).containsExactlyInAnyOrder(979, 366, 675);

        var values3 = List.of(100, 200, 220, 300, 1700, 1800, 2001, 3001);
        assertThat(app.findTripleSumming2020(values3).get()).containsExactlyInAnyOrder(100, 220, 1700);
    }

}