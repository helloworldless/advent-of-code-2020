package com.davidagood.aoc2020;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

// Created for Day2
@Getter
@EqualsAndHashCode
@ToString
public class PasswordPolicy {

    private final int min;
    private final int max;
    private final char letter;

    private PasswordPolicy(int min, int max, char letter) {
        this.min = min;
        this.max = max;
        this.letter = letter;
    }

    public static PasswordPolicy of(int min, int max, char letter) {
        return new PasswordPolicy(min, max, letter);
    }

}