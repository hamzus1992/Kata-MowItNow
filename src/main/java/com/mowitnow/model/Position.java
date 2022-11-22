package com.mowitnow.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class Position {

    private Pelouse pelouse;
    private Orientation orientation;


    public Position(Pelouse pelouse, Orientation orientation) {
        this.pelouse = pelouse;
        this.orientation = orientation;
    }

    public void turnToRightOrientation() {
        orientation = orientation.getRightOrientation();
    }

    public void turnToLeftOrientation() {
        orientation = orientation.getLeftOrientation();
    }

    public void processAdvance(Pelouse pelouse) {
        switch (orientation) {
            case NORTH:
                if (this.pelouse.getY() < pelouse.getY()) {
                    this.pelouse.addY();
                }
                break;
            case EAST:
                if (this.pelouse.getX() < pelouse.getX()) {
                    this.pelouse.addX();
                }
                break;
            case SOUTH:
                if (this.pelouse.getY() > 0) {
                    this.pelouse.minusY();
                }
                break;
            case WEST:
                if (this.pelouse.getX() > 0) {
                    this.pelouse.minusX();
                }
                break;
        }
    }

    public String toString() {
        return this.pelouse.getX() + " " +
                this.pelouse.getY() + " " +
                orientation.getCode();
    }
}
