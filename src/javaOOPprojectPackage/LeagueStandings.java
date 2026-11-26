package javaOOPprojectPackage;

import java.util.ArrayList;
import java.util.List;

public final class LeagueStandings {
    private LeagueStandings() {
    }

    /**
     * Recalculates points from completed fixtures and returns teams in points order.
     * Teams with equal points keep their original order.
     */
    public static List<FootballTeam> calculate(List<FootballTeam> teams, List<Fixture> fixtures)
            throws TeamException {
        List<String> teamIds = new ArrayList<>();
        int[] points = new int[teams.size()];

        for (FootballTeam team : teams) {
            String id = teamId(team);
            if (findTeamIndex(teamIds, id) >= 0) {
                throw new TeamException("Duplicate team registration number: " + id);
            }
            teamIds.add(id);
        }

        for (Fixture fixture : fixtures) {
            if (fixture.getStatus() != Fixture.Status.COMPLETED) {
                continue;
            }

            int home = findTeamIndex(teamIds, teamId(fixture.getHomeTeam()));
            int away = findTeamIndex(teamIds, teamId(fixture.getAwayTeam()));
            if (home < 0 || away < 0) {
                throw new TeamException("Fixture " + fixture.getId()
                        + " contains a team missing from the standings team list.");
            }

            int homeScore = fixture.getHomeScore();
            int awayScore = fixture.getAwayScore();
            if (homeScore > awayScore) {
                points[home] += 3;
            } else if (awayScore > homeScore) {
                points[away] += 3;
            } else {
                points[home]++;
                points[away]++;
            }
        }

        List<FootballTeam> standings = new ArrayList<>(teams);
        for (int i = 0; i < standings.size(); i++) {
            standings.get(i).setPoints(points[i]);
        }

        // Move lower-scoring teams to the right by swapping adjacent teams.
        for (int end = standings.size() - 1; end > 0; end--) {
            for (int i = 0; i < end; i++) {
                FootballTeam left = standings.get(i);
                FootballTeam right = standings.get(i + 1);
                if (left.getPoints() < right.getPoints()) {
                    standings.set(i, right);
                    standings.set(i + 1, left);
                }
            }
        }
        return standings;
    }

    private static int findTeamIndex(List<String> teamIds, String id) {
        for (int i = 0; i < teamIds.size(); i++) {
            if (teamIds.get(i).equalsIgnoreCase(id)) {
                return i;
            }
        }
        return -1;
    }

    private static String teamId(FootballTeam team) throws TeamException {
        if (team == null || team.getRegistrationNumber() == null
                || team.getRegistrationNumber().trim().isEmpty()) {
            throw new TeamException("A team registration number is required for standings.");
        }
        return team.getRegistrationNumber().trim();
    }
}
