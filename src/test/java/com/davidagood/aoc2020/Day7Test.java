package com.davidagood.aoc2020;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class Day7Test {

    @Test
    void parseLine() {
        Day7 app = new Day7();
        Day7.Bag bag = app.parseLine("light red bags contain 1 bright white bag, 2 muted yellow bags.");
        List<Pair<Integer, String>> holdBags = List.of(Pair.of(1, "bright white"), Pair.of(2, "muted yellow"));
        Day7.Bag expected = new Day7.Bag("light red", holdBags);
        assertThat(bag).isEqualTo(expected);
    }

    @Test
    void parseBagSpec() {
        Day7 app = new Day7();
        assertThat(app.parseBagSpec("1 bright white bag")).contains(Pair.of(1, "bright white"));
        assertThat(app.parseBagSpec("2 muted yellow bags")).contains(Pair.of(2, "muted yellow"));
    }

    @Test
    void canBagHoldColor() {
        Map<String, Day7.Bag> map = createBagMap();
        Day7 app = new Day7(map);
        assertThat(app.canBagHoldColor("light red", "shiny gold")).isTrue();
        assertThat(app.canBagHoldColor("dark orange", "shiny gold")).isTrue();
        assertThat(app.canBagHoldColor("bright white", "shiny gold")).isTrue();
        assertThat(app.canBagHoldColor("muted yellow", "shiny gold")).isTrue();
        assertThat(app.canBagHoldColor("shiny gold", "shiny gold")).isFalse();
        assertThat(app.canBagHoldColor("dark olive", "shiny gold")).isFalse();
        assertThat(app.canBagHoldColor("vibrant plum", "shiny gold")).isFalse();
        assertThat(app.canBagHoldColor("faded blue", "shiny gold")).isFalse();
        assertThat(app.canBagHoldColor("dotted black", "shiny gold")).isFalse();
    }

    @Test
    void getCountOfBagsThatCanContain() {
        Map<String, Day7.Bag> map = createBagMap();
        Day7 app = new Day7(map);
        assertThat(app.getCountOfBagsThatCanContain("shiny gold")).isEqualTo(4);
    }

    @Test
    void bagsContainedByColor() {
        Map<String, Day7.Bag> map = createBagMap();
        Day7 app = new Day7(map);
        assertThat(app.bagsContainedByColor("shiny gold")).isEqualTo(32);
    }

    Map<String, Day7.Bag> createBagMap() {
        Map<String, Day7.Bag> map = new HashMap<>();
        map.put("light red", new Day7.Bag("light red", List.of(Pair.of(1, "bright white"), Pair.of(2, "muted yellow"))));
        map.put("dark orange", new Day7.Bag("dark orange", List.of(Pair.of(3, "bright white"), Pair.of(4, "muted yellow"))));
        map.put("bright white", new Day7.Bag("bright white", List.of(Pair.of(1, "shiny gold"))));
        map.put("muted yellow", new Day7.Bag("muted yellow", List.of(Pair.of(2, "shiny gold"))));
        map.put("shiny gold", new Day7.Bag("shiny gold", List.of(Pair.of(1, "dark olive"), Pair.of(2, "vibrant plum"))));
        map.put("dark olive", new Day7.Bag("dark olive", List.of(Pair.of(3, "faded blue"), Pair.of(4, "dotted black"))));
        map.put("vibrant plum", new Day7.Bag("vibrant plum", List.of(Pair.of(5, "faded blue"), Pair.of(6, "dotted black"))));
        map.put("faded blue", new Day7.Bag("faded blue"));
        map.put("dotted black", new Day7.Bag("faded blue"));
        return map;
    }

}