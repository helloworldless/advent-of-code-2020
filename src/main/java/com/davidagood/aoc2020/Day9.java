package com.davidagood.aoc2020;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toList;

public class Day9 {

    public static void main(String[] args) {
        Day9 day9 = new Day9();
        day9.solve();
    }

    private void solve() {
        List<String> lines = FileUtil.readFileLines("day9.txt");
        List<Long> values = lines.stream().map(Long::valueOf).collect(toList());
        int preambleLength = 25;
        int windowSize = 25;

        Long invalidValue = null;
        for (int i = preambleLength; i < values.size(); i++) {
            boolean valid = isValid(values, windowSize, i);
            if (!valid) {
                invalidValue = values.get(i);
                break;
            }
        }

        System.out.println("Part 1: First invalid value=" + invalidValue);

        Optional<Pair<Integer, Integer>> contiguousRange = findContiguousRange(values, invalidValue);

        long sum = part2AnswerFromContiguousRange(values, contiguousRange.get());
        System.out.printf("Part 2: Contiguous range=%s; Sum: %s%n", contiguousRange, sum);
    }

    boolean isValid(List<Long> values, int windowSize, int index) {
        Set<Long> compliments = new HashSet<>();
        Long value = values.get(index);
        for (int i = index - windowSize; i < index; i++) {
            Long currValue = values.get(i);
            if (compliments.contains(currValue)) {
                return true;
            }
            compliments.add(value - currValue);
        }
        return false;
    }

    Optional<Pair<Integer, Integer>> findContiguousRange(List<Long> values, Long invalidNumber) {
        for (int left = 0; left < values.size() - 1; left++) {
            for (int right = left + 1; right < values.size(); right++) {
                long currSum = values.subList(left, right + 1).stream().mapToLong(l -> l).sum();
                if (currSum > invalidNumber) {
                    break;
                }
                if (invalidNumber.equals(currSum)) {
                    return Optional.of(Pair.of(left, right));
                }
            }
        }
        return Optional.empty();
    }

    long part2AnswerFromContiguousRange(List<Long> values, Pair<Integer, Integer> contiguousRange) {
        List<Long> subList = values.subList(contiguousRange.getFirst(), contiguousRange.getSecond() + 1);
        subList.sort(Long::compareTo);
        return subList.get(0) + subList.get(subList.size() - 1);
    }

}
