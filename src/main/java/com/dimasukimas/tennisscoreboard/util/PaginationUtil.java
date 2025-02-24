package com.dimasukimas.tennisscoreboard.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PaginationUtil {

    private static final int FIRST_PAGE_NUMBER = 1;

    public static int getPagesCount(long totalMatchesCount, int pageSize) {
        return (int) Math.ceil((double) totalMatchesCount / pageSize);
    }

    public static int adjustPageNumber(int pageNumber, int totalPages) {
        return Math.max(FIRST_PAGE_NUMBER, Math.min(pageNumber, totalPages));
    }

    public static int calculateOffset(int pageNumber, int pageSize) {
        return (pageNumber - FIRST_PAGE_NUMBER) * pageSize;
    }
}
