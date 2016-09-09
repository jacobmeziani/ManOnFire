package importing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.dynamodbv2.document.Item;

import maintenance.Counter;

public class ImportList {

	static void readFile(String filename) {
		BufferedReader fileReader = null;
		String line = "";
		int lineNumber = 0;
		boolean addingNewList = false;
		new FullReader();
		FullReader.scanItems();
		int listid = 0;
		Item item = null;
		List<String> attributes = null;
		String listname = "";
		String listPicPath = "";
		List<Integer> stringPackages = null;
		List<String> categories = null;

		int newItemCounter = 0;
		try {
			fileReader = new BufferedReader(new FileReader(filename));
			while ((line = fileReader.readLine()) != null) {
				System.out.println("writing line " + line);
				if (lineNumber == 0) {
					String[] longstring = line.split(",");
					String newList = longstring[1];
					if (newList.equals("Yes")) {
						addingNewList = true;
						listid = FullReader.getCurrentListId();
					} else {
						listid = Integer.parseInt(longstring[3]);
						item = getList(listid);
						item = FullReader.getListTable().getItem("Id", listid);
						attributes = item.getList("Attributes");
						listname = item.getString("ListName");
					}
				} else if (lineNumber == 1) {
					if (addingNewList) {
						String[] longstring = line.split(",");
						listname = longstring[1];
						listPicPath = longstring[3];
					}
				} else if (lineNumber == 2) {
					if (addingNewList) {
						String[] longstring = line.split(",");
						String[] tempCategories = longstring[1].split("/");
						categories = new ArrayList<String>();
						for (int i = 0; i < tempCategories.length; i++) {
							categories.add(tempCategories[i]);
						}
						stringPackages = new ArrayList<Integer>();
						for (int i = 3; i < longstring.length; i++) {
							int stringPackage = Integer.parseInt(longstring[i]);
							stringPackages.add(stringPackage);
						}
					}
				} else if (lineNumber == 4) {
					if (addingNewList) {
						String[] longstring = line.split(",");
						attributes = new ArrayList<String>();
						for (int i = 3; i < longstring.length; i++) {
							attributes.add(longstring[i]);
						}
					}
				} else if (lineNumber > 4) {
					String[] longstring = line.split(",");
					String itemname = longstring[0];
					String picpath = longstring[1];
					ArrayList<Double> attributeValues = new ArrayList<Double>();
					for(int i =3; i <longstring.length; i++){
						attributeValues.add(Double.parseDouble(longstring[i]));
					}
					if(FullReader.addItemToDB(itemname, listname, listid, attributes, picpath, attributeValues)){
						newItemCounter++;
					};
				}
			lineNumber++;
			}

			if(addingNewList){
				FullReader.insertListAndCategories(listid, listname, attributes, listPicPath, newItemCounter, stringPackages, categories);
			}
			else{
			FullReader.updateListSize(listid, newItemCounter);
			}
			FullReader.terminate(addingNewList);
			fileReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static Item getList(int listid) {
		try {
			Item item = FullReader.getListTable().getItem("Id", listid);
			System.out.println("inside item");
			System.out.println(item.get("ListName"));
			return item;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error ");
		}
		return null;

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		readFile("C://Users//Jacob//Documents//Lists//actors.csv");
		FullReader.writeConflicts();

	}

}
