package com.mowitnow.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Tondeuse {

    private Position position;
    private Pelouse pelouse;
    private List<Instruction> instructions;

    public Position processTondeuseInstructions() {
        instructions.forEach(this::processInstruction);
        return position;
    }

    private void processInstruction(final Instruction instruction) {
        switch (instruction) {
            case ADVANCE -> position.processAdvance(pelouse);
            case RIGHT -> position.turnToRightOrientation();
            case LEFT -> position.turnToLeftOrientation();
        }
    }

}
