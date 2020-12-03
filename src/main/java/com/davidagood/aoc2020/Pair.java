package com.davidagood.aoc2020;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;

// Created for Day2
@EqualsAndHashCode
@ToString
public class Pair<T, U> {
    private final T first;
    private final U second;

    private Pair(T first, U second) {
        Objects.requireNonNull(first);
        Objects.requireNonNull(second);
        this.first = first;
        this.second = second;
    }

    public static <T, U> Pair<T, U> of(T first, U second) {
        return new Pair<>(first, second);
    }

    public T getFirst() {
        return this.first;
    }

    public U getSecond() {
        return this.second;
    }

}