package com.davidagood.aoc2020.day4;

import com.davidagood.aoc2020.Day4;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Value
@Builder(toBuilder = true)
public class Passport {

    @NotNull(groups = Day4.Part1.class)
    @Min(value = 1920, groups = Day4.Part2.class)
    @Max(value = 2002, groups = Day4.Part2.class)
    Integer birthYear;

    @NotNull(groups = Day4.Part1.class)
    @Min(value = 2010, groups = Day4.Part2.class)
    @Max(value = 2020, groups = Day4.Part2.class)
    Integer issueYear;

    @NotNull(groups = Day4.Part1.class)
    @Min(value = 2020, groups = Day4.Part2.class)
    @Max(value = 2030, groups = Day4.Part2.class)
    Integer expirationYear;

    @NotEmpty(groups = Day4.Part1.class)
    @Height(groups = Day4.Part2.class)
    String height;

    @NotEmpty(groups = Day4.Part1.class)
    @Pattern(regexp = "^#[0-9|a-f]{6}$", groups = Day4.Part2.class)
    String hairColor;

    @NotEmpty(groups = Day4.Part1.class)
    @Pattern(regexp = "^(amb|blu|brn|gry|grn|hzl|oth)$", groups = Day4.Part2.class)
    String eyeColor;

    @NotEmpty(groups = Day4.Part1.class)
    @Pattern(regexp = "^\\d{9}$", groups = Day4.Part2.class)
    String passportId;

    String countryId;
}
