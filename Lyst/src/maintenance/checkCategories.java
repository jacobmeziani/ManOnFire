package maintenance;

import java.util.ArrayList;

import Data.CategoryDB;
import Data.HTMLCategory;
import Database.DatabaseAccessor;

public class checkCategories {
	//purpose is to make sure all lists in leaf categories are represented in parent categories
	
	public static void check () {
		DatabaseAccessor d = new DatabaseAccessor();
		CategoryDB cdb = new CategoryDB(d);
		HTMLCategory top = HTMLCategory.buildit(cdb);
		
		ArrayList<String> missing = new ArrayList<String>();
		top.checkLists(missing);
		for (String miss:missing) {
			System.out.println(miss);
		}
	}
	
	public static void main (String [] args) {
		check();
	}
}
