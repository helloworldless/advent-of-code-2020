package com.davidagood.aoc2020;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

class Day8Test {

    @Test
    void parseRawCommand() {
        Day8 app = new Day8();
        assertThat(app.parseRawCommand("nop +103")).isEqualTo(new Day8.Command(Day8.Op.NOP, 103));
        assertThat(app.parseRawCommand("acc +36")).isEqualTo(new Day8.Command(Day8.Op.ACC, 36));
        assertThat(app.parseRawCommand("acc -18")).isEqualTo(new Day8.Command(Day8.Op.ACC, -18));
        assertThat(app.parseRawCommand("jmp +47")).isEqualTo(new Day8.Command(Day8.Op.JMP, 47));
    }

    @Test
    void processStoppingIfCycle() {
        String[] rawLines = ("nop +0\n" +
                "acc +1\n" +
                "jmp +4\n" +
                "acc +3\n" +
                "jmp -3\n" +
                "acc -99\n" +
                "acc +1\n" +
                "jmp -4\n" +
                "acc +6").split("\n");
        Day8 app = new Day8();
        List<Day8.Command> commands = Arrays.stream(rawLines).map(app::parseRawCommand).collect(toList());
        app.processStoppingIfCycle(commands);
        assertThat(app.getAccumulator()).isEqualTo(5);
    }

}