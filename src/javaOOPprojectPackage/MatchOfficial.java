package javaOOPprojectPackage;

public final class MatchOfficial extends Person {
    private final String officiatingRole;
    private final String rank;
    private boolean available = true;

    public MatchOfficial(String idNumber, String name, int age, String gender,
                         String officiatingRole, String rank) throws FixtureException {
        super(Stadium.requiredText(idNumber, "Official ID"), name, age, gender);
        this.officiatingRole = Stadium.requiredText(officiatingRole, "Officiating role");
        this.rank = Stadium.requiredText(rank, "Official rank");
    }

    public String getOfficiatingRole() { return officiatingRole; }
    public String getRank() { return rank; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String roles() {
        return officiatingRole;
    }
}
