package javaOOPprojectPackage;

public class Player extends Person {
	
	//Final minimum age made static so that it is shared across objects. 
	public static final int minimum_age = 17;
	
	private String position;
	private int jerseynumber;
	private int goals;
	private String playingStatus;
	
	//primary constructor
	public Player(String idNumber, String name, int age, String gender,String position, int jerseynumber, int goals, String playingStatus)
	{
		super(idNumber,name,age,gender);
		this.position = position;
		this.jerseynumber=jerseynumber;
		this.goals=goals;
		this.playingStatus=playingStatus;
		
	}
	
	public void validatePlayer()throws TeamException
	{
		if(getAge() < minimum_age)
		{
			throw new TeamException("Player must be at least " + minimum_age + "years old");
		}
	}
	
	//Goals being validated.
	public void updateGoals(int goalsScored)throws TeamException
	{
		if(goalsScored < 0 )
		{
			throw new TeamException("Goals scored cannot be negative.");
		}
		this.goals+=goalsScored;
	}
	
	//abstract method being implemented.
	@Override
	public String roles()
	{
		return "Player #" + jerseynumber + "("+ position +"):" + getName() + "|Goals: " + goals + "| Status: " + playingStatus;
	}

	//getters and setters
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getJerseynumber() {
		return jerseynumber;
	}

	public void setJerseynumber(int jerseynumber) {
		this.jerseynumber = jerseynumber;
	}

	public int getGoals() {
		return goals;
	}

	public void setGoals(int goals) {
		this.goals = goals;
	}

	public String getPlayingStatus() {
		return playingStatus;
	}

	public void setPlayingStatus(String playingStatus) {
		this.playingStatus = playingStatus;
	}

	@Override
	public String toString() {
		return "Player [" + super.toString() + ",position" + position + ", jerseynumber=" + jerseynumber + ", goals=" + goals
				+ ", playingStatus=" + playingStatus + "]";
	}
	
	
	
	
	

}
