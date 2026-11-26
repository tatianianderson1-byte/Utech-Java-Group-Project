package javaOOPprojectPackage;

public final class Stadium {
    private final String id;
    private final String name;
    private final String location;
    private final int seatingCapacity;
    private String fieldCondition;

    public Stadium(String id, String name, String location, int seatingCapacity,
                   String fieldCondition) throws FixtureException {
        this.id = requiredText(id, "Stadium ID");
        this.name = requiredText(name, "Stadium name");
        this.location = requiredText(location, "Stadium location");
        if (seatingCapacity <= 0) {
            throw new FixtureException("Seating capacity must be positive.");
        }
        this.seatingCapacity = seatingCapacity;
        setFieldCondition(fieldCondition);
    }

    static String requiredText(String value, String label) throws FixtureException {
        if (value == null || value.trim().isEmpty()) {
            throw new FixtureException(label + " is required.");
        }
        return value.trim();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getLocation() { return location; }
    public int getSeatingCapacity() { return seatingCapacity; }
    public String getFieldCondition() { return fieldCondition; }

    public void setFieldCondition(String fieldCondition) throws FixtureException {
        this.fieldCondition = requiredText(fieldCondition, "Field condition");
    }

    @Override
    public String toString() {
        return name + " (" + location + ", capacity " + seatingCapacity
                + ", field condition: " + fieldCondition + ")";
    }
}
