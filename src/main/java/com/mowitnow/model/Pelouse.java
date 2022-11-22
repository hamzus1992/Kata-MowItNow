package com.mowitnow.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Pelouse {

    private int x;
    private int y;

    public void addX() {
        this.x += 1;
    }

    public void minusX() {
        this.x -= 1;
    }

    public void addY() {
        this.y += 1;
    }

    public void minusY() {
        this.y -= 1;
    }

}
