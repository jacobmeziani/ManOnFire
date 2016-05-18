package maintenance;

import com.amazonaws.services.dynamodbv2.document.ItemCollection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;

import importing.FullReader;

public class PicPath {
	
	
	static void writeItemReport () {
		//returns all list items that have no pic path specified
		String filename = "Maintenance/Outputs/item_picpaths.csv";
		
		new FullReader();
		try {
			//DBA 
			ScanSpec spec = new ScanSpec()
					.withFilterExpression("attribute_not_exists(PicPath)");
			
			ItemCollection<ScanOutcome> items = FullReader.getItemsTable().scan(spec);
			Iterator<Item>	iterator = items.iterator();
			FileWriter writer = new FileWriter(filename);
			String firstline = "this file includes Items that do not currently have specified picture paths. Fill in picpath\n"
					+ "Item Name,List Name,PicPath";
			writer.write(firstline);		
			String line = "";
			while (iterator.hasNext()) {
				writer.write("\n");
				Item item = iterator.next();
				System.out.println(item.getString("ItemName"));
				line = item.getString("ItemName") + "," + item.getString("BelongingList");
				writer.write(line);
			}
			writer.close();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static void writeListReport () {
		//returns all list items that have no pic path specified
		String filename = "Maintenance/Outputs/list_picpaths.csv";
		
		new FullReader();
		try {
			//DBA 
			ScanSpec spec = new ScanSpec()
					.withFilterExpression("attribute_not_exists(PicPath)");
							
			ItemCollection<ScanOutcome> items = FullReader.getListTable().scan(spec);
			Iterator<Item>	iterator = items.iterator();
			FileWriter writer = new FileWriter(filename);
			String firstline = "this file includes lists that do not currently have specified picture paths. Fill in picpath\n"
					+ "ListID,List Name,PicPath";
			writer.write(firstline);		
			String line = "";
			while (iterator.hasNext()) {
				writer.write("\n");
				Item item = iterator.next();
				line = item.getNumber("Id").intValue()+","+item.getString("ListName");
				writer.write(line);
			}
			writer.close();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	static void updatePaths(String filename) {
		if (filename.equals("Maintenance/Outputs/item_picpaths.csv")) {
			try {
				//updating picpaths for items 
				BufferedReader fileReader = null;
				new FullReader();
				String line = "";
				boolean firstline = true;
				boolean secondline = true;
				fileReader = new BufferedReader(new FileReader(filename));
				while ((line = fileReader.readLine())!=null) {
					if (firstline) {
						firstline=false;
						continue;
					} else if (secondline) {
						secondline=false;
						continue;
					}
					String[] tokens= line.split(",");
					if (tokens.length == 3) {
						String itemname = tokens[0];
						String listname = tokens[1];
						String picpath = tokens[2];
						
						Map<String,String> expressionAttributeNames = new HashMap<String,String>();
						expressionAttributeNames.put("#L","PicPath");
						Map<String,Object> expressionAttributeValues = new HashMap<String,Object> ();
						expressionAttributeValues.put(":val1",picpath);
						
						FullReader.getItemsTable().updateItem("ItemName",itemname,"BelongingList",listname,
								"set #L = :val1",expressionAttributeNames,expressionAttributeValues);
						
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			writeItemReport();
		}
		
		if (filename.equals("Maintenance/Outputs/list_picpaths.csv")) {
			try {
				//updating picpaths for items 
				BufferedReader fileReader = null;
				new FullReader();
				String line = "";
				boolean firstline = true;
				boolean secondline = true;
				fileReader = new BufferedReader(new FileReader(filename));
				while ((line = fileReader.readLine())!=null) {
					if (firstline) {
						firstline=false;
						continue;
					} else if (secondline) {
						secondline=false;
						continue;
					}
					String[] tokens= line.split(",");
					if (tokens.length == 3) {
						int listid = Integer.parseInt(tokens[0]);
						String listname = tokens[1];
						String picpath = tokens[2];
						
						Map<String,String> expressionAttributeNames = new HashMap<String,String>();
						expressionAttributeNames.put("#L","PicPath");
						Map<String,Object> expressionAttributeValues = new HashMap<String,Object> ();
						expressionAttributeValues.put(":val1",picpath);
						
						FullReader.getListTable().updateItem("Id",listid,
								"set #L = :val1",expressionAttributeNames,expressionAttributeValues);
						
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			//after updates are completed, the report function is called again to trim the report down to the unfilled picpaths
			writeListReport();
		}
	}
	


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//writeListReport();
		//updatePaths("Maintenance/Outputs/list_picpaths.csv");
	}

}
