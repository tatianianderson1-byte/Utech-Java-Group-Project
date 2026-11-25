package javaOOPprojectPackage;

public class Coach extends Person{
	
	private String qualification;
	private String coachingRole;
	private String teamAssignment;
	
	//primary constructor
	public Coach(String idNumber, String name, int age, String gender,String qualification,String coachingRole,String teamAssignment)
	{
		super(idNumber,name,age,gender);
		this.qualification=qualification;
		this.coachingRole=coachingRole;
		this.teamAssignment=teamAssignment;
	}


	//Getters and Setters
	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getCoachingRole() {
		return coachingRole;
	}

	public void setCoachingRole(String coachingRole) {
		this.coachingRole = coachingRole;
	}

	public String getTeamAssignment() {
		return teamAssignment;
	}

	public void setTeamAssignment(String teamAssignment) {
		this.teamAssignment = teamAssignment;
	}
	
	@Override
	public String roles()
	{
		return "Coach #" + getIdNumber() + "("+ coachingRole +"):" + getName() + "|Qualification: " + qualification + "| Team Assignment: " + teamAssignment;
	}


	@Override
	public String toString() {
		return "Coach [" + super.toString() + "qualification=" + qualification + ", Coaching Role=" + coachingRole + ", Team Assignment="
				+ teamAssignment + "]";
	}

	

}
