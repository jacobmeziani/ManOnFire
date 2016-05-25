package importing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

import com.amazonaws.services.dynamodbv2.document.Item;

import maintenance.Counter;

public class ItemImport {
	
	static void readFile (String filename) {
		//reading file sauce
		//reads file - first line is listid
		///All other lines are new items into database
		
		BufferedReader fileReader = null;
		String line = "";
		boolean first_line = true;
		new FullReader();
		FullReader.scanItems();
		int listid = 0;
		Item item = null;
		List<String> attributes = null;
		String listname = "";
		int counter = 0;
		try {
			fileReader = new BufferedReader (new FileReader(filename));
			while ((line = fileReader.readLine())!=null) {
				System.out.println("writing line " + line);
				if (first_line) {
					String[] longstring = line.split(",");
					listid = Integer.parseInt(longstring[1]);
					item = getList(listid);
					first_line = false;
					item = FullReader.getListTable().getItem("Id",listid);
					attributes = item.getList("Attributes");
					listname = item.getString ("ListName");
					
					continue;
				}
				
				String [] longstring = line.split(",");
				if (longstring.length==2) {
					String itemname = longstring[0];
					String picpath = processPicPath(longstring[1]);
					FullReader.itemEntry(itemname, listname, listid, attributes,picpath);
				} else {
				
				FullReader.itemEntry (longstring[0],listname,listid,attributes);
				}
				counter++;
			}
			
			FullReader.updateListSize(listid, counter);
			FullReader.terminate();
			fileReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static String processPicPath(String input) {
		return input;
	}

	
	static Item getList (int listid) {
		try {
			Item item = FullReader.getListTable().getItem("Id",listid);
			System.out.println("inside item");
			System.out.println(item.get("ListName"));
			return item;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error ");
		} return null;
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		readFile("Maintenance/Inputs/item_import.csv");

	}

}
