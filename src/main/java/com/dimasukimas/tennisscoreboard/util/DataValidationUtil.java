package com.dimasukimas.tennisscoreboard.util;

import com.dimasukimas.tennisscoreboard.exception.InvalidDataException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.regex.Pattern;

@UtilityClass
@Slf4j
public class DataValidationUtil {

    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]+(\\s+[a-zA-Z0-9_-]+)*$");
    private static final int MIN_NAME_LENGTH = 3;
    private static final int MAX_NAME_LENGTH = 20;

    public static UUID parseMatchUuid(String uuid) {
        UUID validUuid;
        try {
            validUuid = UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            log.warn("Failed to parse UUID {}", uuid, e);
            throw new IllegalArgumentException("Match with such UUID does not exists", e);
        }
        return validUuid;
    }

    public static Long parsePlayerId(String id) {
        long validId;
        try {
            validId = Long.parseLong(id);
        } catch (IllegalArgumentException e) {
            log.warn("Failed to parse player id {}", id, e);
            throw new IllegalArgumentException("Incorrect player id", e);
        }
        return validId;
    }

    public static int parsePageNumber(String id) {
        int pageNumber;
        try {
            pageNumber = (int) Long.parseLong(id);
        } catch (IllegalArgumentException e) {
            log.warn("Failed to parse page number {}", id, e);
            throw new IllegalArgumentException("Incorrect page number", e);
        }
        return pageNumber;
    }

    public static void validatePlayerNames(String player1Name, String player2Name) {
        checkNotNullOrEmpty(player1Name, player2Name);
        checkLength(player1Name, player2Name);
        checkValidCharacters(player1Name, player2Name);
        checkNamesAreDifferent(player1Name, player2Name);
    }

    private static void checkNotNullOrEmpty(String player1Name, String player2Name) {
        if (player1Name == null || player2Name == null || player1Name.isBlank() || player2Name.isBlank()) {
            log.warn("Invalid player names: '{}', '{}' - null or empty", player1Name, player2Name);
            throw new InvalidDataException("Player names cannot be null or empty");
        }
    }

    private static void checkLength(String player1Name, String player2Name) {
        if (player1Name.length() < MIN_NAME_LENGTH || player2Name.length() < MIN_NAME_LENGTH ||
                player1Name.length() > MAX_NAME_LENGTH || player2Name.length() > MAX_NAME_LENGTH) {
            log.warn("Invalid player names: '{}', '{}' - length out of range", player1Name, player2Name);
            throw new InvalidDataException("Player names must be between " + MIN_NAME_LENGTH + " and " + MAX_NAME_LENGTH + " characters");
        }
    }

    private static void checkValidCharacters(String player1Name, String player2Name) {
        if (!NAME_PATTERN.matcher(player1Name).matches() || !NAME_PATTERN.matcher(player2Name).matches()) {
            log.warn("Invalid player names: '{}', '{}' - invalid characters", player1Name, player2Name);
            throw new InvalidDataException("Player names can only contain Latin letters, numbers, hyphens, underscores and single space between words");
        }
    }

    private static void checkNamesAreDifferent(String player1Name, String player2Name) {
        if (player1Name.equals(player2Name)) {
            log.warn("Invalid player names: '{}', '{}' - names are identical", player1Name, player2Name);
            throw new InvalidDataException("Player names must be different");
        }
    }
}

