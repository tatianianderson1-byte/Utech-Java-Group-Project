package javaOOPprojectPackage;


//Parent abstract class.
public abstract class Person {

	//Encapsulation
	//Attributes 
	private String idNumber;
	private String name;
	private int age;
	private String gender;
	
	
	//Primary constructor
	public Person (String idNumber, String name, int age, String gender)
	{
		this.idNumber=idNumber;
		this.name=name;
		this.age=age;
		this.gender=gender;
	}
	
	//Abstract because it must be implemented by all subclasses.
	public abstract String roles();

	
	//Getters and setters
	public String getIdNumber() {
		return idNumber;
	}


	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getAge() {
		return age;
	}


	//validating age with custom exception.
	public void setAge(int age) throws TeamException{
		if(age < 0)
		{
			throw new TeamException("Age cannot be negative.");
		}
		this.age = age;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "Person [idNumber=" + idNumber + ", name=" + name + ", age=" + age + ", gender=" + gender + "]";
	}
	
	
}

//testing gitbub desktop
