package com.dimasukimas.tennisscoreboard.enumeration;

import lombok.Getter;

@Getter
public enum TennisPoints {
    LOVE(0, "0"),
    FIFTEEN(1, "15"),
    THIRTY(2, "30"),
    FORTY(3, "40"),
    ADVANTAGE(4, "AD");

    private final String view;
    private final int value;

    TennisPoints(int value, String view) {
        this.view = view;
        this.value = value;
    }

    public static String getViewByValue(int value) {
        for (TennisPoints point : TennisPoints.values()) {
            if (point.getValue() == value) {
                return point.getView();
            }
        }
        throw new IllegalArgumentException("No such value: " + value);
    }
}
