package javaOOPprojectPackage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class StadiumFixtureManagement {
    private final Map<String, Stadium> stadiums = new LinkedHashMap<>();
    private final Map<String, Fixture> fixtures = new LinkedHashMap<>();

    public void registerStadium(Stadium stadium) throws FixtureException {
        rejectIf(stadium == null, "Stadium is required.");
        if (stadiums.putIfAbsent(key(stadium.getId()), stadium) != null) {
            throw new FixtureException("Stadium ID already registered: " + stadium.getId());
        }
    }

    public Stadium findStadium(String id) throws FixtureException {
        Stadium stadium = stadiums.get(key(id));
        rejectIf(stadium == null, "Unknown stadium: " + id);
        return stadium;
    }

    public Fixture findFixture(String id) throws FixtureException {
        Fixture fixture = fixtures.get(key(id));
        rejectIf(fixture == null, "Unknown fixture: " + id);
        return fixture;
    }

    public List<Stadium> getStadiums() {
        return Collections.unmodifiableList(new ArrayList<>(stadiums.values()));
    }

    public List<Fixture> getFixtures() {
        return Collections.unmodifiableList(new ArrayList<>(fixtures.values()));
    }

    public Fixture scheduleFixture(String id, FootballTeam home, FootballTeam away,
                                   String stadiumId, LocalDateTime kickoff) throws FixtureException {
        String fixtureKey = key(id);
        rejectIf(fixtures.containsKey(fixtureKey), "Fixture ID already exists: " + id);

        validateTeam(home);
        validateTeam(away);
        if (key(home.getRegistrationNumber()).equals(key(away.getRegistrationNumber()))) {
            throw new FixtureException("A team cannot play against itself.");
        }

        Stadium stadium = findStadium(stadiumId);
        checkSlot(null, home, away, stadium, kickoff);
        Fixture fixture = new Fixture(id.trim(), home, away, stadium, kickoff);
        fixtures.put(fixtureKey, fixture);
        return fixture;
    }

    public void rescheduleFixture(String fixtureId, String stadiumId, LocalDateTime kickoff)
            throws FixtureException {
        Fixture fixture = scheduledFixture(fixtureId);
        validateTeam(fixture.getHomeTeam());
        validateTeam(fixture.getAwayTeam());
        Stadium stadium = findStadium(stadiumId);
        checkSlot(fixture, fixture.getHomeTeam(), fixture.getAwayTeam(), stadium, kickoff);

        for (MatchOfficial official : fixture.getOfficials()) {
            checkOfficialSlot(fixture, official, kickoff);
        }
        fixture.moveTo(stadium, kickoff);
    }

    public void assignOfficial(String fixtureId, MatchOfficial official) throws FixtureException {
        Fixture fixture = scheduledFixture(fixtureId);
        rejectIf(official == null, "Match official is required.");
        rejectIf(fixture.hasOfficial(official.getIdNumber()), "Official is already assigned to this fixture.");
        checkOfficialSlot(fixture, official, fixture.getKickoff());
        fixture.addOfficial(official);
    }

    public void recordResult(String fixtureId, int homeScore, int awayScore) throws FixtureException {
        Fixture fixture = scheduledFixture(fixtureId);
        rejectIf(homeScore < 0 || awayScore < 0, "Match scores cannot be negative.");
        fixture.complete(homeScore, awayScore);
    }

    public void cancelFixture(String fixtureId) throws FixtureException {
        scheduledFixture(fixtureId).cancel();
    }

    private Fixture scheduledFixture(String fixtureId) throws FixtureException {
        Fixture fixture = findFixture(fixtureId);
        rejectIf(fixture.getStatus() != Fixture.Status.SCHEDULED, "Only scheduled fixtures can be changed.");
        return fixture;
    }

    private static void validateTeam(FootballTeam team) throws FixtureException {
        rejectIf(team == null || !team.eligibleForFixture(), "Both teams must be registered and eligible to play.");
        Stadium.requiredText(team.getRegistrationNumber(), "Team registration number");
    }

    private void checkSlot(Fixture moving, FootballTeam home, FootballTeam away,
                           Stadium stadium, LocalDateTime kickoff) throws FixtureException {
        rejectIf(kickoff == null, "Kickoff date and time are required.");

        for (Fixture existing : fixtures.values()) {
            if (!sharesSlot(existing, moving, kickoff)) {
                continue;
            }
            if (existing.hasTeam(home) || existing.hasTeam(away)) {
                throw new FixtureException("One of these teams already has a fixture at this time.");
            }
            if (existing.getStadium() == stadium) {
                throw new FixtureException("The stadium is already booked at this time.");
            }
        }
    }

    private void checkOfficialSlot(Fixture fixture, MatchOfficial official, LocalDateTime kickoff)
            throws FixtureException {
        rejectIf(!official.isAvailable(), "Match official is unavailable: " + official.getName());
        for (Fixture existing : fixtures.values()) {
            if (sharesSlot(existing, fixture, kickoff) && existing.hasOfficial(official.getIdNumber())) {
                throw new FixtureException("Official already has a fixture at this time.");
            }
        }
    }

    private static boolean sharesSlot(Fixture existing, Fixture moving, LocalDateTime kickoff) {
        return existing != moving
                && existing.getStatus() != Fixture.Status.CANCELLED
                && existing.getKickoff().equals(kickoff);
    }

    private static void rejectIf(boolean problem, String message) throws FixtureException {
        if (problem) {
            throw new FixtureException(message);
        }
    }

    private static String key(String id) throws FixtureException {
        return Stadium.requiredText(id, "ID").toLowerCase(Locale.ROOT);
    }
}
