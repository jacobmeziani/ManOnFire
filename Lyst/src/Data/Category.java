package Data;

import java.util.LinkedHashSet;

public class Category {
	//this is the first stop for a new category into the server. no nested categories here. these are part of catlist class
	String name;
	LinkedHashSet<String> subCats;
	LinkedHashSet<String> subLysts;

	
	Category (String name, LinkedHashSet<String>subCats, LinkedHashSet<String>subLysts) {
		this.name=name;
		this.subCats=subCats;
		this.subLysts=subLysts;
	}
	
	public boolean is_final () {
		//not currently used
		if (subCats==null) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean matchname (String name) {
		//not currently used
		if (name == this.name) {
			return true;
		} else {return false;}
	}	
}
