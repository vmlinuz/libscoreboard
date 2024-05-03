package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Scoreboard {
    private final List<Match> matches;

    public Scoreboard() {
        this.matches = new ArrayList<>();
    }

    public void startMatch(String homeTeam, String awayTeam) {
        ValidationUtils.validateTeamName(homeTeam);
        ValidationUtils.validateTeamName(awayTeam);
        if (matches.stream().anyMatch(match -> match.getHomeTeam().equals(homeTeam) && match.getAwayTeam().equals(awayTeam))) {
            throw new IllegalArgumentException("Match already exists.");
        }
        matches.add(new Match(homeTeam, awayTeam));
    }

    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        ValidationUtils.validateTeamName(homeTeam);
        ValidationUtils.validateTeamName(awayTeam);
        ValidationUtils.validateScore(homeScore);
        ValidationUtils.validateScore(awayScore);
        Match match = matches.stream()
                .filter(m -> m.getHomeTeam().equals(homeTeam) && m.getAwayTeam().equals(awayTeam))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Match does not exist."));
        match.setHomeScore(homeScore);
        match.setAwayScore(awayScore);
    }

    public void finishMatch(String homeTeam, String awayTeam) {
        ValidationUtils.validateTeamName(homeTeam);
        ValidationUtils.validateTeamName(awayTeam);
        boolean removed = matches.removeIf(match -> match.getHomeTeam().equals(homeTeam) && match.getAwayTeam().equals(awayTeam));
        if (!removed) {
            throw new IllegalArgumentException("Match does not exist or has already been finished.");
        }
    }

    public List<String> getSummary() {
        return matches.stream()
                .sorted(Comparator.comparingInt(Match::getTotalScore).reversed()
                        .thenComparing(Match::getStartTime, Comparator.reverseOrder()))
                .map(match -> match.getHomeTeam() + " " + match.getHomeScore() + " - "
                        + match.getAwayTeam() + " " + match.getAwayScore())
                .collect(Collectors.toList());
    }

    public int getScoreForATeam(String team) {
        ValidationUtils.validateTeamName(team);
        return matches.stream()
                .filter(match -> match.getHomeTeam().equals(team) || match.getAwayTeam().equals(team))
                .mapToInt(match -> match.getHomeTeam().equals(team) ? match.getHomeScore() : match.getAwayScore())
                .sum();
    }
}
