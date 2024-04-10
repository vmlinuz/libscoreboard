package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

public class ScoreboardTest {
    private Scoreboard scoreboard;

    @BeforeEach
    public void setUp() {
        scoreboard = new Scoreboard();
    }

    @Test
    public void testScoreboardStartMatch() {
        scoreboard.startMatch("HomeTeam1", "AwayTeam1");

        assertEquals(1, scoreboard.getSummary().size());
    }

    @Test
    public void testScoreboardStartMatchProducesDefaultValuesForTeams() {
        scoreboard.startMatch("HomeTeam1", "AwayTeam1");

        assertEquals("HomeTeam1 0 - AwayTeam1 0", scoreboard.getSummary().getFirst());
    }

    @Test
    public void testScoreboardUpdateScore() {
        scoreboard.startMatch("HomeTeam1", "AwayTeam1");
        scoreboard.updateScore("HomeTeam1", "AwayTeam1", 2, 3);

        assertEquals("HomeTeam1 2 - AwayTeam1 3", scoreboard.getSummary().getFirst());
    }

    @Test
    public void testScoreboardFinishMatch() {
        scoreboard.startMatch("HomeTeam1", "AwayTeam1");
        scoreboard.finishMatch("HomeTeam1", "AwayTeam1");

        assertEquals(0, scoreboard.getSummary().size());
    }

    @Test
    public void testScoreboardFinishMatchRemovesCorrectMatch() {
        scoreboard.startMatch("HomeTeam1", "AwayTeam1");
        scoreboard.startMatch("HomeTeam2", "AwayTeam2");

        scoreboard.finishMatch("HomeTeam1", "AwayTeam1");

        assertEquals("HomeTeam2 0 - AwayTeam2 0", scoreboard.getSummary().getFirst());
    }

    @Test
    public void testScoreboardSummaryIsOrderedByTotalScore() {
        scoreboard.startMatch("HomeTeam1", "AwayTeam1");
        scoreboard.updateScore("HomeTeam1", "AwayTeam1", 2, 3);

        scoreboard.startMatch("HomeTeam2", "AwayTeam2");
        scoreboard.updateScore("HomeTeam2", "AwayTeam2", 1, 1);

        List<String> summary = scoreboard.getSummary();

        assertEquals("HomeTeam1 2 - AwayTeam1 3", summary.get(0));
        assertEquals("HomeTeam2 1 - AwayTeam2 1", summary.get(1));
    }

    @Test
    public void testScoreboardSummaryIsOrderedByTheMostRecentMatchIfScoresAreEqual() {
        scoreboard.startMatch("HomeTeam1", "AwayTeam1");
        scoreboard.updateScore("HomeTeam1", "AwayTeam1", 2, 3);

        scoreboard.startMatch("HomeTeam2", "AwayTeam2");
        scoreboard.updateScore("HomeTeam2", "AwayTeam2", 2, 3);

        List<String> summary = scoreboard.getSummary();

        assertEquals("HomeTeam2 2 - AwayTeam2 3", summary.get(0));
        assertEquals("HomeTeam1 2 - AwayTeam1 3", summary.get(1));
    }

    @Test
    public void testStartDuplicateMatchThrowsException() {
        scoreboard.startMatch("HomeTeam1", "AwayTeam1");

        assertThrows(IllegalArgumentException.class, () -> scoreboard.startMatch("HomeTeam1", "AwayTeam1"));
    }

    @Test
    public void testStartMatchThrowsExceptionWhenHomeTeamIsNull() {
        assertThrows(IllegalArgumentException.class, () -> scoreboard.startMatch(null, "AwayTeam1"));
    }

    @Test
    public void testStartMatchThrowsExceptionWhenAwayTeamIsNull() {
        assertThrows(IllegalArgumentException.class, () -> scoreboard.startMatch("HomeTeam1", null));
    }

    @Test
    public void testStartMatchThrowsExceptionWhenHomeTeamIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> scoreboard.startMatch("", "AwayTeam1"));
    }

    @Test
    public void testStartMatchThrowsExceptionWhenAwayTeamIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> scoreboard.startMatch("HomeTeam1", ""));
    }

    @Test
    public void testStartMatchThrowsExceptionWhenHomeTeamIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> scoreboard.startMatch(" ", "AwayTeam1"));
    }

    @Test
    public void testStartMatchThrowsExceptionWhenAwayTeamIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> scoreboard.startMatch("HomeTeam1", " "));
    }

    @Test
    public void testUpdateScoreWithNegativeValuesThrowsException() {
        scoreboard.startMatch("HomeTeam1", "AwayTeam1");

        assertThrows(IllegalArgumentException.class, () -> scoreboard.updateScore("HomeTeam1", "AwayTeam1", -1, 3));
    }

    @Test
    public void testUpdateScoreThrowsExceptionWhenMatchDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> scoreboard.updateScore("NonExistentHomeTeam", "NonExistentAwayTeam", 1, 1));
    }

    @Test
    public void testFinishNonexistentMatchThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> scoreboard.finishMatch("Nonexistent", "Match"));
    }

}
