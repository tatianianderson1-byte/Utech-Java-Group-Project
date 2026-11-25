package javaOOPprojectPackage;

import java.util.List;
import java.util.ArrayList;

public class FootballTeam {
	
	private String teamName;
	private String parish;
	private String registrationNumber;
	private int squadSize;
	private String leagueStatus;
	private int points;
	private List<Player> players;
	private List<Coach> coaches;
	

//primary key
	
public 	FootballTeam(String teamName, String parish, String registrationNumber, int squadSize,  String leagueStatus)
{
	this.teamName=teamName;
	this.parish=parish;
	this.registrationNumber=registrationNumber;
	this.squadSize=squadSize;
	this.leagueStatus=leagueStatus;
	this.points=0;
	this.players=new ArrayList<>();
	this.coaches=new ArrayList<>();
	
}
	
public boolean eligibleForFixture()
{
	//.equalsIgnoreCase works strcmp it checks if the words match ignoring different cases. 
	if(leagueStatus != null && leagueStatus.equalsIgnoreCase("Registered"))
	{
		return true;
	}
	else {
		return false;
	}
}
	
public void addPlayer(Player player)throws TeamException
{
	if(player == null)
	{
		throw new TeamException("Player cannot be null.");
	}
	player.validatePlayer();
	
	if(players.size()>= squadSize)
	{
		throw new TeamException("Squad is full (" + squadSize + "max!");
	}
	for(Player p: players)
	{
		if(p.getIdNumber().equalsIgnoreCase(player.getIdNumber()))
		{
			throw new TeamException("Player is already on this team");
		}
		if(p.getJerseynumber()==player.getJerseynumber())
		{
			throw new TeamException("Jersey number: " + player.getJerseynumber() + "is already there");
		}
			
	}
	players.add(player);

			
}
	
	
public Player findById(String idNumber)
{
	if(idNumber == null)
	{
		return null;
	}
	
	
	for(Player p: players)
	{
		if(p.getIdNumber().equalsIgnoreCase(idNumber)) {
			return p;
		}
	}
	return null;

}
	
	
public boolean removePlayer(String idNumber)
{
	if(idNumber ==null)
	{
		return false;
	}
	for(int i=0;i<players.size();i++)
	{
		if(idNumber.equalsIgnoreCase(players.get(i).getIdNumber()))
		{
			players.remove(i);
			return true;
		}
	}
	return false;
}
	
public void addCoach(Coach coach)throws TeamException
{
	if(coach == null)
	{
		throw new TeamException("Coach cannot be null");
	}
	
	for(Coach c : coaches)
	{
		if(c.getIdNumber().equalsIgnoreCase(coach.getIdNumber()))
		{
			throw new TeamException("Coach already assigned to this team.");
		}
	}
	
	
	
	coach.setTeamAssignment(this.registrationNumber);
	coaches.add(coach);
}
	
public boolean removeCoach(String idNumber)
{
	if(idNumber == null)
	{
		return false;
	}
	for(int i=0; i<coaches.size();i++)
	{
		if(idNumber.equalsIgnoreCase(coaches.get(i).getIdNumber()))
		{
			coaches.remove(i);
			return true;
		}
	}
	return false;
}
	
public void display()
{
	
	System.out.println("Team Name: " + teamName + "(" + registrationNumber + ")" + "-parish-" + parish);
	System.out.println("Status: " + leagueStatus + "-" + "points:" + points);
	System.out.println("Coaches - " + coaches.size() + ":" );
	for(Coach co : coaches)
	{
		System.out.println(" - " + co.roles());
	}
	System.out.println("players - " + players.size() + "/" + squadSize);
	for(Player p: players)
	{
		System.out.println(" - " + p.roles());
	}

}

public String getTeamName() {
	return teamName;
}

public void setTeamName(String teamName) {
	this.teamName = teamName;
}

public String getParish() {
	return parish;
}

public void setParish(String parish) {
	this.parish = parish;
}

public String getRegistrationNumber() {
	return registrationNumber;
}

public int getSquadSize() {
	return squadSize;
}

public void setSquadSize(int squadSize) throws TeamException{
	if(squadSize < players.size())
	{
		throw new TeamException("Squad size cannot be smaller than current number of players.");
	}
	if(squadSize <=0)
	{
		throw new TeamException("Squad size must be greater than zero");
	}
	
	this.squadSize=squadSize;
}

public String getLeagueStatus() {
	return leagueStatus;
}

public void setLeagueStatus(String leagueStatus) {
	this.leagueStatus = leagueStatus;
}

public int getPoints() {
	return points;
}

public void addPoints(int p)throws TeamException
{
	if(p<0)
	{
		throw new TeamException("Points cannot be negative.");
	}
	this.points+=p;
}

//This bypass the check so to prevent that the exception is added.
public void setPoints(int points) throws TeamException{
	if(points < 0)
	{
		throw new TeamException("Points cannot be negative.");
	}
	this.points = points;
}

public List<Player> getPlayers() {
	return players;
}


public List<Coach> getCoaches() {
	return coaches;
}




}
