package com.mowitnow.junit;

import com.mowitnow.exception.ParserException;
import com.mowitnow.io.Parser;
import com.mowitnow.model.Instruction;
import com.mowitnow.model.Orientation;
import com.mowitnow.model.Pelouse;
import com.mowitnow.model.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ParserTest {

    private static Stream<Arguments> pelouse() {
        return Stream.of(
                arguments("5 5", 5, 5),
                arguments("6 6", 6, 6),
                arguments("7 6", 7, 6)
        );
    }

    private static Stream<Arguments> position() {
        return Stream.of(
                arguments("5 5 N", 5, 5, "N"),
                arguments("6 6 E", 6, 6, "E"),
                arguments("7 6 S", 7, 6, "S")
        );
    }

    private static Stream<Arguments> instructions() {
        return Stream.of(
                arguments("ADDG", List.of(Instruction.ADVANCE, Instruction.RIGHT, Instruction.RIGHT, Instruction.LEFT)),
                arguments("ADDGAAG", List.of(Instruction.ADVANCE, Instruction.RIGHT, Instruction.RIGHT, Instruction.LEFT, Instruction.ADVANCE, Instruction.ADVANCE, Instruction.LEFT)),
                arguments("ADDGAAGAA", List.of(Instruction.ADVANCE, Instruction.RIGHT, Instruction.RIGHT, Instruction.LEFT, Instruction.ADVANCE, Instruction.ADVANCE, Instruction.LEFT, Instruction.ADVANCE, Instruction.ADVANCE))
        );
    }

    private static Stream<Arguments> instructionsExceptions() {
        return Stream.of(
                arguments("ADDGKKAA"),
                arguments("KKADDGAAG"),
                arguments("ADDGAAGBLABLA")
        );
    }

    private static Stream<Arguments> positionException() {
        return Stream.of(
                arguments("5 5 NN"),
                arguments("6 6 K"),
                arguments("7 S")
        );
    }

    private static Stream<Arguments> pelouseException() {
        return Stream.of(
                arguments("5 5 a"),
                arguments("6"),
                arguments("7 bbb")
        );
    }

    @ParameterizedTest
    @MethodSource("pelouse")
    public void pelouseParser(final String line, final int x, final int y) {
        Pelouse pelouse = Parser.parsePelouse(line);
        Pelouse expectedPelouse = new Pelouse(x, y);
        assertThat(pelouse).isEqualTo(expectedPelouse);
    }

    @ParameterizedTest
    @MethodSource("position")
    public void positionParser(final String line, final int x, final int y,
                               String orientation) {
        Position position = Parser.parsePosition(line);
        Position expectedPosition = Position.builder()
                .orientation(Orientation.findOrientationFromCode(orientation))
                .pelouse(Pelouse.builder().x(x).y(y).build())
                .build();
        assertEquals(position, expectedPosition);
    }

    @ParameterizedTest
    @MethodSource("instructions")
    public void instructionsParser(final String line, List<Instruction> expectedInstructions) {
        List<Instruction> instructions = Parser.instructionParser(line);
        assertEquals(instructions, expectedInstructions);
    }

    @ParameterizedTest
    @MethodSource("pelouseException")
    public void pelouseParserException(final String line) {
        assertThrows(ParserException.class, () -> Parser.parsePelouse(line));
    }

    @ParameterizedTest
    @MethodSource("positionException")
    public void positionParserException(final String line) {
        assertThrows(ParserException.class, () -> Parser.parsePosition(line));
    }

    @ParameterizedTest
    @MethodSource("instructionsExceptions")
    public void instructionParserException(final String line) {
        assertThrows(IllegalArgumentException.class, () -> Parser.instructionParser(line));
    }


}
