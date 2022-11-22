
Feature: Tondeuse Movement

 Scenario Outline: Should move tondeuse to final position when execute instruction list
    Given the initial position <InitialPosition> and <Pelouse>
    And with the following <Instructions>
    When I move the tondeuse with the following instructions
    Then the final position is <FinalPosition>
    Examples:
      | InitialPosition | Pelouse | Instructions     | FinalPosition |
      | 1 2 "N"         | 5 5         | "GAGAGAGAA"  | 1 3 "N"       |
      | 3 3 "E"         | 5 5         | "AADAADADDA" | 5 1 "E"       |