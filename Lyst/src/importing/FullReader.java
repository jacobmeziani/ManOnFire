package importing;

import Database.*;
import maintenance.Counter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;

/**
 * FullReader class includes methods for classes lol
 * @author alexmarrero
 *
 */

@SuppressWarnings("rawtypes")
public class FullReader {
	private static DatabaseAccessor db;
	private static Table attributeTable;
	private static Table listsTable;
	private static Table listItemsTable;
	private static Table categoriesTable;
	private static Table counterTable;
	private static int list_id_counter;
	private static int item_id_counter;
	private static List<String> conflicts;
	private static HashMap hm;
	
	public FullReader () {
		if (db == null) {
			db = new DatabaseAccessor ();
			attributeTable = db.getDDB().getTable("Attributes");
			listsTable = db.getDDB().getTable("Lists");
			listItemsTable	= db.getDDB().getTable("ListItems");
			categoriesTable = db.getDDB().getTable("Categories");
			counterTable = db.getDDB().getTable("Counter");
			list_id_counter = counterTable.getItem("Name","ListID").getNumber("counter").intValue();
			item_id_counter = counterTable.getItem("Name","ItemID").getNumber("counter").intValue();
			conflicts = new ArrayList<String> ();
			conflicts.add("The Following Item names were already in the DB\n");
			conflicts.add("Item Name,List Name, Item ID");
		}
	}
	
	public static void scanItems() {
		hm = new HashMap();
		//DBA
		ItemCollection<ScanOutcome> collection = listItemsTable.scan();
		Iterator<Item> iterator = collection.iterator();
		while (iterator.hasNext()) {
			Item listitem = iterator.next();
			String name = listitem.getString("ItemName");
			try {
				int id =  listitem.getNumber("ItemID").intValue();
				hm.put(name, id);
			} catch (Exception e) {
				continue;
			}
		}
	}

	static List<String> parseCell (String inputstring) {
		String longstring = inputstring.substring(1,inputstring.length()-1);
		String [] things = longstring.split("; ");
		List<String> returnbro = new ArrayList<String>();
		for (String thing : things) {
			returnbro.add(thing);
		}
		return returnbro;
	}
	
	static List<Integer> parseCellInteger(String inputstring) {
		String longstring = inputstring.substring(1,inputstring.length()-1);
		String [] things = longstring.split("; ");
		List<Integer> returnbro = new ArrayList<Integer>();
		for (String thing : things) {
			returnbro.add(Integer.parseInt(thing));
		}
		return returnbro;
	}
	
	static void Parser (String filename) {
		//only method to call 
		BufferedReader fileReader = null;
		
		String line = "";
		boolean first_line = true;
		try {
			fileReader = new BufferedReader (new FileReader(filename));
			while ((line = fileReader.readLine())!=null) {
				if (first_line) {
					first_line = false;
					continue;
				}
				buildLine(line);
			}
			fileReader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	static void itemEntry (String itemname, String list, String picpath, int itemid, int listid) {
		//unused
		Item item = new Item()
				.withPrimaryKey("ItemName",itemname,"BelongingList",list)
				.withString("PicPath", picpath)
				.withNumber("Win",0)
				.withNumber("Loss",0)
				.withNumber("ItemID",itemid)
				.withNumber("ListID", listid);
		listItemsTable.putItem(item);
	}
	
	public static int getItemCounter(){
		return item_id_counter;
	}
	
	static void addOneListSize(int listid) {
		//DBA
		Map<String,String> expressionAttributeNames = new HashMap<String,String>();
		expressionAttributeNames.put("#L", "ListSize");
		Map<String,Object> expressionAttributeValues = new HashMap<String,Object>();
		expressionAttributeValues.put(":val1",1);
		listsTable.updateItem("Id",listid,
				"set #L = #L + :val2",
				expressionAttributeNames,expressionAttributeValues);
	}
	
	static int itemEntry (String itemname, String list, int listid, List<String> attributes) {
		int itemid;
		try {
			Integer tempid = (Integer) hm.get(itemname);
			itemid = tempid;
			//itemEntry(itemname,list, itemid, listid, attributes);
			String tempstring = itemname + "," + list + "," + tempid;
			conflicts.add(tempstring);
		} catch (NullPointerException e) {
			//itemEntry(itemname,list, item_id_counter, listid, attributes);
			itemid = item_id_counter;
			hm.put(itemname, item_id_counter);
			item_id_counter++;}

			Item item = new Item()
					.withPrimaryKey("ItemName",itemname,"BelongingList",list)
					.withNumber("Win",0)
					.withNumber("Loss",0)
					.withNumber("ItemID",itemid)
					.withNumber("Score", 75)
					.withNumber("ListID", listid);
			listItemsTable.putItem(item);

			attributeEntry(listid,0,itemid,"Overall");
			for (int att_value=1;att_value<(attributes.size()+1);att_value++) {
				attributeEntry(listid,att_value,itemid,attributes.get(att_value-1));
				//System.out.println("Adding " + attributes.get(att_value-1) + "to item " + itemname);
			}
		
		
		return itemid;
	}
	
	static void itemEntry (String itemname, String list, int itemid, int listid, List<String> attributes) {
		//unused
		Item item = new Item()
				.withPrimaryKey("ItemName",itemname,"BelongingList",list)
				.withNumber("Win",0)
				.withNumber("Loss",0)
				.withNumber("ItemID",itemid)
				.withNumber("ListID", listid);
		listItemsTable.putItem(item);
		
		attributeEntry(listid,0,itemid,"Overall");
		for (int att_value = 0;att_value<attributes.size();att_value++) {
			attributeEntry(listid,att_value,itemid,attributes.get(att_value));
			System.out.println("Adding " + attributes.get(att_value) + " to item " + itemname);
		}
	}
	
	static void attributeEntry (int listid,int att,int itemid,String attributename) {
		String codestring = encodeString(listid,att);
		Item item = new Item () 
				.withPrimaryKey("ItemID",itemid,"ListAttribute",codestring)
				.withNumber("ItemID",itemid)
				.withNumber("Average",75)
				.withNumber("n_rated",1)
				.withString("AttributeName",attributename);
		attributeTable.putItem(item);
	}
	
	static void buildLine (String line) {
		String [] tokens = line.split(",");
		String listname = tokens[0];
		String picpath = tokens[1];
		if (listname.equals("SKIP")) {
			System.out.println("Skipped list: " + picpath);
			return;
		}
		List<String> attributes	= parseCell(tokens[2]);
		List<String> items = parseCell(tokens[3]);
		String category = tokens[4];
		if (tokens.length == 6) {
			List<Integer> stringpackage = parseCellInteger(tokens[5]);
			insertList(list_id_counter,listname,attributes,picpath,items.size(),items,stringpackage);
		} else {
		insertList(list_id_counter,listname,attributes,picpath,items.size(),items);
		}
		addCategory(category,list_id_counter);
		list_id_counter++;
	}
	
	
	public static void addCategory (String Category, int listid) {
		try {
		if (db==null) {
			new FullReader();
		}
		
		Map <String,String> expressionAttributeNames = new HashMap<String,String>();
		expressionAttributeNames.put("#L", "ListIds");
		
		Map <String,Object> expressionAttributeValues = new HashMap<String,Object>();
		expressionAttributeValues.put(":val1",
				new HashSet <Integer>(Arrays.asList(listid)));
		categoriesTable.updateItem("CategoryName",Category,
				"add #L :val1",expressionAttributeNames,expressionAttributeValues);
		} catch (NullPointerException e) {e.printStackTrace();}	
	}
	
	public static Table getItemsTable() {
		return listItemsTable;
	}
	
	public static Table getAttributesTable() {
		return attributeTable;
	}
	static int getItemID(String itemname) {
		//unused

		try { //DBA
			QuerySpec spec = new QuerySpec()
					.withKeyConditionExpression("ItemName = :name")
					.withValueMap(new ValueMap()
							.withString(":name", itemname));
			ItemCollection<QueryOutcome> items = listItemsTable.query(spec);
			Iterator<Item> iterator = items.iterator();
			Item item = iterator.next();
			System.out.println(item.getNumber("ItemID"));

		} catch (NoSuchElementException e) {

		}
		return 0;
	}
	
	public static Table getCategoriesTable() {return categoriesTable;}
	
	static void insertList (int listid, String listname, List<String> attributes, String picpath, int listsize,List<String> items) {
		List<BigDecimal> stringPackage =new ArrayList<BigDecimal> ();
		for (String attribute:attributes) {
			stringPackage.add(new BigDecimal(0));
		}
		Item item = new Item ()
				.withPrimaryKey("Id",listid)
				.withString("ListName", listname)
				.withList("Attributes", attributes)
				.withString("PicPath",picpath)
				.withList("StringPackage", stringPackage)
				.withNumber("ListSize",listsize);
		listsTable.putItem(item);
		int max = 0;
		for (int i = 0;i<items.size();i++) {
			String listitem = items.get(i);
			int current = itemEntry(listitem,listname,listid,attributes);
			if (current > max) {
				max = current;
			}
		}
	}
	
	static void insertList (int listid, String listname, List<String> attributes, String picpath, int listsize,List<String> items,List<Integer> stringpackage) {
		Item item;
		if (stringpackage.size()!=attributes.size()) {
			System.out.println("String Package non-matching array lengths at: "+listname);
			item = new Item ()
					.withPrimaryKey("Id",listid)
					.withString("ListName", listname)
					.withList("Attributes", attributes)
					.withString("PicPath",picpath)
					.withNumber("ListSize",listsize);
		} else {
			item = new Item ()
					.withPrimaryKey("Id",listid)
					.withString("ListName", listname)
					.withList("Attributes", attributes)
					.withString("PicPath",picpath)
					.withList("StringPackage", stringpackage)
					.withNumber("ListSize",listsize);
		};
		
		listsTable.putItem(item);
		int max = 0;
		for (int i = 0;i<items.size();i++) {
			String listitem = items.get(i);
			int current = itemEntry(listitem,listname,listid,attributes);
			if (current > max) {
				max = current;
			}
		}
	}
	
	public static int getListNumber (String in) {
		String newint = in.substring(0,10);
		return Integer.parseInt(newint);
	}
	
	public static int getAttributeNumber (String in) {
		String newint = in.substring(12);
		return Integer.parseInt(newint);
	}
	
	public static Table getListTable () {
		return listsTable;
	}
	
	public static Table getCounterTable() {
		return counterTable;
	}
	public static String encodeString (int id,int att) {
		String zeros = "0000000000";
		String idstring = zeros + id;
		String attstring = zeros + att;
		String first = idstring.substring((idstring.length()-10));
		String last = attstring.substring((attstring.length()-2));
		String bigstring = first + "xx" + last;
		return bigstring;
	}
	
	public static String encodeString(int listid) {
		String zeros = "0000000000";
		String idstring = zeros + listid;
		String returnbro = idstring.substring((idstring.length()-10));
		return returnbro;
	} 

	static void writeConflicts () {
		//writes conflicts --> conflicts are times when an item being imported is already in the DB. the program will use that item number to continue
 
		try{

			FileWriter writer = new FileWriter ("Maintenance/Outputs/conflicts.csv");
			for (String line : conflicts) {
				writer.write(line);
				writer.write("\n");
			}
			writer.close();
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public static void terminate() {
		Counter.updateCounter("ItemID", item_id_counter, counterTable);
		Counter.updateCounter("ListID",list_id_counter , counterTable);
	}

	static void readCategories (String filename) {
		//some stuff to do still 
	}

	public static void main (String[] args) {
		new FullReader();
		scanItems();
		Parser("Maintenance/Inputs/import_test.csv");
		//System.out.println(item.getString("BelongingList"));
		writeConflicts();
		terminate();		
		
	}
}

