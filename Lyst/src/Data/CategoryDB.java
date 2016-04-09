package Data;

import java.util.ArrayList;
import java.util.Iterator;
import Database.DatabaseAccessor;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Item;
import java.util.LinkedHashSet;

public class CategoryDB {
	
	ArrayList<Category> categories;

	public CategoryDB(DatabaseAccessor db){
		ItemCollection<ScanOutcome> collection = db.getCategories();
		Iterator<Item> iterator = collection.iterator();
		Category temp;
		ArrayList<Category> categories = new ArrayList<Category>();
		while(iterator.hasNext()) {
			Item cat = iterator.next();
			String name = (String)cat.get("CategoryName");
			LinkedHashSet<String> subCats = (LinkedHashSet<String>) cat.get("subCategories");
			LinkedHashSet<String> subLysts = (LinkedHashSet<String>) cat.get("ListIds");
			temp = new Category(name,subCats,subLysts);
			categories.add(temp);
		}
		this.categories = categories;
	}
	
	
	public Category findCategory (String name) {
		for (int i = 0;i<categories.size();i++) {
			Category cat = categories.get(i);
			if (cat.name.equals(name)) {
				return cat;
			}
		} return null;
	}
}
