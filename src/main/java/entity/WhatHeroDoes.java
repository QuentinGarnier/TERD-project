package entity;

import graphics.elements.Position;

public enum WhatHeroDoes {
    MOVING, CHOOSING_ATTACK, ATTACKING;
    private Position p;
    WhatHeroDoes(){}

    public Position getP() {
        return p;
    }

    public void setP(Position p) {
        System.out.println(p);
        this.p = p;
    }
}