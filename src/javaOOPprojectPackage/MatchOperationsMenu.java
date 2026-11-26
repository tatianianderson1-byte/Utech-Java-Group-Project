package javaOOPprojectPackage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class MatchOperationsMenu {
    private final StadiumFixtureManagement fixtureManager;
    private final List<FootballTeam> teams;
    private final List<MatchOfficial> officials = new ArrayList<>();

    public MatchOperationsMenu(StadiumFixtureManagement fixtureManager, List<FootballTeam> teams) {
        this.fixtureManager = fixtureManager;
        this.teams = teams;
    }

    public void run(Scanner input) {
        while (true) {
            System.out.println("\n1. Record score");
            System.out.println("2. View match results");
            System.out.println("3. View league standings");
            System.out.println("4. Register match official");
            System.out.println("5. List match officials");
            System.out.println("6. Assign match official");
            System.out.println("0. Exit match operations");

            try {
                String option = read(input, "Choose an option: ");
                if (option == null) {
                    return;
                }
                switch (option) {
                    case "1":
                        recordScore(input);
                        break;
                    case "2":
                        showResults();
                        break;
                    case "3":
                        showStandings();
                        break;
                    case "4":
                        registerOfficial(input);
                        break;
                    case "5":
                        showOfficials();
                        break;
                    case "6":
                        assignOfficial(input);
                        break;
                    case "0":
                        return;
                    default:
                        System.out.println("Choose a number from 0 to 6.");
                }
            } catch (FixtureException | TeamException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private void recordScore(Scanner input) throws FixtureException {
        if (!showScheduledFixtures()) {
            return;
        }
        String fixtureId = readRequired(input, "Fixture ID: ");
        int homeScore = readNumber(input, "Home score: ");
        int awayScore = readNumber(input, "Away score: ");
        fixtureManager.recordResult(fixtureId, homeScore, awayScore);
        System.out.println("Result recorded.");
    }

    private boolean showScheduledFixtures() {
        boolean found = false;
        for (Fixture fixture : fixtureManager.getFixtures()) {
            if (fixture.getStatus() == Fixture.Status.SCHEDULED) {
                System.out.println(fixture);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No scheduled fixtures.");
        }
        return found;
    }

    private void showResults() {
        boolean found = false;
        for (Fixture fixture : fixtureManager.getFixtures()) {
            if (fixture.getStatus() == Fixture.Status.COMPLETED) {
                System.out.println(fixture.getId() + " | "
                        + fixture.getHomeTeam().getTeamName() + " " + fixture.getHomeScore()
                        + " - " + fixture.getAwayScore() + " "
                        + fixture.getAwayTeam().getTeamName());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No completed matches.");
        }
    }

    private void showStandings() throws TeamException {
        List<FootballTeam> standings =
                LeagueStandings.calculate(teams, fixtureManager.getFixtures());
        if (standings.isEmpty()) {
            System.out.println("No teams in the league.");
            return;
        }
        System.out.println("Team | Points");
        for (FootballTeam team : standings) {
            System.out.println(team.getTeamName() + " | " + team.getPoints());
        }
    }

    private void registerOfficial(Scanner input) throws FixtureException {
        String id = readRequired(input, "Official ID: ");
        if (findOfficial(id) != null) {
            throw new FixtureException("That official ID is already registered.");
        }
        String name = readRequired(input, "Name: ");
        int age = readNumber(input, "Age: ");
        if (age < 0) {
            throw new FixtureException("Age cannot be negative.");
        }
        String gender = readRequired(input, "Gender: ");
        String role = readRequired(input, "Officiating role: ");
        String rank = readRequired(input, "Rank: ");
        officials.add(new MatchOfficial(id, name, age, gender, role, rank));
        System.out.println("Official registered.");
    }

    private MatchOfficial findOfficial(String id) {
        for (MatchOfficial official : officials) {
            if (official.getIdNumber().equalsIgnoreCase(id)) {
                return official;
            }
        }
        return null;
    }

    private void showOfficials() {
        if (officials.isEmpty()) {
            System.out.println("No match officials registered.");
        }
        for (MatchOfficial official : officials) {
            System.out.println(official.getIdNumber() + " | " + official.getName()
                    + " | " + official.getOfficiatingRole() + " | " + official.getRank());
        }
    }

    private void assignOfficial(Scanner input) throws FixtureException {
        if (officials.isEmpty()) {
            showOfficials();
            return;
        }
        if (!showScheduledFixtures()) {
            return;
        }
        String fixtureId = readRequired(input, "Fixture ID: ");
        showOfficials();
        MatchOfficial official = findOfficial(readRequired(input, "Official ID: "));
        if (official == null) {
            throw new FixtureException("Official ID was not found.");
        }
        fixtureManager.assignOfficial(fixtureId, official);
        System.out.println("Official assigned.");
    }

    private static String read(Scanner input, String prompt) {
        System.out.print(prompt);
        if (input.hasNextLine()) {
            return input.nextLine().trim();
        }
        return null;
    }

    private static String readRequired(Scanner input, String prompt) throws FixtureException {
        String value = read(input, prompt);
        if (value == null || value.isEmpty()) {
            throw new FixtureException("A value is required.");
        }
        return value;
    }

    private static int readNumber(Scanner input, String prompt) throws FixtureException {
        try {
            return Integer.parseInt(readRequired(input, prompt));
        } catch (NumberFormatException exception) {
            throw new FixtureException("Enter a whole number.");
        }
    }

    // Run this class in Eclipse to try the menu with one sample fixture.
    public static void main(String[] args) throws FixtureException {
        FootballTeam home = new FootballTeam("Kingston FC", "Kingston", "T1", 20, "Registered");
        FootballTeam away = new FootballTeam("Harbour FC", "Kingston", "T2", 20, "Registered");
        List<FootballTeam> teams = new ArrayList<>();
        teams.add(home);
        teams.add(away);

        StadiumFixtureManagement manager = new StadiumFixtureManagement();
        manager.registerStadium(new Stadium("S1", "National Stadium", "Kingston", 1000, "Good"));
        manager.scheduleFixture("F1", home, away, "S1", LocalDateTime.of(2026, 12, 1, 16, 0));

        Scanner input = new Scanner(System.in);
        MatchOperationsMenu menu = new MatchOperationsMenu(manager, teams);
        menu.run(input);
    }
}
