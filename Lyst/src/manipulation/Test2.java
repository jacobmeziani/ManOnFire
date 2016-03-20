package manipulation;

public class Test2 {
	//test 1 will make two elements: Lebron and Curry
	//Lebron will start at 50 for his value in a category
	//Curry will start at 99 for his value in a category
	//both will start at 10 data insertions
	//will run test for multiple variations of the same conditions: Lebron is rating of 0; this means user thinks they are equal. 
	//end result should be that lebron's stats and curry's stats will even out eventually. 
	Element lebron;
	Element curry;
	
	public Test2 () {
		
		this.lebron = new Element ("Lebz",500.0,10);
		this.curry = new Element ("Currsauce",990.0,10);
	}
	
	public void run (int number) {
		for (int i = 1;i<number;i++) {
			System.out.println(i);
			Matchup.run(lebron,curry,0);
		}
	}
}
