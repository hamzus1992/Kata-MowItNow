package com.mowitnow.cucumber;

import com.mowitnow.model.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("com.mowitnow.cucumber.feature")
public class TondeuseInstructionsCucumberTest {

    private Tondeuse tondeuse;
    private Position position;

    @Given("the initial position {int} {int} {string} and {int} {int}")
    public void the_initial_position_and_pelouse(Integer x, Integer y, String direction, Integer PelouseX, Integer PelouseY) {
        Position p = new Position(new Pelouse(x, y), Orientation.findOrientationFromCode(direction));
        tondeuse = Tondeuse.builder().position(p).pelouse(new Pelouse(PelouseX, PelouseY)).build();
    }

    @Given("with the following {string}")
    public void with_the_following_instructions(String instructionsAsString) {
        List<Instruction> instructions = new ArrayList<>();
        char[] instructionsAsCharacters = instructionsAsString.toCharArray();
        for (char c : instructionsAsCharacters) {
            instructions.add(Instruction.findInstructionFromCode(c));
        }
        tondeuse.setInstructions(instructions);
    }

    @When("I move the tondeuse with the following instructions")
    public void i_move_the_tondeuse_with_the_following_instructions() {
        position = tondeuse.processTondeuseInstructions();
    }

    @Then("the final position is {int} {int} {string}")
    public void the_final_position_is(Integer x, Integer y, String direction) {
        assertThat(position).isNotNull();
        assertThat(position.getPelouse().getX()).isEqualTo(x);
        assertThat(position.getPelouse().getY()).isEqualTo(y);
        assertThat(position.getOrientation().getCode()).isEqualTo(direction);
    }

}