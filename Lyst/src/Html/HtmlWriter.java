package Html;
import java.util.ArrayList;

import Data.Lyst;
import Database.DatabaseAccessor;


public class HtmlWriter {
	
	
	public ArrayList<Lyst> LoadListsMainPage(){
		
		DatabaseAccessor dbAccessor = new DatabaseAccessor();
		ArrayList<Lyst> lysts = dbAccessor.getRandomLists(6);
		return lysts;
	}

}
