package com.davidagood.aoc2020;

import com.davidagood.aoc2020.day4.Passport;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.BiConsumer;

import static java.util.stream.Collectors.toList;

public class Day4 {

    public static void main(String[] args) {
        Day4 app = new Day4();
        app.solve();
    }

    private void solve() {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        List<String> lines = FileUtil.readFileLines("day4.txt");
        List<String> parsePassportLines = parsePassportLines(lines);
        List<Passport> passports = parsePassportLines.stream()
                .map(this::parsePassportLine)
                .collect(toList());

        List<Passport> validPassportsPart1 = passports.stream().filter(passport -> {
            Set<ConstraintViolation<Passport>> constraintViolations = validator.validate(passport, Part1.class);
            return constraintViolations.isEmpty();
        }).collect(toList());

        System.out.println("Valid passport count (part 1): " + validPassportsPart1.size());

        List<Passport> validPassportsPart2 = validPassportsPart1.stream().filter(passport -> {
            Set<ConstraintViolation<Passport>> constraintViolations = validator.validate(passport, Part2.class);
            return constraintViolations.isEmpty();
        }).collect(toList());
        System.out.println("Valid passport count (part 2): " + validPassportsPart2.size());
    }

    List<String> parsePassportLines(List<String> lines) {
        List<String> passportLines = new ArrayList<>();

        int i = 0;
        StringJoiner sj = new StringJoiner(" ");
        while (i < lines.size()) {

            String line = lines.get(i);

            if (line.isEmpty()) {
                passportLines.add(sj.toString());
                sj = new StringJoiner(" ");
            } else {
                sj.add(line);
            }

            i++;
        }

        // capture the final details
        passportLines.add(sj.toString());
        return passportLines;
    }

    public Passport parsePassportLine(String rawPassportLine) {
        List<Pair<String, String>> passportAttributePairs = Arrays.stream(rawPassportLine.split(" "))
                .map(this::parseRawPair).collect(toList());

        Map<String, BiConsumer<Passport.PassportBuilder, String>> map = new HashMap<>();
        map.put("byr", (builder, rawValue) -> builder.birthYear(Integer.valueOf(rawValue)));
        map.put("iyr", (builder, rawValue) -> builder.issueYear(Integer.valueOf(rawValue)));
        map.put("eyr", (builder, rawValue) -> builder.expirationYear(Integer.valueOf(rawValue)));
        map.put("hgt", Passport.PassportBuilder::height);
        map.put("hcl", Passport.PassportBuilder::hairColor);
        map.put("ecl", Passport.PassportBuilder::eyeColor);
        map.put("pid", Passport.PassportBuilder::passportId);
        map.put("cid", Passport.PassportBuilder::countryId);

        Passport.PassportBuilder passportBuilder = Passport.builder();

        passportAttributePairs.forEach(pair -> {
            BiConsumer<Passport.PassportBuilder, String> biConsumer = map.get(pair.getFirst());
            biConsumer.accept(passportBuilder, pair.getSecond());
        });

        return passportBuilder.build();
    }

    Pair<String, String> parseRawPair(String rawPair) {
        String[] parts = rawPair.split(":");
        return Pair.of(parts[0], parts[1]);
    }

    public interface Part1 {
    }

    public interface Part2 {
    }

}
