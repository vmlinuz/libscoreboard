package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
    public void testScoreboardSummary() {
        scoreboard.startMatch("HomeTeam1", "AwayTeam1");
        scoreboard.updateScore("HomeTeam1", "AwayTeam1", 2, 3);

        scoreboard.startMatch("HomeTeam2", "AwayTeam2");
        scoreboard.updateScore("HomeTeam2", "AwayTeam2", 1, 1);

        List<String> summary = scoreboard.getSummary();
        assertEquals("HomeTeam1 2 - AwayTeam1 3", summary.get(0));
        assertEquals("HomeTeam2 1 - AwayTeam2 1", summary.get(1));
    }
}
