package com.davidagood.aoc2020;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day2 {

    public static void main(String[] args) throws IOException, URISyntaxException {
        Day2 app = new Day2();
        app.solve();
    }

    private void solve() throws URISyntaxException, IOException {
        URL resource = getClass().getClassLoader().getResource("day2.txt");
        List<String> lines = Files.readAllLines(Path.of(resource.toURI()));

        long compliantCount = lines.stream().map(this::parseLine).filter(this::isPasswordCompliant).count();
        System.out.println("Part 1 - Compliant count: " + compliantCount);

        long compliantCountPart2 = lines.stream().map(this::parseLine).filter(this::isPasswordCompliantPart2).count();
        System.out.println("Part 2 - Compliant count: " + compliantCountPart2);
    }

    public Pair<PasswordPolicy, String> parseLine(String line) {
        Pattern pattern = Pattern.compile("(?<min>\\d+)-(?<max>\\d+) (?<letter>\\w): (?<password>.*)");
        Matcher matcher = pattern.matcher(line);

        matcher.matches();
        String min = matcher.group("min");
        String max = matcher.group("max");
        String letter = matcher.group("letter");
        String password = matcher.group("password");

        return Pair.of(PasswordPolicy.of(Integer.parseInt(min), Integer.parseInt(max), letter.charAt(0)), password);
    }

    public int[] charCounts(String lowerCaseChars) {
        int[] counts = new int[26];
        for (char c : lowerCaseChars.toCharArray()) {
            counts[c - 'a']++;
        }
        return counts;
    }

    public boolean isPasswordCompliant(Pair<PasswordPolicy, String> policyPasswordPair) {
        return this.isPasswordCompliant(policyPasswordPair.getSecond(), policyPasswordPair.getFirst());
    }

    public boolean isPasswordCompliantPart2(Pair<PasswordPolicy, String> policyPasswordPair) {
        return this.isPasswordCompliantPart2(policyPasswordPair.getSecond(), policyPasswordPair.getFirst());
    }

    public boolean isPasswordCompliant(String password, PasswordPolicy policy) {
        int[] counts = charCounts(password);
        char letter = policy.getLetter();
        int charCount = counts[letter - 'a'];
        return charCount >= policy.getMin() && charCount <= policy.getMax();
    }

    public boolean isPasswordCompliantPart2(String password, PasswordPolicy policy) {
        char first = password.charAt(policy.getMin() - 1);
        char second = password.charAt(policy.getMax() - 1);

        boolean firstMatch = first == policy.getLetter();
        boolean secondMatch = second == policy.getLetter();

        return firstMatch ^ secondMatch;
    }
}
