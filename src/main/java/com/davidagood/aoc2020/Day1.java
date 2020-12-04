package com.davidagood.aoc2020;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Day1 {
    public static void main(String[] args) throws IOException, URISyntaxException {
        Day1 app = new Day1();
        app.solve();
    }

    void solve() throws URISyntaxException, IOException {
        URL resource = this.getClass().getClassLoader().getResource("day1.txt");
        URI uri = resource.toURI();
        Path path = Path.of(uri);
        List<String> lines = Files.readAllLines(path);
        List<Integer> values = lines.stream().map(Integer::valueOf).collect(Collectors.toList());

        Optional<int[]> pairSumming2020 = this.findPairSumming2020(values);

        if (pairSumming2020.isEmpty()) {
            System.out.println("No pair found which sums to 2020");
        } else {
            int[] pair = pairSumming2020.get();
            System.out.printf("Pair summing 2020; [%s, %s]%n", pair[0], pair[1]);
            System.out.printf("%s * %s == %s%n", pair[0], pair[1], pair[0] * pair[1]);
        }

        Optional<int[]> tripleSumming2020 = this.findTripleSumming2020(values);
        if (tripleSumming2020.isEmpty()) {
            System.out.println("No triple found which sums to 2020");
        } else {
            int[] triple = tripleSumming2020.get();
            System.out.printf("Triple summing 2020; [%s, %s, %s]%n", triple[0], triple[1], triple[2]);
            System.out.printf("%s * %s * %s == %s%n", triple[0], triple[1], triple[2], triple[0] * triple[1] * triple[2]);
        }

    }

    Optional<int[]> findPairSumming2020(List<Integer> input) {
        List<Integer> values = input.stream().sorted().collect(Collectors.toList());
        int front = 0;
        int back = values.size() - 1;
        int targetSum = 2020;

        while (front < back) {

            Integer frontValue = values.get(front);
            Integer backValue = values.get(back);

            int sum = frontValue + backValue;
            if (sum < targetSum) {
                front++;
            } else if (sum > targetSum) {
                back--;
            } else {
                return Optional.of(new int[]{frontValue, backValue});
            }

        }

        return Optional.empty();
    }

    Optional<int[]> findTripleSumming2020(List<Integer> input) {
        List<Integer> values = input.stream().sorted().collect(Collectors.toList());
        int front = 0;
        int back = values.size() - 1;
        int targetSum = 2020;

        while (front < back) {

            Integer frontValue = values.get(front);
            Integer backValue = values.get(back);

            int sum = frontValue + backValue;

            // if sum >= 2020, we need a small number, so decrement the back index
            if (sum >= targetSum) {
                back--;
                continue;
            }
            int innerFront = front + 1;
            while (innerFront < back) {
                Integer innerValue = values.get(innerFront);
                int tripleSum = sum + innerValue;
                if (tripleSum == 2020) {
                    return Optional.of(new int[]{frontValue, backValue, innerValue});
                } else if (tripleSum > 2020) {
                    back--;
                    break;
                } else {
                    innerFront++;
                }
            }

            // we iterated thru the inner loop without exceeding the targetValue so increment the front index
            if (innerFront == back) {
                front++;
            }

        }

        return Optional.empty();
    }

}
