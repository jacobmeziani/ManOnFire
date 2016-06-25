package importing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;

import maintenance.Scores;

public class AttributeImport {
	
	static void readFile() {
			String filename = "Maintanance/Inputs/attribute_import.csv";
			//First line is information --> Attribute,listid,listname
			//
	 }
	 
	static void buildAttribute() {
		
	}
	
	static boolean addToList(Table lists,String attribute,int listid) {
		//attribute will be added to the last position of the attribute list
		try {
			
			//DBA
			Map<String,String> expressionAtrributeNames = new HashMap<String,String> ();
			expressionAtrributeNames.put("#L","Attributes");

			Map<String,Object> expressionAttributeValues = new HashMap<String,Object> ();
			List<String> attributes = new ArrayList<String>();
			attributes.add(attribute);
			expressionAttributeValues.put(":val1",attributes);

			
			lists.updateItem("Id",listid,
					"set #L = list_append(#L,:val1)",
					expressionAtrributeNames,expressionAttributeValues);

			return true;	
			
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	static boolean addToAttributes(Table listitems, Table attributes, Table lists, int listid, int att_number, String att_name) {
		//adds attribute DB item to each listitem in the list in question 

		//scan DB items table for all items that are in the list
		try {
			Item tempitem = lists.getItem("Id",listid);
			String listname = tempitem.getString("ListName");
			ScanSpec spec = new ScanSpec()
					.withFilterExpression("BelongingList = :name")
					.withValueMap(new ValueMap()
							.withString(":name",listname));
			ItemCollection<ScanOutcome> items = listitems.scan(spec);
			String encoded = FullReader.encodeString(listid, att_number);
			for (Item item : items ){
				String itemname = item.getString("ItemName");
				System.out.println(item.getString("ItemName"));
				int itemid = item.getNumber("ItemID").intValue();
				Item newitem = new Item()
						.withPrimaryKey("ItemID",itemid,"ListAttribute",encoded)
						.withNumber("Rating",100)
						.withNumber("Entries",0)
						.withNumber("Points",0)
						.withNumber("Wins",0)
						.withString("AttributeName",att_name);
				attributes.putItem(newitem);
				
				Scores.checkScore(itemid, itemname, listid);						
			}return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public static void main (String[] args) {
		new FullReader();
		Table lists = FullReader.getListTable();
		Table listitems = FullReader.getItemsTable();
		Table att = FullReader.getAttributesTable();
		
		System.out.println(addToAttributes(listitems,att,lists,1001,4,"Twerking"));
	}
}
