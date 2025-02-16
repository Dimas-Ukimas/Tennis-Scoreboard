package com.dimasukimas.tennisscoreboard.enums;

import lombok.Getter;

@Getter
public enum TennisPointsView {
    LOVE("0"),
    FIFTEEN("15"),
    THIRTY("30"),
    FORTY("40"),
    ADVANTAGE ("AD");

    private final String points;

    TennisPointsView(String points) {
        this.points = points;
    }

}
