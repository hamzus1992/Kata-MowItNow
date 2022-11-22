package com.mowitnow.junit;


import com.mowitnow.model.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TondeuseTest {

    private static Stream<Arguments> tondeuse() {
        return Stream.of(
                //should Rotate To EAST When Tondeuse In NORTH And Instruction Is Right
                arguments(2, 2, 2, 2, Collections.singletonList(Instruction.RIGHT), Orientation.NORTH, Orientation.EAST, new Pelouse(5, 5)),
                //should Rotate To WEST When Tondeuse In SOUTH And Instruction Is Right
                arguments(2, 2, 2, 2, Collections.singletonList(Instruction.RIGHT), Orientation.SOUTH, Orientation.WEST, new Pelouse(5, 5)),
                //should Rotate To NORTH When Tondeuse In WEST And Instruction Is Right
                arguments(2, 2, 2, 2, Collections.singletonList(Instruction.RIGHT), Orientation.WEST, Orientation.NORTH, new Pelouse(5, 5)),
                // should Rotate To WEST When Tondeuse In NORTH And Instruction Is Left
                arguments(2, 2, 2, 2, Collections.singletonList(Instruction.LEFT), Orientation.NORTH, Orientation.WEST, new Pelouse(5, 5)),
                // should Rotate To NORTH When Tondeuse In EAST And Instruction Is Left
                arguments(2, 2, 2, 2, Collections.singletonList(Instruction.LEFT), Orientation.EAST, Orientation.NORTH, new Pelouse(5, 5)),
                //should Rotate To EAST When Tondeuse In SOUTH And Instruction Is Left
                arguments(2, 2, 2, 2, Collections.singletonList(Instruction.LEFT), Orientation.SOUTH, Orientation.EAST, new Pelouse(5, 5)),
                //should Rotate To SOUTH When Tondeuse In WEST And Instruction Is Left
                arguments(2, 2, 2, 2, Collections.singletonList(Instruction.LEFT), Orientation.WEST, Orientation.SOUTH, new Pelouse(5, 5)),
                //should Move To Next Y Position When Instruction Is Advance And Orientation Is NORTH
                arguments(2, 2, 2, 3, Collections.singletonList(Instruction.ADVANCE), Orientation.NORTH, Orientation.NORTH, new Pelouse(5, 5)),
                //should Move To Next X Position When Instruction Is Advance And Orientation Is EAST
                arguments(2, 2, 3, 2, Collections.singletonList(Instruction.ADVANCE), Orientation.EAST, Orientation.EAST, new Pelouse(5, 5)),
                //should Move To Back Y Position When Instruction Is Advance And Orientation Is SOUTH
                arguments(2, 2, 2, 1, Collections.singletonList(Instruction.ADVANCE), Orientation.SOUTH, Orientation.SOUTH, new Pelouse(5, 5)),
                //should Move To Back X Position When Instruction Is Advance And Orientation Is WEST
                arguments(2, 2, 1, 2, Collections.singletonList(Instruction.ADVANCE), Orientation.WEST, Orientation.WEST, new Pelouse(5, 5)),
                //should Stay At Same Position When X is Zero And Instruction Is Advance And Orientation WEST
                arguments(0, 2, 0, 2, Collections.singletonList(Instruction.ADVANCE), Orientation.WEST, Orientation.WEST, new Pelouse(5, 5)),
                //should Stay At Same Position When Y is Zero And Instruction Is Advance And Orientation SOUTH
                arguments(2, 0, 2, 0, Collections.singletonList(Instruction.ADVANCE), Orientation.SOUTH, Orientation.SOUTH, new Pelouse(5, 5)),
                //should Stay At Same Place When Tondeuse In Garden LimitX
                arguments(5, 5, 5, 5, Collections.singletonList(Instruction.ADVANCE), Orientation.EAST, Orientation.EAST, new Pelouse(5, 5)),
                //should Stay At Same Place When Tondeuse In Garden LimitY
                arguments(5, 5, 5, 5, Collections.singletonList(Instruction.ADVANCE), Orientation.NORTH, Orientation.NORTH, new Pelouse(5, 5)),
                //should Move To 1 3 N When Tondeuse Position Is 1 2 N And Instructions GAGAGAGAA
                arguments(1, 2, 1, 3, List.of(Instruction.LEFT, Instruction.ADVANCE,
                        Instruction.LEFT, Instruction.ADVANCE, Instruction.LEFT, Instruction.ADVANCE,
                        Instruction.LEFT, Instruction.ADVANCE, Instruction.ADVANCE), Orientation.NORTH, Orientation.NORTH, new Pelouse(5, 5)),
                //should Move To 5 1 E When Tondeuse Position Is 3 3 E And Instructions AADAADADDA
                arguments(3, 3, 5, 1, List.of(Instruction.ADVANCE, Instruction.ADVANCE,
                        Instruction.RIGHT, Instruction.ADVANCE, Instruction.ADVANCE, Instruction.RIGHT,
                        Instruction.ADVANCE, Instruction.RIGHT, Instruction.RIGHT, Instruction.ADVANCE), Orientation.EAST, Orientation.EAST, new Pelouse(5, 5))


        );
    }

    @ParameterizedTest
    @MethodSource("tondeuse")
    void processTondeuseInstructionsTest(final int x, final int y,
                                         final int expectedX, final int expectedY,
                                         List<Instruction> instructions, Orientation orientation,
                                         Orientation expectedOrientation, Pelouse pelouse) {
        Tondeuse tondeuse = Tondeuse.builder()
                .position(new Position(new Pelouse(x, y), orientation))
                .instructions(instructions)
                .pelouse(pelouse).build();
        Position position = tondeuse.processTondeuseInstructions();
        Position expectedPosition = new Position(new Pelouse(expectedX, expectedY), expectedOrientation);
        assertThat(position).isEqualTo(expectedPosition);
    }
}