package manipulation;

public class Hub {
	
	
	public static void main(String[] args) {

		Test1 run1 = new Test1();
		Test2 run2 = new Test2();
		Test3 run3 = new Test3();
		run1.run(100);
		//run2.run(100);
		//run3.run(100); //test 3 has mixed results. Lebron's stats go down as Curry's stats dip considerably. Basically, in the algorithm, even beating scrubs gives up some Ls
	}

}
