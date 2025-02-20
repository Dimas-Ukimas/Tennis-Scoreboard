package com.dimasukimas.tennisscoreboard.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PaginationUtil {

    public static int getPagesCount(long totalMatchesCount, int pageSize) {
        return (int) Math.ceil((double) totalMatchesCount / pageSize);
    }

    public static int adjustPageNumber(int pageNumber, int totalPages) {
        return Math.max(1, Math.min(pageNumber, totalPages));
    }
}
