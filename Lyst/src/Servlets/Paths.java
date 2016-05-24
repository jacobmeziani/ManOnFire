package Servlets;

public class Paths {
	
	static String stringpath;
	
	public static String getPicPath () {
		
		if (stringpath==null) {
			if (System.getProperty("os.name").equals("Mac OS X")) {
				stringpath = "/Users/alexmarrero/Desktop/RememberTheTitans/pictures"; //for bruce
				System.out.println("bruce mac shit");
			}
			else {
				stringpath =  "C:\\Users\\Jacob\\Desktop\\WebImages"; //for drama 
			}
		}
		
		return stringpath;
	}
}
