package com.mowitnow.model;

import java.util.Arrays;

public enum Instruction {
    ADVANCE('A'),
    RIGHT('D'),
    LEFT('G');

    private char code;

    Instruction(char code) {
        this.code = code;
    }

    public static Instruction findInstructionFromCode(char code) {
        return Arrays.stream(Instruction.values())
                .filter(c -> c.code == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No Instruction found for " + code));
    }
}
