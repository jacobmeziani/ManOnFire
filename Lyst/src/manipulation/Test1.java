package manipulation;

public class Test1 {
	//test 1 will make two elements: Lebron and Curry
	//Lebron will start at 50 for his value in a category
	//Curry will start at 99 for his value in a category
	//both will start at 10 data insertions
	//will run test for multiple variations of the same conditions: Lebron is rating of 5 better; this means user inputs 5 every time. 
	//end result should be that lebron's stats will tend to 100 while curry's will tend towards 50
	Element lebron;
	Element curry;
	
	public Test1 () {
		
		this.lebron = new Element ("Lebz",30.0,1);
		this.curry = new Element ("Currsauce",40.0,1);
	}
	
	public void run (int number) {
		for (int i = 1;i<number;i++) {
			System.out.println(i);
			Matchup.run(lebron,curry,1);
		}
		for (int i = 1;i<number;i++) {
			System.out.println(i);
			Matchup.run(curry,lebron,1);
		}
	}
}
