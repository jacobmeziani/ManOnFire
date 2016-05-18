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
		int max = FullReader.getItemCounter();
		Item item = null;
		List<String> attributes = null;
		String listname = "";
		int current = 0;
		try {
			fileReader = new BufferedReader (new FileReader(filename));
			while ((line = fileReader.readLine())!=null) {
				if (first_line) {
					String[] longstring = line.split(",");
					listid = Integer.parseInt(longstring[1]);
					item = getList(listid);
					attributes = getAttributes(item);
					first_line = false;
					item = FullReader.getListTable().getItem("Id",listid);
					attributes = item.getList("Attributes");
					listname = item.getString ("ListName");
					
					continue;
				}
				//String line is the item name
				
				current = FullReader.itemEntry (line,listname,listid,attributes);
				if (current > max) {
					max = current;
				}
				FullReader.terminate();
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static List<String> getAttributes(Item item) {
		List<String> newlsit = item.getList("Attributes");
		for (String list: newlsit) {
			System.out.println(list);
		}
		return newlsit;
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
		new FullReader();
		

	}

}
