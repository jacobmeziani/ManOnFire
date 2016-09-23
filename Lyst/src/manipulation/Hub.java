package manipulation;

public class Hub {
	
	
	public static void main(String[] args) {

		Test1 run1 = new Test1();
		Test2 run2 = new Test2();
		Test3 run3 = new Test3();
		run1.run(100);
		//run2.run(100);
		//run3.run(100); //test 3 has mixed results. Lebron's stats go down as Curry's stats dip considerably. Basically, in the algorithm, even beating scrubs gives up some Ls
	int steph = 40;
	int bron = 30;
	
	for(int i=11; i<111; i++){
		bron+=10;
		steph+=8;
		double stephen = (steph*10)/i;
		double brony = (bron*10)/i;
		System.out.println(i);
		System.out.println("Steph:" +stephen);
		System.out.println("Bron:" + brony);
	}
	
	System.out.println("Its the muthafuckin remix");
	for(int i=111; i<212; i++){
		bron+=8;
		steph+=10;
		double stephen = (steph*10)/i;
		double brony = (bron*10)/i;
		System.out.println(i);
		System.out.println("Steph:" +stephen);
		System.out.println("Bron:" + brony);
	}
	
	
	}

}
