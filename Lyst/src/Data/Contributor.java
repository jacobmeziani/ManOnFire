package Data;

import java.util.HashMap;

public class Contributor {

	static HashMap<String, String> hm;

//	 private Contributor() {
//	 hm = new HashMap<String,String>();
//	 hm.put("00","Tyler the Creator");
//	 }

	public static String getContributor(String key) {
		if (hm == null) {
			hm = new HashMap<String, String>();
			hm.put("00", "Tyler the Creator");
		}
		return hm.get(key);
	}

	public static boolean check(String key) {
		String test = getContributor(key);
		if (test == null)
			return false;
		return true;
	}
}
