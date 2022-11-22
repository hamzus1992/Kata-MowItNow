package com.mowitnow.io;

import com.mowitnow.exception.ParserException;
import com.mowitnow.model.Instruction;
import com.mowitnow.model.Orientation;
import com.mowitnow.model.Pelouse;
import com.mowitnow.model.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Parser {

    private static final String SEPARATOR = " ";
    private static final String PATTERN_POSITION = "^\\d+ \\d+ [N|E|W|S]$";
    private static final String PATTERN_PELOUSE = "^\\d+ \\d+$";

    private Parser() {

    }

    public static Pelouse parsePelouse(final String pelouse) {
        if (pelouse.matches(PATTERN_PELOUSE)) {
            List<String> pelouseSplit = Arrays.asList(pelouse.split(SEPARATOR));
            return new Pelouse(Integer.parseInt(pelouseSplit.get(0)), Integer.parseInt(pelouseSplit.get(1)));
        } else {
            throw new ParserException(String.format("Error parse pelouse in this line [%s] it must be with this pattern  [two numbers between 0 and 9 separated by a space ]", pelouse));
        }
    }


    public static Position parsePosition(final String position) {
        if (!position.matches(PATTERN_POSITION)) {
            throw new ParserException(String.format("Error parse position in this line [%s] it must be with this pattern [two numbers between 0 and 9 separated by a space , a space and letter in {N|E|W|S}]", position));
        }
        List<String> positionSplit = Arrays.asList(position.split(SEPARATOR));
        Pelouse p = new Pelouse(Integer.parseInt(positionSplit.get(0)), Integer.parseInt(positionSplit.get(1)));
        Orientation orientation = Orientation.findOrientationFromCode(positionSplit.get(2));
        return Position.builder()
                .orientation(orientation)
                .pelouse(p)
                .build();
    }

    public static List<Instruction> instructionParser(final String instructions) {
        List<Instruction> instructionsSplit = new ArrayList<>();
        for (char instruction : instructions.toCharArray()) {
            instructionsSplit.add(Instruction.findInstructionFromCode(instruction));
        }
        return instructionsSplit;
    }


}
