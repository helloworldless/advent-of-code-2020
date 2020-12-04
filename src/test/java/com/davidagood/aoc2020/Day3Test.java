package com.davidagood.aoc2020;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day3Test {

    @Test
    void linesToGrid() {
        Day3 app = new Day3();
        String[] linesArray = ("..##.......\n" +
                "#...#...#..\n" +
                ".#....#..#.\n" +
                "..#.#...#.#\n" +
                ".#...##..#.\n" +
                "..#.##.....\n" +
                ".#.#.#....#\n" +
                ".#........#\n" +
                "#.##...#...\n" +
                "#...##....#\n" +
                ".#..#...#.#").split("\n");
        List<List<String>> grid = app.linesToGrid(Arrays.asList(linesArray)).getGrid();
        assertThat(grid.size()).isEqualTo(11);
        assertThat(grid.get(0)).isEqualTo(List.of(".", ".", "#", "#", ".", ".", ".", ".", ".", ".", "."));
        assertThat(grid.get(10)).isEqualTo(List.of(".", "#", ".", ".", "#", ".", ".", ".", "#", ".", "#"));
    }

    @Test
    void isTree() {
        var grid = new Day3.Grid(List.of(
                List.of(".", "#", "."), // (".", "#", "."),
                List.of(".", ".", "."), // (".", ".", "."),
                List.of("#", ".", "#"), // ("#", ".", "#"),
                List.of(".", "#", "."), // (".", "#", "."),
                List.of(".", ".", "."), // (".", ".", "."),
                List.of("#", ".", "#")  // ("#", ".", "#"))
        ));
        assertThat(grid.isTree(0, 0)).isFalse();
        assertThat(grid.isTree(0, 1)).isTrue();
        assertThat(grid.isTree(0, 2)).isFalse();

        assertThat(grid.isTree(1, 0)).isFalse();
        assertThat(grid.isTree(1, 1)).isFalse();
        assertThat(grid.isTree(1, 2)).isFalse();

        assertThat(grid.isTree(2, 0)).isTrue();
        assertThat(grid.isTree(2, 1)).isFalse();
        assertThat(grid.isTree(2, 2)).isTrue();

        // Column wrap test
        assertThat(grid.isTree(0, 3)).isFalse();
        assertThat(grid.isTree(0, 4)).isTrue();
        assertThat(grid.isTree(0, 5)).isFalse();

        assertThat(grid.isTree(5, 3)).isTrue();
        assertThat(grid.isTree(5, 4)).isFalse();
        assertThat(grid.isTree(5, 5)).isTrue();
    }

    @Test
    void wrapColumnIndex() {
        var grid = new Day3.Grid(List.of(
                List.of(".", "#", "."),
                List.of(".", ".", "."),
                List.of("#", ".", "#")));
        assertThat(grid.wrapColumnIndex(0)).isEqualTo(0);
        assertThat(grid.wrapColumnIndex(1)).isEqualTo(1);
        assertThat(grid.wrapColumnIndex(2)).isEqualTo(2);
        assertThat(grid.wrapColumnIndex(3)).isEqualTo(0);
        assertThat(grid.wrapColumnIndex(4)).isEqualTo(1);
        assertThat(grid.wrapColumnIndex(5)).isEqualTo(2);
        assertThat(grid.wrapColumnIndex(6)).isEqualTo(0);
    }

    @Test
    void treeCountForTrajectory() {
        Day3 app = new Day3();

        String[] linesArray = ("..##.........##.........##.........##.........##.........##.......\n" +
                "#...#...#..#...#...#..#...#...#..#...#...#..#...#...#..#...#...#..\n" +
                ".#....#..#..#....#..#..#....#..#..#....#..#..#....#..#..#....#..#.\n" +
                "..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#\n" +
                ".#...##..#..#...##..#..#...##..#..#...##..#..#...##..#..#...##..#.\n" +
                "..#.##.......#.##.......#.##.......#.##.......#.##.......#.##.....\n" +
                ".#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#\n" +
                ".#........#.#........#.#........#.#........#.#........#.#........#\n" +
                "#.##...#...#.##...#...#.##...#...#.##...#...#.##...#...#.##...#...\n" +
                "#...##....##...##....##...##....##...##....##...##....##...##....#\n" +
                ".#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#").split("\n");

        Day3.Grid grid = app.linesToGrid(Arrays.asList(linesArray));

        assertThat(grid.treeCountForTrajectory(1, 1)).isEqualTo(2);
        assertThat(grid.treeCountForTrajectory(1, 3)).isEqualTo(7);
        assertThat(grid.treeCountForTrajectory(1, 5)).isEqualTo(3);
        assertThat(grid.treeCountForTrajectory(1, 7)).isEqualTo(4);
        assertThat(grid.treeCountForTrajectory(2, 1)).isEqualTo(2);
    }

}