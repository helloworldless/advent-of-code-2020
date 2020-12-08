package com.davidagood.aoc2020;

import lombok.Value;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

public class Day8 {

    private long accumulator = 0;

    public static void main(String[] args) {
        Day8 app = new Day8();
        app.solve();
    }

    public long getAccumulator() {
        return accumulator;
    }

    private void solve() {
        List<String> lines = FileUtil.readFileLines("day8.txt");
        List<Command> commands = lines.stream().map(this::parseRawCommand).collect(toList());

        Optional<Integer> maybeCycleAtIndex = processStoppingIfCycle(commands);
        System.out.printf("Part 1: Cycle at index: %s; Accumulator value is: %s%n", maybeCycleAtIndex, accumulator);

        Optional<Integer> maybeCyclePreventedByChangingOp = processPart2(commands);
        System.out.printf("Part 2: Maybe cycle prevented by changing op at index: %s; Accumulator value is: %s%n", maybeCyclePreventedByChangingOp, accumulator);
    }

    Command parseRawCommand(String rawCommandLine) {
        Pattern pattern = Pattern.compile("^(acc|nop|jmp) (\\+|\\-)(\\d+)$");
        Matcher matcher = pattern.matcher(rawCommandLine);
        matcher.matches();
        String rawCommand = matcher.group(1);
        String plusOrMinus = matcher.group(2);
        String rawValue = matcher.group(3);
        int value = "-".equals(plusOrMinus) ? -1 * Integer.parseInt(rawValue) : Integer.parseInt(rawValue);
        return new Command(Op.from(rawCommand), value);
    }

    Optional<Integer> processStoppingIfCycle(List<Command> commands) {
        // Reset state
        this.accumulator = 0;
        Set<Integer> seenIndices = new HashSet<>();

        int currentIndex = 0;

        while (!seenIndices.contains(currentIndex) && currentIndex < commands.size()) {
            seenIndices.add(currentIndex);
            Command command = commands.get(currentIndex);
            Op op = command.getOp();
            switch (op) {
                case ACC:
                    accumulator += command.getValue();
                    currentIndex++;
                    break;
                case JMP:
                    currentIndex = currentIndex + command.getValue();
                    break;
                case NOP:
                    currentIndex++;
                    break;
                default:
                    throw new IllegalArgumentException("Unhandled op: " + op);
            }
        }

        return currentIndex == commands.size() ? Optional.empty() : Optional.of(currentIndex);
    }

    Optional<Integer> processPart2(List<Command> commands) {
        for (int i = 0; i < commands.size(); i++) {
            Command command = commands.get(i);
            if (Op.JMP.equals(command.getOp())) {
                List<Command> newCommands = replaceOpAtIndex(commands, i, Op.NOP);
                Optional<Integer> maybeCycle = processStoppingIfCycle(newCommands);
                if (maybeCycle.isEmpty()) {
                    return Optional.of(i);
                }
            } else if (Op.NOP.equals(command.getOp())) {
                List<Command> newCommands = replaceOpAtIndex(commands, i, Op.JMP);
                Optional<Integer> maybeCycle = processStoppingIfCycle(newCommands);
                if (maybeCycle.isEmpty()) {
                    return Optional.of(i);
                }
            }
        }

        return Optional.empty();
    }

    List<Command> replaceOpAtIndex(List<Command> commands, int i, Op newOp) {
        Command command = commands.get(i);
        List<Command> front = commands.subList(0, i);
        List<Command> back = commands.subList(i + 1, commands.size());
        List<Command> newCommands = new ArrayList<>();
        newCommands.addAll(front);
        newCommands.add(new Command(newOp, command.getValue()));
        newCommands.addAll(back);
        return newCommands;
    }

    public enum Op {
        ACC,
        JMP,
        NOP;

        public static Op from(String rawCommand) {
            return Op.valueOf(rawCommand.toUpperCase());
        }
    }

    @Value
    public static class Command {
        Op op;
        int value;
    }

}
