package com.davidagood.aoc2020;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day2Test {

    @Test
    void parseLineTest() {
        Day2 app = new Day2();

        Pair<PasswordPolicy, String> policyPasswordPair = app.parseLine("1-3 a: abcde");
        assertThat(policyPasswordPair).isEqualTo(Pair.of(PasswordPolicy.of(1, 3, 'a'), "abcde"));

        Pair<PasswordPolicy, String> policyPasswordPair2 = app.parseLine("1-3 b: cdefg");
        assertThat(policyPasswordPair2).isEqualTo(Pair.of(PasswordPolicy.of(1, 3, 'b'), "cdefg"));

        Pair<PasswordPolicy, String> policyPasswordPair3 = app.parseLine("2-9 c: ccccccccc");
        assertThat(policyPasswordPair3).isEqualTo(Pair.of(PasswordPolicy.of(2, 9, 'c'), "ccccccccc"));
    }

    @Test
    void charCount() {
        Day2 app = new Day2();

        int[] expectedCounts = new int[26];
        expectedCounts[0] = 3;
        assertThat(app.charCounts("aaa")).containsExactlyInAnyOrder(expectedCounts);

        int[] expectedCounts2 = new int[26];
        expectedCounts[0] = 3;
        expectedCounts['z' - 'a'] = 3;
        assertThat(app.charCounts("zazaza")).containsExactlyInAnyOrder(expectedCounts);
    }

    @Test
    void isPasswordCompliant() {
        Day2 app = new Day2();

        assertThat(app.isPasswordCompliant("a", PasswordPolicy.of(1, 1, 'a'))).isTrue();
        assertThat(app.isPasswordCompliant("aa", PasswordPolicy.of(1, 1, 'a'))).isFalse();
        assertThat(app.isPasswordCompliant("aa", PasswordPolicy.of(1, 2, 'a'))).isTrue();
        assertThat(app.isPasswordCompliant("attta", PasswordPolicy.of(1, 10, 't'))).isTrue();
    }

    @Test
    void isPasswordCompliantPart2() {
        Day2 app = new Day2();

        assertThat(app.isPasswordCompliantPart2("ab", PasswordPolicy.of(1, 2, 'a'))).isTrue();
        assertThat(app.isPasswordCompliantPart2("aa", PasswordPolicy.of(1, 2, 'a'))).isFalse();
        assertThat(app.isPasswordCompliantPart2("bb", PasswordPolicy.of(1, 2, 'a'))).isFalse();
        assertThat(app.isPasswordCompliantPart2("attta", PasswordPolicy.of(1, 4, 'a'))).isTrue();
    }
}