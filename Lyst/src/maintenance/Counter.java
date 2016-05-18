package maintenance;
import java.util.Iterator;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;

import Database.DatabaseAccessor;

public class Counter {
	
	static int checkLists(Table lists, Table counter) {
		//no make public plis
		ItemCollection<ScanOutcome> collection = lists.scan();
		Iterator<Item> iterator = collection.iterator();
		int highest = 0;
		while (iterator.hasNext()) {
			Item listitem = iterator.next();
			int current = listitem.getNumber("Id").intValue();
			if (current > highest) {
				highest = current;
			}
		}
		
		int highcounter = counter.getItem("Name","ListID").getInt("counter");
		if (highcounter == highest) {
			return 0;
		} 
		return highest;
	}
	
	static int checkItems(Table items, Table counter) {
 		//no make public plis
		ItemCollection<ScanOutcome> collection = items.scan();
		Iterator<Item> iterator = collection.iterator();
		int highest = 0;
		while (iterator.hasNext()) {
			Item listitem = iterator.next();
			int current = listitem.getNumber("ItemID").intValue();
			if (current > highest) {
				highest = current;
			}
		}
		
		int highcounter = counter.getItem("Name","ItemID").getInt("counter");
		if (highcounter == highest) {
			return 0;
		} 
		return highest;
	}
	
	
	public static void updateCounter (String type,int value, Table counter) {
		Item item = new Item ()
				.withPrimaryKey("Name",type)
				.withNumber("counter",value);
		counter.putItem(item);
						
	}
	public static void main(String[] args) {
		DatabaseAccessor db = new DatabaseAccessor();
		Table lists = db.getDDB().getTable("Lists");
		Table items = db.getDDB().getTable("ListItems");
		Table counter = db.getDDB().getTable("Counter");
	
		int max = checkLists(lists,counter);
		if (max > 0) {
		updateCounter("ListID",max,counter);
		}
		max = checkItems (items,counter);
		if (max > 0) {
			updateCounter("ItemID",max,counter);
		}
		System.out.println("success");
	}

}
