package javaOOPprojectPackage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Fixture {
    public enum Status { SCHEDULED, COMPLETED, CANCELLED }

    private final String id;
    private final FootballTeam homeTeam;
    private final FootballTeam awayTeam;
    private Stadium stadium;
    private LocalDateTime kickoff;
    private Status status = Status.SCHEDULED;
    private final List<MatchOfficial> officials = new ArrayList<>();
    private Integer homeScore;
    private Integer awayScore;

    Fixture(String id, FootballTeam homeTeam, FootballTeam awayTeam,
            Stadium stadium, LocalDateTime kickoff) {
        this.id = id;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        moveTo(stadium, kickoff);
    }

    public String getId() { return id; }
    public FootballTeam getHomeTeam() { return homeTeam; }
    public FootballTeam getAwayTeam() { return awayTeam; }
    public Stadium getStadium() { return stadium; }
    public LocalDateTime getKickoff() { return kickoff; }
    public Status getStatus() { return status; }
    public Integer getHomeScore() { return homeScore; }
    public Integer getAwayScore() { return awayScore; }

    public List<MatchOfficial> getOfficials() {
        return Collections.unmodifiableList(new ArrayList<>(officials));
    }

    boolean hasTeam(FootballTeam team) {
        String registration = team.getRegistrationNumber().trim();
        return registration.equalsIgnoreCase(homeTeam.getRegistrationNumber().trim())
                || registration.equalsIgnoreCase(awayTeam.getRegistrationNumber().trim());
    }

    boolean hasOfficial(String idNumber) {
        return officials.stream().anyMatch(official -> idNumber.equalsIgnoreCase(official.getIdNumber()));
    }

    void moveTo(Stadium stadium, LocalDateTime kickoff) {
        this.stadium = stadium;
        this.kickoff = kickoff;
    }

    void addOfficial(MatchOfficial official) {
        officials.add(official);
    }

    void complete(int homeScore, int awayScore) {
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.status = Status.COMPLETED;
    }

    void cancel() {
        status = Status.CANCELLED;
    }

    @Override
    public String toString() {
        return id + ": " + homeTeam.getTeamName() + " vs " + awayTeam.getTeamName()
                + " at " + stadium.getName() + ", " + kickoff + " (" + status + ")";
    }
}
