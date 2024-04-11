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
    public void testScoreboardStartMatchIncreasesSummarySizeByOne() {
        String homeTeam = "HomeTeam1";
        String awayTeam = "AwayTeam1";

        scoreboard.startMatch(homeTeam, awayTeam);

        int expectedSize = 1;
        int actualSize = scoreboard.getSummary().size();
        assertEquals(expectedSize, actualSize, "Starting a match should increase the summary size by 1");
    }

    @Test
    public void testScoreboardStartMatchProducesDefaultValuesForTeams() {
        String homeTeam = "HomeTeam1";
        String awayTeam = "AwayTeam1";

        scoreboard.startMatch(homeTeam, awayTeam);

        String expectedSummary = "HomeTeam1 0 - AwayTeam1 0";
        String actualSummary = scoreboard.getSummary().getFirst();
        assertEquals(expectedSummary, actualSummary, "Starting a match should produce default values for teams");
    }

    @Test
    public void testScoreboardUpdateScoreReflectsInTheMatchSummary() {
        String homeTeam = "HomeTeam1";
        String awayTeam = "AwayTeam1";
        int homeScore = 2;
        int awayScore = 3;
        scoreboard.startMatch(homeTeam, awayTeam);

        scoreboard.updateScore(homeTeam, awayTeam, homeScore, awayScore);

        String expectedSummary = "HomeTeam1 2 - AwayTeam1 3";
        String actualSummary = scoreboard.getSummary().getFirst();
        assertEquals(expectedSummary, actualSummary, "Updating the score should reflect in the match summary");
    }

    @Test
    public void testScoreboardFinishMatchDecreasesSummarySizeByOne() {
        String homeTeam = "HomeTeam1";
        String awayTeam = "AwayTeam1";
        scoreboard.startMatch(homeTeam, awayTeam);

        scoreboard.finishMatch(homeTeam, awayTeam);

        int expectedSize = 0;
        int actualSize = scoreboard.getSummary().size();
        assertEquals(expectedSize, actualSize, "Finishing a match should decrease the summary size by 1");
    }

    @Test
    public void testScoreboardFinishMatchRemovesCorrectMatch() {
        scoreboard.startMatch("HomeTeam1", "AwayTeam1");
        scoreboard.startMatch("HomeTeam2", "AwayTeam2");

        scoreboard.finishMatch("HomeTeam1", "AwayTeam1");

        String expectedSummary = "HomeTeam2 0 - AwayTeam2 0";
        String actualSummary = scoreboard.getSummary().getFirst();
        assertEquals(expectedSummary, actualSummary, "Finishing a match should remove the correct match from the summary");
    }

    @Test
    public void testScoreboardSummaryIsOrderedByTotalScore() {
        scoreboard.startMatch("HomeTeam1", "AwayTeam1");
        scoreboard.updateScore("HomeTeam1", "AwayTeam1", 2, 3);
        scoreboard.startMatch("HomeTeam2", "AwayTeam2");
        scoreboard.updateScore("HomeTeam2", "AwayTeam2", 1, 1);

        List<String> summary = scoreboard.getSummary();

        assertEquals("HomeTeam1 2 - AwayTeam1 3", summary.get(0), "The summary should be ordered by total score");
        assertEquals("HomeTeam2 1 - AwayTeam2 1", summary.get(1), "The summary should be ordered by total score");
    }

    @Test
    public void testScoreboardSummaryIsOrderedByTheMostRecentMatchIfScoresAreEqual() {
        scoreboard.startMatch("HomeTeam1", "AwayTeam1");
        scoreboard.updateScore("HomeTeam1", "AwayTeam1", 2, 3);
        scoreboard.startMatch("HomeTeam2", "AwayTeam2");
        scoreboard.updateScore("HomeTeam2", "AwayTeam2", 2, 3);

        List<String> summary = scoreboard.getSummary();

        assertEquals("HomeTeam2 2 - AwayTeam2 3", summary.get(0), "The summary should be ordered by the most recent match if scores are equal");
        assertEquals("HomeTeam1 2 - AwayTeam1 3", summary.get(1), "The summary should be ordered by the most recent match if scores are equal");
    }

    @Test
    public void testStartDuplicateMatchThrowsException() {
        scoreboard.startMatch("HomeTeam1", "AwayTeam1");

        assertThrows(IllegalArgumentException.class, () -> scoreboard.startMatch("HomeTeam1", "AwayTeam1"), "Starting a duplicate match should throw an exception");
    }

    @Test
    public void testStartMatchThrowsExceptionWhenHomeTeamIsNull() {
        assertThrows(IllegalArgumentException.class, () -> scoreboard.startMatch(null, "AwayTeam1"), "Starting a match with null home team should throw an exception");
    }

    @Test
    public void testStartMatchThrowsExceptionWhenAwayTeamIsNull() {
        assertThrows(IllegalArgumentException.class, () -> scoreboard.startMatch("HomeTeam1", null), "Starting a match with null away team should throw an exception");
    }

    @Test
    public void testStartMatchThrowsExceptionWhenHomeTeamIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> scoreboard.startMatch("", "AwayTeam1"), "Starting a match with empty home team should throw an exception");
    }

    @Test
    public void testStartMatchThrowsExceptionWhenAwayTeamIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> scoreboard.startMatch("HomeTeam1", ""), "Starting a match with empty away team should throw an exception");
    }

    @Test
    public void testStartMatchThrowsExceptionWhenHomeTeamIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> scoreboard.startMatch(" ", "AwayTeam1"), "Starting a match with blank home team should throw an exception");
    }

    @Test
    public void testStartMatchThrowsExceptionWhenAwayTeamIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> scoreboard.startMatch("HomeTeam1", " "), "Starting a match with blank away team should throw an exception");
    }

    @Test
    public void testUpdateScoreWithNegativeValuesThrowsException() {
        scoreboard.startMatch("HomeTeam1", "AwayTeam1");

        assertThrows(IllegalArgumentException.class, () -> scoreboard.updateScore("HomeTeam1", "AwayTeam1", -1, 3), "Updating score with negative values should throw an exception");
    }

    @Test
    public void testUpdateScoreThrowsExceptionWhenMatchDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> scoreboard.updateScore("NonExistentHomeTeam", "NonExistentAwayTeam", 1, 1), "Updating score for non-existent match should throw an exception");
    }

    @Test
    public void testFinishNonexistentMatchThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> scoreboard.finishMatch("Nonexistent", "Match"), "Finishing non-existent match should throw an exception");
    }
}
