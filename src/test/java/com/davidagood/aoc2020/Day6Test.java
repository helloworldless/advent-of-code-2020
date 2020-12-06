package com.davidagood.aoc2020;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day6Test {

    @Test
    void splitIntoGroups() {
        Day6 app = new Day6();
        String rawInput = "abc\nabc\n\ndef";
        List<String> groups = app.splitIntoRawGroups(rawInput);
        assertThat(groups).isEqualTo(List.of("abc\nabc", "def"));
    }

    @Test
    void parseRawGroups() {
        Day6 app = new Day6();
        List<String> rawGroups = List.of("abc\nabc", "def");
        List<List<String>> parsedGroups = app.parseRawGroups(rawGroups);
        assertThat(parsedGroups).isEqualTo(List.of(List.of("abc", "abc"), List.of("def")));
    }

    @Test
    void extractUniqueAnswersFromRawGroup() {
        Day6 app = new Day6();
        List<String> rawGroup = List.of("abc", "abc");
        String uniqueAnswers = app.extractUniqueAnswersFromRawGroup(rawGroup);
        assertThat(uniqueAnswers).isEqualTo("abc");
    }

    @Test
    void getSumOfYesAnswersFromRawInput() {
        Day6 app = new Day6();
        String rawInput = "abc\naz\n\nadefg\nh\n\nq";
        int sumOfUniqueAnswers = app.getSumOfYesAnswersFromRawInput(rawInput);
        assertThat(sumOfUniqueAnswers).isEqualTo(11);
    }

    @Test
    void calculateAllYesAnswersCountForGroup() {
        Day6 app = new Day6();
        assertThat(app.calculateAllYesAnswersCountForGroup(List.of("abc", "ade", "az"))).isEqualTo(1);
        assertThat(app.calculateAllYesAnswersCountForGroup(List.of("abc"))).isEqualTo(3);
        assertThat(app.calculateAllYesAnswersCountForGroup(List.of("abc", "def"))).isEqualTo(0);
    }

}