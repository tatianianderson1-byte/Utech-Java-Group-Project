package javaOOPprojectPackage;

public class LeagueCommissioner extends Person {
	
	
	public LeagueCommissioner (String idNumber, String name, int age, String gender)
	{
		super(idNumber,name,age,gender);
	}
	
	public void approveRegistration(FootballTeam team)throws TeamException
	{
		if(team == null)
		{
			throw new TeamException("Team cannot be null");
		}
		//.isEmpty is a built in function of list
		if(team.getPlayers().isEmpty() || team.getCoaches().isEmpty())
		{
			throw new TeamException("Team must contain at least 1 player and 1 coach.");
		}
		team.setLeagueStatus("Registered");
	}
	
	public void suspendTeam(FootballTeam team)throws TeamException
	{
		if(team == null)
		{
			throw new TeamException("Team cannot be null.");
		}
		team.setLeagueStatus("Suspended");
	}
	
	@Override
	public String roles()
	{
		return "League Commissioner: " + getName();
	}
	
	
	
	
	
}
