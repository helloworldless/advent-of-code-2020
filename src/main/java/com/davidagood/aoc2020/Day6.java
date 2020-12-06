package com.davidagood.aoc2020;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class Day6 {

    public static void main(String[] args) {
        Day6 app = new Day6();
        app.solve();
    }

    private void solve() {
        String rawInput = FileUtil.readFileAsString("day6.txt");
        int sumOfYesAnswers = getSumOfYesAnswersFromRawInput(rawInput);
        System.out.println("Part 1: Sum of yes answers: " + sumOfYesAnswers);

        List<String> rawGroups = splitIntoRawGroups(rawInput);
        List<List<String>> rawGroupList = parseRawGroups(rawGroups);
        int allYesAnswersSum = rawGroupList.stream().map(this::calculateAllYesAnswersCountForGroup).mapToInt(Long::intValue).sum();
        System.out.println("Part 2: Sum of all yes answers: " + allYesAnswersSum);
    }

    int getSumOfYesAnswersFromRawInput(String rawInput) {
        List<String> rawGroups = splitIntoRawGroups(rawInput);
        List<List<String>> rawGroupList = parseRawGroups(rawGroups);
        List<String> groupAnswers = rawGroupList.stream().map(this::extractUniqueAnswersFromRawGroup).collect(toList());
        return groupAnswers.stream().mapToInt(String::length).sum();
    }

    List<String> splitIntoRawGroups(String rawInput) {
        return Arrays.asList(rawInput.split("\n\n"));
    }

    List<List<String>> parseRawGroups(List<String> rawGroups) {
        return rawGroups.stream().map(s -> Arrays.asList(s.split("\n"))).collect(toList());
    }

    String extractUniqueAnswersFromRawGroup(List<String> rawGroup) {
        Set<Character> uniqueChars = new HashSet<>();
        for (String groupPart : rawGroup) {
            for (char c : groupPart.toCharArray()) {
                uniqueChars.add(c);
            }
        }
        return uniqueChars.stream().map(String::valueOf).collect(Collectors.joining(""));
    }

    long calculateAllYesAnswersCountForGroup(List<String> rawGroupLines) {
        int groupSize = rawGroupLines.size();
        int[] charCounts = new int[26];

        for (String singlePersonAnswers : rawGroupLines) {
            for (char c : singlePersonAnswers.toCharArray()) {
                charCounts[c - 'a']++;
            }
        }

        return Arrays.stream(charCounts).filter(count -> count == groupSize).count();
    }

}
