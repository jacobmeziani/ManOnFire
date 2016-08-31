package Data;

import java.util.ArrayList;
import java.util.Iterator;
import Database.DatabaseAccessor;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Item;
import java.util.LinkedHashSet;
import java.math.BigDecimal;

public class CategoryDB {
	
	ArrayList<Category> categories;
	ArrayList<ListServerInit> lists;

	@SuppressWarnings("unchecked")
	public CategoryDB(DatabaseAccessor db){
		ItemCollection<ScanOutcome> collection = db.getCategories();
		Iterator<Item> iterator = collection.iterator();
		ItemCollection<ScanOutcome>collectionlists= db.getLists();
		Iterator<Item> iteratorlist = collectionlists.iterator();
		Category temp;
		ListServerInit templist;
		ArrayList<Category> categories = new ArrayList<Category>();
		ArrayList<ListServerInit> lists = new ArrayList<ListServerInit>(); 
		while(iterator.hasNext()) {
			Item cat = iterator.next();
			String name = (String)cat.get("CategoryName");
			LinkedHashSet<String> subCats = (LinkedHashSet<String>) cat.get("SubCategories");
			LinkedHashSet<BigDecimal> subLysts = (LinkedHashSet<BigDecimal>) cat.get("ListIds");
			temp = new Category(name,subCats,subLysts);
			categories.add(temp);
		}
		try {
		while (iteratorlist.hasNext()) {
			Item list = iteratorlist.next();
			int id = list.getInt("Id");
			String name = (String)list.get("ListName");
			templist = new ListServerInit(id,name);
			lists.add(templist);
		}} finally {}
		this.categories = categories;
		this.lists = lists;
	}
	
	public ListServerInit getListObject (int id) {
		for (int i = 0;i<lists.size();i++) {
			ListServerInit list = lists.get(i);
			if (list.getID()==id) {
				return list;
			}
		} return null;
	}

	
	public String getListName (int id) {
		for (int i = 0;i<lists.size();i++) {
			ListServerInit list = lists.get(i);
			if (list.getID()==id) {
				return list.getName();
			}
		} return null;
	}
	
	public Category findCategory (String name) {
		for (int i = 0;i<categories.size();i++) {
			Category cat = categories.get(i);
			if (cat.name.equals(name)) {
				return cat;
			}
		} return null;
	}

//	public static void main (String args[]) {
//		DatabaseAccessor d = new DatabaseAccessor();
//		CategoryDB cdb = new CategoryDB(d);
//		HTMLCategory top = HTMLCategory.buildit(cdb);
//		String category_html = top.HTMLWriter();
//		System.out.println(category_html);
//	}
}
