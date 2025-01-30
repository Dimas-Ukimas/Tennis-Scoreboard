package com.dimasukimas.tennisscoreboard.enums;

import lombok.Getter;

@Getter
public enum TennisPoints {
    LOVE("0"),
    FIFTEEN("15"),
    THIRTY("30"),
    FORTY("40"),
    ADVANTAGE ("AD");

    private final String points;

    TennisPoints(String points) {
        this.points = points;
    }

}
