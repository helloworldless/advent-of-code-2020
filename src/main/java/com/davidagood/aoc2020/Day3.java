package com.davidagood.aoc2020;

import lombok.Value;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day3 {

    private static final String TREE = "#";

    public static void main(String[] args) throws IOException, URISyntaxException {
        Day3 app = new Day3();
        app.solve();
    }

    void solve() throws URISyntaxException, IOException {
        URL resource = getClass().getClassLoader().getResource("day3.txt");
        List<String> lines = Files.readAllLines(Path.of(resource.toURI()));
        Grid grid = linesToGrid(lines);

        int[][] pairs = new int[][]{{1, 1}, {3, 1}, {5, 1}, {7, 1}, {1, 2}};
        long totalMultiplied = 1;
        for (int[] pair : pairs) {
            int colOffset = pair[0];
            int rowOffset = pair[1];
            int treeCount = grid.treeCountForTrajectory(colOffset, rowOffset);
            System.out.printf("For trajectory rowOffset=%s, colOffset=%s, tree count=%s%n", rowOffset, colOffset, treeCount);
            totalMultiplied *= treeCount;
        }

        System.out.println("Total tree count multiplied: " + totalMultiplied);

    }

    Grid linesToGrid(List<String> lines) {
        List<List<String>> linesLists = lines.stream().map(line -> line.split("")).map(Arrays::asList).collect(Collectors.toList());
        return new Grid(linesLists);
    }

    @Value
    public static class Grid {
        int rowCount;
        int colCount;
        List<List<String>> grid;

        public Grid(List<List<String>> grid) {
            this.grid = grid;
            this.rowCount = grid.size();
            this.colCount = grid.get(0).size();
        }

        public boolean isTree(int row, int col) {
            int wrappedCol = wrapColumnIndex(col);
            return TREE.equals(grid.get(row).get(wrappedCol));
        }

        int wrapColumnIndex(int col) {
            return col > colCount - 1 ? col % colCount : col;
        }

        int treeCountForTrajectory(int colOffset, int rowOffset) {
            int row = rowOffset;
            int col = colOffset;

            int treeCount = 0;

            while (row < this.rowCount) {
                if (isTree(row, col)) {
                    treeCount++;
                }

                row += rowOffset;
                col += colOffset;
            }

            System.out.println("Max row reached: " + row);
            System.out.println("Max col reached: " + col);
            return treeCount;
        }
    }

}
