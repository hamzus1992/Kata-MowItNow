package com.mowitnow.model;

import java.util.Arrays;

public enum Orientation {

    NORTH("N","E", "W"),
    EAST("E", "S", "N"),
    SOUTH("S","W", "E"),
    WEST("W", "N", "S");

    private final String code;
    private final String codeOrientationRight;
    private final String codeOrientationLeft;

    Orientation(final String code,final String codeOrientationRight,final String codeOrientationLeft) {
        this.code = code;
        this.codeOrientationLeft = codeOrientationLeft;
        this.codeOrientationRight = codeOrientationRight;
    }

    public String getCode() {
        return this.code;
    }

    public Orientation getLeftOrientation() {
        return findOrientationFromCode(codeOrientationLeft);
    }

    public Orientation getRightOrientation() {
        return findOrientationFromCode(codeOrientationRight);
    }

    public static Orientation findOrientationFromCode(String code) {
        return Arrays.stream(Orientation.values())
                .filter(c -> c.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No direction found for " + code));
    }


}
