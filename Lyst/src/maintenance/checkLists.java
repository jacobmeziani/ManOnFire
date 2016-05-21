package maintenance;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;

import Database.DatabaseAccessor;

public class checkLists {
	
	static void checkStringPackage(DatabaseAccessor db) throws IOException {
		
		String filename = "Maintenance/Outputs/StringPackage.csv";
		Table lists = db.getDDB().getTable("Lists");
		ScanSpec spec = new ScanSpec()
				.withFilterExpression("attribute_not_exists(StringPackage)");
		ItemCollection<ScanOutcome> items = lists.scan(spec);
		
		FileWriter writer = new FileWriter(filename);
		List<String> temp_attributes;
		
		String firstline = "Insert String package number below";
		writer.append(firstline);
		for (Item item:items) {
			System.out.println("ITEM: "+ item.getString("ListName"));
			writer.append("\n");
			String line = "ListID: "+ item.getInt("Id") + "\n" + "ListName: " + item.getString("ListName");
			writer.append(line);
			//temp = new ArrayList<String> ();
			
			temp_attributes = item.getList("Attributes");
			int countnumber = 1;
			for (String attribute:temp_attributes) {
				writer.append("\n"+countnumber+":,Attribute:,"+attribute+"\n,String Package:");
				countnumber++;
				
			}
		}
		writer.close();
	}
	
	
	static void checkList (DatabaseAccessor db, int listid) {
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
		checkStringPackage(new DatabaseAccessor());
		} catch (Exception e) {System.out.println("exception");e.printStackTrace();}
	}

}
