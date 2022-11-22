package com.mowitnow.service;

import com.mowitnow.exception.FileFormatInvalidException;
import com.mowitnow.io.Parser;
import com.mowitnow.model.Instruction;
import com.mowitnow.model.Pelouse;
import com.mowitnow.model.Position;
import com.mowitnow.model.Tondeuse;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TondeuseService {

    private final List<Tondeuse> tondeuses = new ArrayList<>();

    public TondeuseService(Path tondeuseMovementPathInput) throws FileNotFoundException {
        if (tondeuseMovementPathInput == null) {
            throw new FileNotFoundException("The tondeuse simulation file is not found");
        }
        try {
            List<String> fileLines = Files.readAllLines(tondeuseMovementPathInput);
            if (fileLines.isEmpty()) {
                throw new FileFormatInvalidException(String.format("Can't create Tondeuse instruction with input file : %s", tondeuseMovementPathInput));
            }
            Iterator<String> iterator = fileLines.iterator();
            Pelouse pelouse = Parser.parsePelouse(iterator.next().strip());

            while (iterator.hasNext()) {
                String tondeusePosition = iterator.next();
                if (!iterator.hasNext()) {
                    throw new FileFormatInvalidException("Missing orientation instruction");
                }
                String tondeuseInstructions = iterator.next();
                Position p = Parser.parsePosition(tondeusePosition.strip());
                List<Instruction> instructions = Parser.instructionParser(tondeuseInstructions);
                tondeuses.add(Tondeuse.builder().position(p).instructions(instructions).pelouse(pelouse).build());
            }
        } catch (IOException e) {
            throw new FileFormatInvalidException("Invalid input file", e);

        }
    }

    public List<Position> processMovement() {
        List<Position> finalPositions = new ArrayList<>(tondeuses.size());
        for (Tondeuse tondeuse : tondeuses) {
            finalPositions.add(tondeuse.processTondeuseInstructions());
        }
        return finalPositions;
    }

}