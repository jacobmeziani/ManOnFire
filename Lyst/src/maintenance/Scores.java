package maintenance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;

import importing.FullReader;

public class Scores {
	
	
	public static boolean checkScore(int itemid, String itemname, int listid ) {
		//checks item and list so that score average is calculated correctly and is displayed in all lists correctly
		
		//get attributes
		Item item = null;
		try {
			item = FullReader.getListTable().getItem("Id",listid);
		} catch (Exception e) {
			System.out.println("list does not exist");
		}
		List<String> attributes = item.getList("Attributes");
		String listname = item.getString("ListName");
		
		BigDecimal itemscore;
		try {
			itemscore = FullReader.getItemsTable().getItem("ItemName",itemname,"BelongingList",listname).getNumber("Score");
		} catch (Exception e) {
			System.out.println(	"item exception -- no score available in item list" );
			itemscore = new BigDecimal (0) ;
		}
		
		int att_size = attributes.size();
		
		ItemCollection<QueryOutcome> items = getAttributes (FullReader.getAttributesTable(),itemid,listid);
		String overallscore_token = FullReader.encodeString(listid, 0);
		
		ArrayList<Integer> n_ranked = new ArrayList<Integer> ();
		ArrayList<BigDecimal> averages = new ArrayList<BigDecimal> ();
		
		
		boolean found_overall = false;
		
		String listattribute = "";
		int n_rank;
		int n_rank_total;               //deprecated ol bitch
		BigDecimal average;
		BigDecimal attribute_overall = null;
		
		for (Item attribute_item:items) {
			listattribute = attribute_item.getString ("ListAttribute");
			average = attribute_item.getNumber("Average");
			n_rank = attribute_item.getInt("n_rated");
			n_ranked.add(n_rank);
			if (FullReader.getListNumber(listattribute)!=listid) {
				System.out.println("something pooped"); //safety check
			}
			if (!found_overall) {
				if (listattribute.equals(overallscore_token)) {
					found_overall = true;
					attribute_overall = average;
					n_rank_total = n_rank;
				}
			} else {
				averages.add(average);
			}
		}
		
		boolean average_check = checkAverages(itemscore,attribute_overall,averages);
		boolean number_check = checkNumberOfRanks(n_ranked);
		
		if (!average_check) {
			System.out.println("average check throws false");
			return false;
		}
		if (!number_check)	{
			System.out.println("number check throws false");
		}
		return true;
	}
	
	
	static boolean checkAverages (BigDecimal itemlist_average, BigDecimal average, ArrayList<BigDecimal> averages ) {
		//check that average and itemlist_average are the same
		//check that averages computes to average
		
		//cheers
		
		//check averages first
		int compar = itemlist_average.compareTo(average);
		if (compar !=0) {
			System.out.println("averages do not match accross lists");
			return false;
		}
		
		BigDecimal total = new BigDecimal(0);
		
		int counter = 0;
		for (BigDecimal each:averages) {
			total = total.add(each);
			counter++;
		}
		
		BigDecimal calc_average = total.divide(new BigDecimal(counter));
		
		compar = calc_average.compareTo(average);
		if (compar!=0) {
			System.out.println("list values do not compute to the average");
			return false;
		}
			
		
		return true;
	}
	
	static boolean checkNumberOfRanks(ArrayList<Integer> inputs) {
		//do something
		//input -- n_ranked array 
		//check that all atributes have been ranked the same number of times. 
		
		int first = inputs.get(0);
		int other = 0;
		boolean allow_check_other = true;
		boolean other_set = false;
		for (int number : inputs) {
			if (number == first)  {
				continue;
			}
			if (allow_check_other) 	{
				if ((number == (first+1))||(number == (first-1)) ) {
					other = number;
					allow_check_other = false;
					other_set = true;
					continue;
				} else {
					return false;
				}
			} 
			
			if ((number == other) && (other_set)) {
				continue;
			}
			return false;
		} return true;
	}
	
	public static void check() {}
	 
	static ItemCollection<QueryOutcome> getAttributes(Table attributes, int itemid, int listid ) {
		
		String substring = FullReader.encodeString(listid);
		try{ 
			//DBA
			QuerySpec spec = new QuerySpec() 
					.withKeyConditionExpression("ItemID = :id and begins_with(ListAttribute,:str)" )
					.withValueMap(new ValueMap()
							.withNumber(":id",itemid)
							.withString(":str", substring));
			ItemCollection<QueryOutcome> items = attributes.query(spec);
			return items;
			
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	public static boolean checkScore(String itemname, String listname) {
		return true;
	}
	
	static void updateScores() {}
	

	public static void main (String [] args) {
		new FullReader();
		System.out.println(checkScore(1006,"Los Angeles",1001));
		

	}
}
