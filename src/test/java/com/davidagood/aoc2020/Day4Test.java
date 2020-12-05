package com.davidagood.aoc2020;

import com.davidagood.aoc2020.day4.Passport;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class Day4Test {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    private static Passport createValidPassport() {
        return Passport.builder()
                .passportId("688706448")
                .issueYear(2017)
                .height("162cm")
                .countryId("174")
                .eyeColor("grn")
                .birthYear(1943)
                .hairColor("#808e9e")
                .expirationYear(2025)
                .build();
    }

    @Test
    void parsePassportLines() {
        Day4 app = new Day4();
        List<String> lines = List.of(
                "iyr:2015 cid:189 ecl:oth",
                "byr:1947 hcl:#6c4ab1 eyr:2026",
                "",
                "pid:688706448 iyr:2017 hgt:162cm cid:174 ecl:grn byr:1943 hcl:#808e9e eyr:2025");
        List<String> expected = List.of(
                "iyr:2015 cid:189 ecl:oth byr:1947 hcl:#6c4ab1 eyr:2026",
                "pid:688706448 iyr:2017 hgt:162cm cid:174 ecl:grn byr:1943 hcl:#808e9e eyr:2025");
        assertThat(app.parsePassportLines(lines)).isEqualTo(expected);
    }

    @Test
    void parsePassportLine() {
        Day4 app = new Day4();
        Passport expected = Passport.builder()
                .passportId("688706448")
                .issueYear(2017)
                .height("162cm")
                .countryId("174")
                .eyeColor("grn")
                .birthYear(1943)
                .hairColor("#808e9e")
                .expirationYear(2025)
                .build();
        Passport passport = app.parsePassportLine("pid:688706448 iyr:2017 hgt:162cm cid:174 ecl:grn byr:1943 hcl:#808e9e eyr:2025");
        assertThat(passport).isEqualTo(expected);
    }

    @Test
    void isPassportValid() {
        // with countryId
        Passport passport = Passport.builder()
                .passportId("688706448")
                .issueYear(2017)
                .height("162cm")
                .countryId("174")
                .eyeColor("grn")
                .birthYear(1943)
                .hairColor("#808e9e")
                .expirationYear(2025)
                .build();
        Set<ConstraintViolation<Passport>> constraintViolations = VALIDATOR.validate(passport, Day4.Part1.class);
        assertThat(constraintViolations.isEmpty()).isTrue();

        // no countryId
        Passport passport2 = Passport.builder()
                .passportId("688706448")
                .issueYear(2017)
                .height("162cm")
                .eyeColor("grn")
                .birthYear(1943)
                .hairColor("#808e9e")
                .expirationYear(2025)
                .build();
        Set<ConstraintViolation<Passport>> constraintViolations2 = VALIDATOR.validate(passport2, Day4.Part1.class);
        assertThat(constraintViolations2.isEmpty()).isTrue();

        // missing issueYear
        Passport passport3 = Passport.builder()
                .passportId("688706448")
                .height("162cm")
                .eyeColor("grn")
                .birthYear(1943)
                .hairColor("#808e9e")
                .expirationYear(2025)
                .build();
        Set<ConstraintViolation<Passport>> constraintViolations3 = VALIDATOR.validate(passport3, Day4.Part1.class);
        assertThat(constraintViolations3.isEmpty()).isFalse();
    }

    @Test
    void validatePassportValid() {
        Passport passport = createValidPassport();
        Set<ConstraintViolation<Passport>> constraintViolations = VALIDATOR.validate(passport, Day4.Part2.class);
        assertThat(constraintViolations).isEmpty();

        Passport passport2 = createValidPassport().toBuilder().passportId("000123456").build();
        Set<ConstraintViolation<Passport>> constraintViolations2 = VALIDATOR.validate(passport2, Day4.Part2.class);
        assertThat(constraintViolations2).isEmpty();

        Passport passport3 = createValidPassport().toBuilder().height("70in").build();
        Set<ConstraintViolation<Passport>> constraintViolations3 = VALIDATOR.validate(passport3, Day4.Part2.class);
        assertThat(constraintViolations3.isEmpty()).isTrue();
    }

    @Test
    void validatePassportInvalid() {
        Passport passport2 = createValidPassport().toBuilder().birthYear(1919).build();
        Set<ConstraintViolation<Passport>> constraintViolations2 = VALIDATOR.validate(passport2, Day4.Part2.class);
        assertThat(constraintViolations2.size()).isEqualTo(1);
        assertThat(constraintViolations2.iterator().next().getPropertyPath().toString()).isEqualTo("birthYear");

        Passport passport3 = createValidPassport().toBuilder().issueYear(2021).build();
        Set<ConstraintViolation<Passport>> constraintViolations3 = VALIDATOR.validate(passport3, Day4.Part2.class);
        assertThat(constraintViolations3.size()).isEqualTo(1);
        assertThat(constraintViolations3.iterator().next().getPropertyPath().toString()).isEqualTo("issueYear");

        Passport passport4 = createValidPassport().toBuilder().expirationYear(2035).build();
        Set<ConstraintViolation<Passport>> constraintViolations4 = VALIDATOR.validate(passport4, Day4.Part2.class);
        assertThat(constraintViolations4.size()).isEqualTo(1);
        assertThat(constraintViolations4.iterator().next().getPropertyPath().toString()).isEqualTo("expirationYear");

        Passport passport5 = createValidPassport().toBuilder().hairColor("oops").build();
        Set<ConstraintViolation<Passport>> constraintViolations5 = VALIDATOR.validate(passport5, Day4.Part2.class);
        assertThat(constraintViolations5.size()).isEqualTo(1);
        assertThat(constraintViolations5.iterator().next().getPropertyPath().toString()).isEqualTo("hairColor");

        Passport passport6 = createValidPassport().toBuilder().hairColor("#808e9eX").build();
        Set<ConstraintViolation<Passport>> constraintViolations6 = VALIDATOR.validate(passport6, Day4.Part2.class);
        assertThat(constraintViolations6.size()).isEqualTo(1);
        assertThat(constraintViolations6.iterator().next().getPropertyPath().toString()).isEqualTo("hairColor");

        Passport passport7 = createValidPassport().toBuilder().eyeColor("red").build();
        Set<ConstraintViolation<Passport>> constraintViolations7 = VALIDATOR.validate(passport7, Day4.Part2.class);
        assertThat(constraintViolations7.size()).isEqualTo(1);
        assertThat(constraintViolations7.iterator().next().getPropertyPath().toString()).isEqualTo("eyeColor");

        Passport passport8 = createValidPassport().toBuilder().eyeColor("ambr").build();
        Set<ConstraintViolation<Passport>> constraintViolations8 = VALIDATOR.validate(passport8, Day4.Part2.class);
        assertThat(constraintViolations8.size()).isEqualTo(1);
        assertThat(constraintViolations8.iterator().next().getPropertyPath().toString()).isEqualTo("eyeColor");

        Passport passport9 = createValidPassport().toBuilder().passportId("123").build();
        Set<ConstraintViolation<Passport>> constraintViolations9 = VALIDATOR.validate(passport9, Day4.Part2.class);
        assertThat(constraintViolations9.size()).isEqualTo(1);
        assertThat(constraintViolations9.iterator().next().getPropertyPath().toString()).isEqualTo("passportId");

        Passport passport10 = createValidPassport().toBuilder().passportId("1234567890").build();
        Set<ConstraintViolation<Passport>> constraintViolations10 = VALIDATOR.validate(passport10, Day4.Part2.class);
        assertThat(constraintViolations10.size()).isEqualTo(1);
        assertThat(constraintViolations10.iterator().next().getPropertyPath().toString()).isEqualTo("passportId");

        Passport passport11 = createValidPassport().toBuilder().height("20in").build();
        Set<ConstraintViolation<Passport>> constraintViolations11 = VALIDATOR.validate(passport11, Day4.Part2.class);
        assertThat(constraintViolations11.size()).isEqualTo(1);
        assertThat(constraintViolations11.iterator().next().getPropertyPath().toString()).isEqualTo("height");

        Passport passport12 = createValidPassport().toBuilder().height("200cm").build();
        Set<ConstraintViolation<Passport>> constraintViolations12 = VALIDATOR.validate(passport12, Day4.Part2.class);
        assertThat(constraintViolations12.size()).isEqualTo(1);
        assertThat(constraintViolations12.iterator().next().getPropertyPath().toString()).isEqualTo("height");

        Passport passport13 = createValidPassport().toBuilder().height("123").build();
        Set<ConstraintViolation<Passport>> constraintViolations13 = VALIDATOR.validate(passport13, Day4.Part2.class);
        assertThat(constraintViolations13.size()).isEqualTo(1);
        assertThat(constraintViolations13.iterator().next().getPropertyPath().toString()).isEqualTo("height");
    }

}