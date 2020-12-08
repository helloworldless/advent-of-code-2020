package com.davidagood.aoc2020;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

public class Day7 {

    private final Map<String, Bag> bagMap;

    public Day7() {
        this.bagMap = new HashMap<>();
    }

    public Day7(Map<String, Bag> bagMap) {
        this.bagMap = bagMap;
    }

    public static void main(String[] args) {
        Day7 app = new Day7();
        app.solve();
    }

    private void solve() {
        List<String> lines = FileUtil.readFileLines("day7.txt");
        List<Bag> bags = lines.stream().map(this::parseLine).collect(toList());
        bags.forEach(bag -> bagMap.put(bag.getColor(), bag));

        long countOfBags = this.getCountOfBagsThatCanContain("shiny gold");
        System.out.printf("Part 1: Number of bags that can eventually hold a shiny gold bag: %s%n", countOfBags);

        long bagsContainedByColor = this.bagsContainedByColor("shiny gold");
        System.out.printf("Part 2: Number of bags contained by a shiny gold bag: %s%n", bagsContainedByColor);
    }

    Bag parseLine(String line) {
        Pattern pattern = Pattern.compile("^(?<color>.+) bags contain (.+)");
        Matcher matcher = pattern.matcher(line);

        matcher.matches();

        String color = matcher.group("color");
        String rest = matcher.group(2);

        String rawList = rest.substring(0, rest.length() - 1);
        String[] rawBagSpecs = rawList.split(", ");


        List<Pair<Integer, String>> childBags = Arrays.stream(rawBagSpecs)
                .map(this::parseBagSpec)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toList());

        return new Bag(color, childBags);
    }

    Optional<Pair<Integer, String>> parseBagSpec(String rawBagSpec) {
        if ("no other bags".equals(rawBagSpec)) {
            return Optional.empty();
        }
        Pattern bagSpecPattern = Pattern.compile("^(\\d{1,}) (.+) bags?$");
        Matcher bagSpecMatcher = bagSpecPattern.matcher(rawBagSpec);
        bagSpecMatcher.matches();

        try {
            Integer numBags = Integer.valueOf(bagSpecMatcher.group(1));
            String childColor = bagSpecMatcher.group(2);
            return Optional.of(Pair.of(numBags, childColor));
        } catch (IllegalStateException e) {
            System.out.printf("Unexpected input string=%s did not match pattern; Error: %s%n", rawBagSpec, e.getMessage());
            throw e;
        }

    }

    long getCountOfBagsThatCanContain(String color) {
        return this.bagMap.values().stream()
                .map(bag -> this.canBagHoldColor(bag.getColor(), color))
                .mapToInt(canHold -> canHold ? 1 : 0)
                .sum();

    }

    boolean canBagHoldColor(String bagColor, String childColor) {
        return canBagHoldColorHelper(bagColor, childColor, new HashMap<>());
    }

    boolean canBagHoldColorHelper(String bagColor, String childColor, Map<String, Boolean> cache) {

        if (cache.containsKey(bagColor)) {
            return cache.get(bagColor);
        }

        List<Pair<Integer, String>> childBags = bagMap.get(bagColor).getChildBags();

        List<String> childColorsHeld = childBags.stream().map(Pair::getSecond).collect(toList());
        if (childColorsHeld.contains(childColor)) {
            cache.put(bagColor, true);
            return true;
        }

        boolean canHold = false;
        for (Pair<Integer, String> childBag : childBags) {
            boolean canHoldCurr = canBagHoldColorHelper(childBag.getSecond(), childColor, cache);
            canHold = canHold || canHoldCurr;
        }

        cache.put(bagColor, canHold);

        return canHold;
    }

    public long bagsContainedByColor(String color) {
        return bagsContainedByColorHelper(color, new HashMap<>());
    }

    public int bagsContainedByColorHelper(String color, Map<String, Integer> cache) {
        if (cache.containsKey(color)) {
            return cache.get(color);
        }

        List<Pair<Integer, String>> childBags = bagMap.get(color).getChildBags();

        int count = childBags.stream().map(Pair::getFirst).reduce(0, Integer::sum);

        for (Pair<Integer, String> childBag : childBags) {
            count += childBag.getFirst() * bagsContainedByColorHelper(childBag.getSecond(), cache);
        }

        cache.put(color, count);

        return count;
    }

    @Value
    @AllArgsConstructor
    public static class Bag {
        String color;
        List<Pair<Integer, String>> childBags;

        public Bag(String color) {
            this.color = color;
            this.childBags = Collections.emptyList();
        }

    }

}
