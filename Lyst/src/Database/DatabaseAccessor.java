package Database;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.BatchGetItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.KeyAttribute;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableKeysAndAttributes;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.TableDescription;

import Data.Lyst;
import Data.LystItem;

public class DatabaseAccessor {

	static DynamoDB dynamoDB;
	Random random = new Random();
	static AmazonDynamoDBClient client;

	public DatabaseAccessor() {
		Region usWest2 = Region.getRegion(Regions.US_WEST_2);
		AmazonDynamoDBClient dbClient = new AmazonDynamoDBClient(new ProfileCredentialsProvider("jmeziani"));
		dbClient.setRegion(usWest2);
		client = dbClient;
		dynamoDB = new DynamoDB(dbClient);
	}
			
	
	public ItemCollection<ScanOutcome> getCategories () {
		//Used to get categories from the database to build tree
		Table categories = dynamoDB.getTable("Categories");
		//ScanRequest scanrequest = new ScanRequest().withTableName("Categories");
		//ScanResult result = client.scan(scanrequest);
		return categories.scan();
		
		
	}
	
	public ArrayList<Lyst> getRandomLists(int numberOfLists) {
		BatchGetItemOutcome outcome = getRandomListsFromDb(numberOfLists);

		ArrayList<Lyst> lysts = new ArrayList<Lyst>();
			List<Item> items = outcome.getTableItems().get("Lists");
			for (Item item : items) {
				String name = (String) item.get("ListName");
				String categories = (String) item.get("Categories");
				int size = item.getInt("ListSize");
				String picPath = (String) item.get("PicPath");
				Lyst lyst = new Lyst(name, categories, size, picPath);
				lysts.add(lyst);
			}
		return lysts;
	}

	public LystItem[] getNextCombatants(String category) {

		Table categories = dynamoDB.getTable("Categories");
		Table lists = dynamoDB.getTable("Lists");
		Table listItems = dynamoDB.getTable("ListItems");
		int listSize;
		String listToPullFrom = "";
		if (category.equals("Everything")) {
			ArrayList<Lyst> theCombatantList = getRandomLists(1);
			listToPullFrom = theCombatantList.get(0).getListName();
			listSize = theCombatantList.get(0).getSize();
		} 
		else 
		{
			Item categoryItem = categories.getItem("CategoryName", category);
			 LinkedHashSet<Integer> e = (LinkedHashSet<Integer>)categoryItem.get("ListIds");
		        ArrayList<Integer> listsInCategory = new ArrayList<Integer>();
		        listsInCategory.addAll(e);
		        int size = listsInCategory.size();
		        int randomIndex = random.nextInt(size);
		        int listKey = listsInCategory.get(randomIndex);
		        Item theCombatantList = lists.getItem("Id",listKey);
		        listToPullFrom = theCombatantList.getString("ListName");
		        listSize = theCombatantList.getInt("ListSize");
		        
		}
		
		Index index = listItems.getIndex("BelongingList-index");
		QuerySpec spec = new QuerySpec()
			    .withKeyConditionExpression("BelongingList = :v_belong")
			    .withValueMap(new ValueMap()
			        .withString(":v_belong",listToPullFrom));
		

		ItemCollection<QueryOutcome> items = index.query(spec);
		Iterator<Item> iter = items.iterator();
		
		int combatantIndex1 = random.nextInt(listSize);
		int combatantIndex2;
		do{
			combatantIndex2 = random.nextInt(listSize);		
		}
		while(combatantIndex2 == combatantIndex1);
		
		int smallerIndex,largerIndex;
		if(combatantIndex1>combatantIndex2){
			largerIndex = combatantIndex1;
			smallerIndex = combatantIndex2;			
		}
		else{
			
			largerIndex = combatantIndex2;
			smallerIndex = combatantIndex1;	
		}
		
		int counter =0;
		
		while (counter!= smallerIndex) {
		    iter.next();
		    counter++;
		}
		Item item1 = iter.next();
		System.out.println(item1.toJSONPretty());
		counter++;
		while (counter!= largerIndex) {
		    iter.next();
		    counter++;
		}
		Item item2 = iter.next();
		System.out.println(item2.toJSONPretty());
		LystItem[] lystItems = new LystItem[2];
		
		String name = item1.getString("ItemName");
		String picPath = item1.getString("PicPath");
		String belongingList = item1.getString("BelongingList");
		LystItem firstItem = new LystItem(name, belongingList, picPath);		
		name = item2.getString("ItemName");
		picPath = item2.getString("PicPath");
		LystItem secondItem = new LystItem(name, belongingList, picPath);
		lystItems[0] = firstItem;
		lystItems[1] = secondItem;
		return lystItems;
		
	}

	private BatchGetItemOutcome getRandomListsFromDb(int numberOfLists) {
		Table lists = dynamoDB.getTable("Lists");
		TableDescription description = lists.describe();
		long count = description.getItemCount();
		int randomSeed = (int) count - numberOfLists;
		int startingIndex = random.nextInt(randomSeed);
		Object[] idsToGet = new Object[numberOfLists];
		for (int i = 0; i < numberOfLists; i++) {
			idsToGet[i] = startingIndex;
			startingIndex++;
		}
		TableKeysAndAttributes forumTableKeysAndAttributes = new TableKeysAndAttributes("Lists");
		// Add a partition key
		forumTableKeysAndAttributes.addHashOnlyPrimaryKeys("Id", idsToGet);
		BatchGetItemOutcome outcome = dynamoDB.batchGetItem(forumTableKeysAndAttributes);
		return outcome;
	}
}
