package maintenance;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;

import Data.CategoryDB;
import Data.HTMLCategory;
import Database.DatabaseAccessor;
import importing.FullReader;

public class checkCategories {
	//purpose is to make sure all lists in leaf categories are represented in parent categories
	
	public static ArrayList<String> check () {
		DatabaseAccessor d = new DatabaseAccessor();
		CategoryDB cdb = new CategoryDB(d);
		HTMLCategory top = HTMLCategory.buildit(cdb);
		
		ArrayList<String> missing = new ArrayList<String>();
		top.checkLists(missing);
		for (String miss:missing) {
			System.out.println(miss);
		}
		return missing;
	}
	
	static void writeCategoryConflicts (ArrayList<String>missing) {
		//helper
		String filename = "Maintenance/Outputs/category_conflicts.csv";
		try {
			FileWriter writer = new FileWriter (filename);
			writer.write("Type,Category,List");
			for (String line:missing) {
				writer.write("\n");
				writer.write(line);
			}
			writer.close();
		} catch(Exception e) {e.printStackTrace();}
	}
	
	
	static boolean stripLists() {
		//this method will strip DB Categories of ListIDs in order to add new ones via the program
		//DBA
		try {
			ScanSpec scan_spec = new ScanSpec()
					.withFilterExpression("attribute_exists(ListIds)");
			ItemCollection<ScanOutcome> items = FullReader.getCategoriesTable().scan(scan_spec);

			for (Item item : items) {
				//DBA
				UpdateItemSpec update_spec = new UpdateItemSpec () 
						.withPrimaryKey("CategoryName",item.getString("CategoryName"))
						.withUpdateExpression("remove ListIds");

				FullReader.getCategoriesTable().updateItem(update_spec);

			}
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	static boolean updateCategories () {
		BufferedReader fileReader = null;
		String line = "";
		boolean first_line = true;
		try {
			fileReader = new BufferedReader (new FileReader("Maintenance/Outputs/category_conflicts.csv"));
			while((line = fileReader.readLine())!=null) {
				if (first_line) {
					first_line = false;
					continue;
				}
				buildLine(line);
				
			}
			fileReader.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	static void buildLine (String line) {
		//helper
		String [] tokens = line.split(",");
		String typeOfError = tokens[0];
		String category = tokens [1];
		String list = "";
		try {
			list = tokens[2];
		} catch (Exception e) {list = "NOTHERE";}
		if (typeOfError.equals("Category")) {
			System.out.println("Category " + category + " is missing list " + list);
			FullReader.addCategory(category,Integer.parseInt(list));
		}
	}  
	
	public static void main (String [] args) {
		//writeCategoryConflicts(check()); //   <--this goes into database
		//updateCategories();   			//    <-- this does not go into database 
		new FullReader();
		stripLists();
		
		
	}
}
