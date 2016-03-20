package manipulation;

public class Test3 {
	//test 1 will make two elements: Lebron and Curry
	//Lebron will start at 99 for his value in a category
	//Curry will start at 99 for his value in a category
	//both will start at 10 data insertions
	//will run test for multiple variations of the same conditions: Lebron is rating of 5; this means user thinks lebron is alot better. 
	//end result should be that lebron's stats should continue at 99 and curry should eat some D's. even if both at 99, curry's stat should take a hit for losing so much
	Element lebron;
	Element curry;
	
	public Test3 () {
		
		this.lebron = new Element ("Lebz",990.0,10);
		this.curry = new Element ("Currsauce",990.0,10);
	}
	
	public void run (int number) {
		for (int i = 1;i<number;i++) {
			System.out.println(i);
			Matchup.run(lebron,curry,5);
		}
	}
}