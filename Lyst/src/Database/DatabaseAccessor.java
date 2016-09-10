package Database;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.BatchGetItemOutcome;
import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.KeyAttribute;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableKeysAndAttributes;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;

import Data.CategoryDB;
import Data.HTMLCategory;
import Data.Lyst;
import Data.LystItem;
import Data.Attribute;

public class DatabaseAccessor {
	
	static String rating_name = "Rating";
	static String ranking_name = "Ranking";
	
	private DynamoDB dynamoDB;
	Random random = new Random();
	static AmazonDynamoDBClient client;
	private static String htmlmenu;
	static private HashMap<String, ArrayList<Integer>> CategoryListIDs;

	public DatabaseAccessor() {
		Region usWest2 = Region.getRegion(Regions.US_WEST_2);
		DefaultAWSCredentialsProviderChain  providesauce = new DefaultAWSCredentialsProviderChain ();
		AmazonDynamoDBClient dbClient;
		try{
			AWSCredentials creds = providesauce.getCredentials();
			dbClient = new AmazonDynamoDBClient(creds);
		}
		catch(Exception e){
			dbClient = new AmazonDynamoDBClient(new ProfileCredentialsProvider("jmeziani"));
		}
		
		dbClient.setRegion(usWest2);
		client = dbClient;
		dynamoDB = new DynamoDB(dbClient);

		if (CategoryListIDs == null) {
			buildMaps();
		}
	}
	
	public DatabaseAccessor(boolean lite) {
		Region usWest2 = Region.getRegion(Regions.US_WEST_2);
		AmazonDynamoDBClient dbClient = new AmazonDynamoDBClient(new ProfileCredentialsProvider("jmeziani"));
		dbClient.setRegion(usWest2);
		client = dbClient;
		dynamoDB = new DynamoDB(dbClient);
		
		if ((CategoryListIDs == null)&(!lite)) {
			buildMaps();
		}
	}
	
	public List<String> getAttributes(int list_id) {
		Table lists = dynamoDB.getTable("Lists");
		
		Item item = lists.getItem("Id",list_id);
		List<String> atts = item.getList("Attributes");
		return atts;
//		String[] returnDude = new String[atts.size()];
//		String[] returnSauce = (String[]) atts.toArray();
//		return  returnSauce;
	}

	private void buildMaps() {
		Table categories = dynamoDB.getTable("Categories");
		CategoryListIDs = new HashMap<String, ArrayList<Integer>>();
		ItemCollection<ScanOutcome> collection = categories.scan();
		LinkedHashSet<BigDecimal> tempBigDecimals;
		for (Item item : collection) {
			String name = item.getString("CategoryName");
			tempBigDecimals = (LinkedHashSet<BigDecimal>) item.get("ListIds");
			ArrayList<Integer> ids = new ArrayList<Integer>();
			try {
				for (BigDecimal bigdecimal : tempBigDecimals) {
					ids.add(bigdecimal.intValue());
				}
				CategoryListIDs.put(name, ids);
			} catch (NullPointerException e) {
				CategoryListIDs.put(name, null);
			}
		}
		
		

		CategoryDB cdb = new CategoryDB(new DatabaseAccessor());
		HTMLCategory top = HTMLCategory.buildit(cdb);
		htmlmenu = top.HTMLWriter();
	}

	public String getMenu() {
		return htmlmenu;
	}

	public DynamoDB getDDB() {
		return dynamoDB;
	}

	public ItemCollection<ScanOutcome> getCategories() {
		// Used to get categories from the database to build tree
		Table categories = dynamoDB.getTable("Categories");
		// ScanRequest scanrequest = new
		// ScanRequest().withTableName("Categories");
		// ScanResult result = client.scan(scanrequest);
		return categories.scan();
	}
	
	public LystItem getItem(int itemid) {  //TODO: must return pic path as well sorry
		//
		Table items = dynamoDB.getTable("ListItems");
		ScanSpec spec = new ScanSpec()
				.withFilterExpression("ItemID = :id")
				.withValueMap(new ValueMap()
						.withNumber(":id", itemid));
		ItemCollection<ScanOutcome> listitems = items.scan(spec);
		
		Iterator <Item> iter =listitems.iterator();
		Item item = iter.next();
		
		String itemname = item.getString("ItemName");
		String picPath = item.getString("PicPath");
		String belongingList = item.getString("BelongingList");
		return new LystItem(itemname, belongingList, picPath);
		
	}
	
	public ArrayList<Integer> getRankedIDsOnlyIDs(int listid, int attributeNeeded) {
		Table attributes = dynamoDB.getTable("Attributes");
		String listidsubstring = Integer.toString(listid) + "-" + Integer.toString(attributeNeeded);
		Map<String,Object>expressionAttributeValues = new HashMap<String,Object>();
		expressionAttributeValues.put(":lid", listidsubstring);
		ScanSpec spec = new ScanSpec()
				.withFilterExpression("begins_with(ListAttribute, :lid)")
				.withValueMap(expressionAttributeValues);
		
		ArrayList <Map<String,Object>> infoMapList = new ArrayList <Map<String,Object>> ();
		
		
		try {
			ItemCollection<ScanOutcome> items = attributes.scan(spec);
			int count = items.getTotalScannedCount();
			System.out.println(count);
			Iterator<Item> iter = items.iterator();
			Map<String,Object> tempMap;
			while (iter.hasNext()) {
				Item item = iter.next();
				tempMap = new HashMap<String,Object>();
				tempMap.put("Rating",item.getInt("Rating"));
				tempMap.put("ItemID", item.getInt("ItemID"));
				tempMap.put("Ranking", item.getInt("Ranking"));
				
				infoMapList.add(tempMap);
			}
			
		} catch (Exception e) {
			System.out.println("shit went down sorry papo");
			e.printStackTrace();
		}
		

//		Comparator<Map<String,Object>> attributeComparator = new Comparator<Map<String,Object>>() {
//
//			public int compare(Map<String,Object> map1, Map<String,Object> map2) {
//				return ((Integer)map1.get("Ranking") - (Integer) map2.get("Ranking"));
//			}
//
//		};
		
		//infoMapList.sort(attributeComparator);
		
		ArrayList<Integer> returnSauce = new ArrayList<Integer> ();
		for (int i = 0; i < infoMapList.size(); i++) {
			Map<String, Object> tempMap = infoMapList.get(i);
			returnSauce.add((Integer) tempMap.get("ItemID"));
		}
		return returnSauce;
	}
	
	
	public ArrayList<Map<String,Object>> getRankedIDs(int listid, int attributeNeeded) {
		Table attributes = dynamoDB.getTable("Attributes");
		
//		Map<String,String> expressionAttributeNames = new HashMap<String, String>();
//		expressionAttributeNames.put("#ranking", "Ranking");
//		expressionAttributeNames.put("#rating", "Rating");
//		
		
		String listidsubstring = Integer.toString(listid) + "-" + Integer.toString(attributeNeeded);
		Map<String,Object>expressionAttributeValues = new HashMap<String,Object>();
		expressionAttributeValues.put(":lid", listidsubstring);
		
		ScanSpec spec = new ScanSpec()
				.withFilterExpression("begins_with(ListAttribute, :lid)")
				.withValueMap(expressionAttributeValues);
		
		ArrayList <Map<String,Object>> returnSauce = new ArrayList <Map<String,Object>> (); //TODO: rename plz
		
		
		try {
			ItemCollection<ScanOutcome> items = attributes.scan(spec);
			int count = items.getTotalScannedCount();
			System.out.println(count);
			Iterator<Item> iter = items.iterator();
			Map<String,Object> tempMap;
			while (iter.hasNext()) {
				Item item = iter.next();
				tempMap = new HashMap<String,Object>();
				tempMap.put("Rating",item.getInt("Rating"));
				tempMap.put("ItemID", item.getInt("ItemID"));
				tempMap.put("Ranking", item.getInt("Ranking"));
				
				returnSauce.add(tempMap);
			}
			
		} catch (Exception e) {
			System.out.println("shit went down sorry papo");
			e.printStackTrace();
		}
		

		Comparator<Map<String,Object>> attributeComparator = new Comparator<Map<String,Object>>() {

			public int compare(Map<String,Object> map1, Map<String,Object> map2) {
				return ((Integer)map1.get("Ranking") - (Integer) map2.get("Ranking"));
			}

		};
		
		//returnSauce.sort(attributeComparator);
		
		return returnSauce;
	}
	
	/**
	 * 
	 * @param itemID
	 * @param listid
	 * @return
	 */
	public ArrayList<Map<String, Object>> getAttributeItem(int itemID, int listid) {
		Table attributes = dynamoDB.getTable("Attributes");
		
		String listidstring = Integer.toString(listid) + "-";
		
		QuerySpec spec = new QuerySpec().withKeyConditionExpression("ItemID = :v_id and begins_with(ListAttribute, :v_listId)")
				.withValueMap(new ValueMap()
						.withNumber(":v_id", itemID)
						.withString(":v_listId", listidstring));
		
		ItemCollection<QueryOutcome> returnedattributes = attributes.query(spec);
		int count = returnedattributes.getTotalCount();

		ArrayList<Map<String, Object>> returnSauce = new ArrayList<Map<String, Object>> (count);
		
		Map<String, Object> itemMap;
		
		Iterator<Item> iterator = returnedattributes.iterator();
		Item item = null;
		while (iterator.hasNext()) {
			item = iterator.next();

			itemMap = new HashMap<String, Object>();
			
			String temp = item.getString("ListAttribute");
			int attributeNumber = Integer.parseInt(temp.split("-")[1]);
			
			itemMap.put("AttributeNumber", attributeNumber);
			itemMap.put("Rating", item.getInt("Rating"));
			itemMap.put("Ranking", item.getInt("Ranking"));
			itemMap.put("AttributeName", item.getString("AttributeName"));
			
			returnSauce.add(attributeNumber, itemMap);
		}
		return returnSauce;
	}
	
	

	public ItemCollection<ScanOutcome> getLists() {
		Table lists = dynamoDB.getTable("Lists");
		return lists.scan();
	}

	public ArrayList<Lyst> getRandomLists(String category, int numberOfLists) { // drama
		System.out.println("Okman here we are. The category is" +category);
		BatchGetItemOutcome outcome = getRandomListsFromDatabase(category, numberOfLists);

		ArrayList<Lyst> lysts = new ArrayList<Lyst>();
		List<Item> items = outcome.getTableItems().get("Lists");
		for (Item item : items) {
			String name = (String) item.get("ListName");
			int index = item.getInt("Id");
			ArrayList<String> attributes = (ArrayList<String>) item.get("Attributes");
			int size = item.getInt("ListSize");
			String picPath = (String) item.get("PicPath");
			Lyst lyst = new Lyst(name, index, attributes, size, picPath);
			lysts.add(lyst);
		}
		return lysts;
	}

	public Object[] getNextCombatants(String categoryOrList, boolean list) { // drama
																				// n
																				// bruce

		Table categories = dynamoDB.getTable("Categories");
		Table lists = dynamoDB.getTable("Lists");
		Table listItems = dynamoDB.getTable("ListItems");
		int listSize;
		Lyst listToPullFrom;
		if (!list) {
			ArrayList<Lyst> theCombatantList = getRandomLists(categoryOrList, 1);
			listToPullFrom = theCombatantList.get(0);
			listSize = theCombatantList.get(0).getSize();
		}
		// else if(!list)
		// {
		// Item categoryItem = categories.getItem("CategoryName",
		// categoryOrList);
		// LinkedHashSet<Integer> e =
		// (LinkedHashSet<Integer>)categoryItem.get("ListIds");
		// ArrayList<Integer> listsInCategory = new ArrayList<Integer>();
		// listsInCategory.addAll(e);
		// int size = listsInCategory.size();
		// int randomIndex = random.nextInt(size);
		// int listKey = listsInCategory.get(randomIndex);
		// Item item = lists.getItem("Id",listKey);
		// String name = (String) item.get("ListName");
		// ArrayList<String> attributes = (ArrayList<String>)
		// item.get("Attributes");
		// size = item.getInt(" ListSize");
		// String picPath = (String) item.get("PicPath");
		// listToPullFrom = new Lyst(name, listKey, attributes, size, picPath);
		// listSize = size;
		//
		// }
		else {
			// Todo: test this garbo

			int listKey = Integer.parseInt(categoryOrList);
			Item item = lists.getItem("Id", listKey);
			String name = (String) item.get("ListName");
			ArrayList<String> attributes = (ArrayList<String>) item.get("Attributes");
			int size = item.getInt("ListSize");
			String picPath = (String) item.get("PicPath");
			listToPullFrom = new Lyst(name, listKey, attributes, size, picPath);
			listSize = size;
		}

		Index index = listItems.getIndex("BelongingList-index");
		QuerySpec spec = new QuerySpec().withKeyConditionExpression("BelongingList = :v_belong")
				.withValueMap(new ValueMap().withString(":v_belong", listToPullFrom.getListName()));

		ItemCollection<QueryOutcome> items = index.query(spec);
		Iterator<Item> iter = items.iterator();

		int combatantIndex1 = random.nextInt(listSize);
		int combatantIndex2;
		do {
			combatantIndex2 = random.nextInt(listSize);
		} while (combatantIndex2 == combatantIndex1);

		int smallerIndex, largerIndex;
		if (combatantIndex1 > combatantIndex2) {
			largerIndex = combatantIndex1;
			smallerIndex = combatantIndex2;
		} else {

			largerIndex = combatantIndex2;
			smallerIndex = combatantIndex1;
		}

		int counter = 0;

		while (counter != smallerIndex) {
			iter.next();
			counter++;
		}
		Item item1 = iter.next();
		System.out.println(item1.toJSONPretty());
		counter++;
		while (counter != largerIndex) {
			iter.next();
			counter++;
		}
		Item item2 = iter.next();
		System.out.println(item2.toJSONPretty());
		Object[] lystItems = new Object[3];

		try{
		String name = item1.getString("ItemName");
		String picPath = item1.getString("PicPath");
		String belongingList = item1.getString("BelongingList");
		int listId = item1.getInt("ListID");
		int itemId = item1.getInt("ItemID");
		int overall = item1.getInt("Overall");
		LystItem firstItem = new LystItem(name, belongingList, picPath, overall, listId, itemId);
		name = item2.getString("ItemName");
		picPath = item2.getString("PicPath");
		itemId = item2.getInt("ItemID");
		overall = item2.getInt("Overall");
		LystItem secondItem = new LystItem(name, belongingList, picPath, overall, listId, itemId);
		lystItems[0] = listToPullFrom;
		lystItems[1] = firstItem;
		lystItems[2] = secondItem;
		}
		catch(Exception e){
			
		}
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

	@SuppressWarnings("unchecked")
	private BatchGetItemOutcome getRandomListsFromDatabase(String category, int numberOfLists) {

		System.out.println("Okman here we are again. The category is" +category);
		System.out.println("We gone enter the categories ids array, printing now:");
		for(int i=0; i<CategoryListIDs.size(); i++){
			System.out.println(CategoryListIDs.get(i));
		}
		ArrayList<Integer> temp = CategoryListIDs.get(category);
		ArrayList<Integer> listArray = (ArrayList<Integer>) temp.clone();
		int largest = listArray.size();
		Object[] idsToGet = new Object[numberOfLists];
		int indexToGet;
		for (int i = 0; i < numberOfLists; i++) {
			indexToGet = random.nextInt(largest);

			idsToGet[i] = listArray.get(indexToGet);
			listArray.remove((int) indexToGet);
			largest--;
		}
		TableKeysAndAttributes forumTableKeysAndAttributes = new TableKeysAndAttributes("Lists");
		// Add a partition key
		forumTableKeysAndAttributes.addHashOnlyPrimaryKeys("Id", idsToGet);
		BatchGetItemOutcome outcome = dynamoDB.batchGetItem(forumTableKeysAndAttributes);
		return outcome;
	}

	public ArrayList<Integer> getRandomListNumbers(String category, ArrayList<Integer> delivered, int n_lists) {

		ArrayList<Integer> allPossible = (ArrayList<Integer>) CategoryListIDs.get(category).clone();
		Random rando = new Random();
		allPossible.removeAll(delivered);
		int possible_values = allPossible.size();
		ArrayList<Integer> new_lists = new ArrayList<Integer>();
		int newint;
		int newrando;
		try {
			for (int i = 0; i < n_lists; i++) {
				newrando = rando.nextInt(possible_values);
				newint = allPossible.get(newrando);
				allPossible.remove((int) newrando);
				new_lists.add(newint);
				possible_values--;
				if (possible_values == 0) {
					if (i == (n_lists - 1)) {
						new_lists.add(0);
					}
					break;
				}
			}
		} catch (Exception e) { // methinks think this is unused
			e.printStackTrace();
		}
		return new_lists;
	}

	public List<Item> getListIDsFromCategoryTable(Integer[] ids_to_get) {
		TableKeysAndAttributes KandA = new TableKeysAndAttributes("Lists");
		KandA.addHashOnlyPrimaryKeys("Id", ids_to_get);

		BatchGetItemOutcome result = dynamoDB.batchGetItem(KandA);
		List<Item> items = result.getTableItems().get("Lists");

		return items;
	}

	public Object[] getAttributesAndStringPackages(int listId) {
		Table lists = dynamoDB.getTable("Lists");
		Item list = lists.getItem("Id", listId);
		ArrayList<String> attributeList = (ArrayList<String>) list.get("Attributes");
		ArrayList<Integer> stringList = (ArrayList<Integer>) list.get("StringPackage");
		return new Object[] { attributeList, stringList };
	}

	public ArrayList<Attribute> getItemAttributes(LystItem item) {

		ArrayList<Attribute> returnAttributes = new ArrayList<Attribute>();
		Table table = dynamoDB.getTable("Attributes");
		
		
		QuerySpec spec = new QuerySpec().withKeyConditionExpression("ItemID = :v_id and begins_with(ListAttribute, :v_listId)")
				.withValueMap(new ValueMap()
						.withNumber(":v_id", item.getItemId())
						.withString(":v_listId", item.getListId()+"-"));

		ItemCollection<QueryOutcome> attributes = table.query(spec);

		Iterator<Item> iterator = attributes.iterator();
		Item attribute = null;
		while (iterator.hasNext()) {
			attribute = iterator.next();
			String name = attribute.getString("AttributeName");
			int ranking = attribute.getBigInteger("Ranking").intValue();
			int rating = attribute.getBigInteger("Rating").intValue();
			double average = attribute.getDouble("AverageScore");
			BigInteger wins = attribute.getBigInteger("Wins");
			BigInteger entries = attribute.getBigInteger("Entries");
			BigInteger points  = attribute.getBigInteger("Points");
			String listIdString = attribute.getString("ListAttribute").split("-")[0];
			String attributeNumberString = attribute.getString("ListAttribute").split("-")[1];
			int listId = Integer.parseInt(listIdString);
			int attributeNumber = Integer.parseInt(attributeNumberString);
			Attribute att = new Attribute(item.getItemId(), name,listId,attributeNumber,ranking,rating,wins,entries,points, average);
			returnAttributes.add(att);
		}
		
		  Attribute temp;
	        for(int i=0; i < returnAttributes.size()-1; i++){
	 
	            for(int j=1; j < returnAttributes.size()-i; j++){
	                if(returnAttributes.get(j-1).getAttributeNumber() > returnAttributes.get(j).getAttributeNumber()){
	                    temp=returnAttributes.get(j-1);
	                    returnAttributes.add(j-1, returnAttributes.get(j));
	                    returnAttributes.add(j,temp);
	                }
	            }
	        }
		return returnAttributes;
	}

	@SuppressWarnings("unused")
//	public static void main (String [] args ) {
//	 DatabaseAccessor db = new DatabaseAccessor(true);
//	 ArrayList<Map<String,Object>> thing =  db.getRankedIDs(0, 0, 1, 5);
//	 ArrayList<Map<String, Object>> returnSauce = db.getAttributeItem(10, 0);
//	 System.out.println(returnSauce.toString());
	 //db.getRankedListItems(11,0, 3, 6);
	// System.out.println(db.random);
	// System.out.println(db.dynamodb);
	// System.out.println(DatabaseAccessor.dynamoDB);
	// }
	//Puts the modified attributes passed in back into the DB
	public void UpdateAttributes(ArrayList<Attribute> currentAttributes) {
		
		ArrayList<Attribute> attributes = new ArrayList(currentAttributes);
		try{
		ArrayList<Item> attributesToPut = new ArrayList<Item>();
		int threshold = 25;
		while(!attributes.isEmpty()){
			if(attributes.size()<threshold){
				threshold = attributes.size();
			}
			for(int i = 0; i< threshold; i++){
			Attribute currentAttr = attributes.get(i);
			String listAttribute = currentAttr.getListId()+"-"+currentAttr.getAttributeNumber();
			Item item = new Item().withPrimaryKey("ItemID", currentAttr.getItemId(), "ListAttribute", listAttribute)
					.withNumber("Rating",currentAttr.getRating() ).withNumber("Ranking", currentAttr.getRanking()).
					withNumber("Entries", currentAttr.getEntries()).withNumber("Points", currentAttr.getPoints())
					.withNumber("Wins", currentAttr.getWins()).withString("AttributeName", currentAttr.getName()).withDouble("AverageScore", currentAttr.getAverage());
			attributesToPut.add(item);
			}
			
		TableWriteItems tableWrite = new TableWriteItems("Attributes").withItemsToPut(attributesToPut);
		BatchWriteItemOutcome outcome = dynamoDB.batchWriteItem(tableWrite);
		
		do {

            // Check for unprocessed keys which could happen if you exceed provisioned throughput

            Map<String, List<WriteRequest>> unprocessedItems = outcome.getUnprocessedItems();

            if (outcome.getUnprocessedItems().size() == 0) {
                System.out.println("No unprocessed items found");
               for (int i=0; i<threshold;i++){
            	   System.out.println("Inserted " + attributes.get(0).getItemId());
            	   attributes.remove(0);
               }
            } else {
                System.out.println("Retrieving the unprocessed items");
                outcome = dynamoDB.batchWriteItemUnprocessed(unprocessedItems);
            }

        } while (outcome.getUnprocessedItems().size() > 0);
		attributesToPut.clear();
		}
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}
	
	public ArrayList<Lyst> getAllLists(){
		ArrayList<Lyst> lysts = new ArrayList<Lyst>();
		
		ScanRequest scanRequest = new ScanRequest()
			    .withTableName("Lists");

			ScanResult result = client.scan(scanRequest);
			for (Map<String, AttributeValue> item : result.getItems()){
				String name = (String) item.get("ListName").getS();
				int index = Integer.parseInt(item.get("Id").getN());
				List<AttributeValue> attributesA = item.get("Attributes").getL();
				ArrayList<String> attributes = new ArrayList<String>();
				for (int i=0; i< attributesA.size(); i++){
					attributes.add(attributesA.get(i).getS());
				}
				int size = Integer.parseInt(item.get("ListSize").getN());
				String picPath = item.get("PicPath").getS();
				Lyst lyst = new Lyst(name, index, attributes, size, picPath);
				lysts.add(lyst);
			}
			return lysts;
	}

	//Gets all attributes for a specific list, like all Overalls for the Villains list
	public ArrayList<Attribute> getAttributes(int listNumber, int attributeNumber) {
		String listAttribute = listNumber+"-"+attributeNumber;
		Table listItems = dynamoDB.getTable("Attributes");
		Index index = listItems.getIndex("ListAttribute-index");
		QuerySpec spec = new QuerySpec().withKeyConditionExpression("ListAttribute = :v_listAttribute")
				.withValueMap(new ValueMap().withString(":v_listAttribute", listAttribute));

		ItemCollection<QueryOutcome> items = index.query(spec);
		Iterator<Item> iter = items.iterator();
		
		ArrayList<Attribute> returnAttributes = new ArrayList<Attribute>();
		while(iter.hasNext()){
			Item attribute = iter.next();
			String name = attribute.getString("AttributeName");
			int itemId = attribute.getInt("ItemID");
			int ranking = attribute.getBigInteger("Ranking").intValue();
			int rating = attribute.getBigInteger("Rating").intValue();
			double average = attribute.getDouble("AverageScore");
			BigInteger wins = attribute.getBigInteger("Wins");
			BigInteger entries = attribute.getBigInteger("Entries");
			BigInteger points  = attribute.getBigInteger("Points");
			Attribute att = new Attribute(itemId, name,listNumber,attributeNumber,ranking,rating,wins,entries,points, average);
			returnAttributes.add(att);
		}
		return returnAttributes;
	}

	public void updateOverallRatings(ArrayList<Attribute> currentAttributes, String listName) {
		Table listItems = dynamoDB.getTable("ListItems");
		ArrayList<LystItem> lystItems = new ArrayList<LystItem>();
		
		Index index = listItems.getIndex("BelongingList-index");
		QuerySpec spec = new QuerySpec().withKeyConditionExpression("BelongingList = :v_belong")
				.withValueMap(new ValueMap().withString(":v_belong", listName));

		ItemCollection<QueryOutcome> items = index.query(spec);
		Iterator<Item> iter = items.iterator();
		
		while(iter.hasNext()){
			Item item = iter.next();
			String name = item.getString("ItemName");
			String picPath = item.getString("PicPath");
			String belongingList = item.getString("BelongingList");
			int listId = item.getInt("ListID");
			int itemId = item.getInt("ItemID");
			int overall = item.getInt("Overall");
			LystItem lystItem = new LystItem(name, belongingList, picPath, overall, listId, itemId);
			lystItems.add(lystItem);
		}
		
		for(int i=0; i< lystItems.size(); i++){
			for(int j=0; j< currentAttributes.size(); j++){
				if(lystItems.get(i).itemId == currentAttributes.get(j).getItemId()){
					lystItems.get(i).overallRating = currentAttributes.get(j).rating;
					break;
				}
			}
		}
		updateListItems(lystItems);
	}
	
	public void updateListItems(ArrayList<LystItem> lystItems){
		try{
			ArrayList<Item> lystItemsToPut = new ArrayList<Item>();
			int threshold = 25;
			while(!lystItems.isEmpty()){
				if(lystItems.size()<threshold){
					threshold = lystItems.size();
				}
				for(int i = 0; i< threshold; i++){
				LystItem currentLystItem = lystItems.get(i);
				Item item = new Item().withPrimaryKey("ItemName", currentLystItem.name, "BelongingList", currentLystItem.belongingList)
						.withNumber("ItemID",currentLystItem.itemId ).withNumber("ListID", currentLystItem.listId).
						withNumber("Overall", currentLystItem.overallRating).withString("PicPath", currentLystItem.picPath);
				lystItemsToPut.add(item);
				}
				
			TableWriteItems tableWrite = new TableWriteItems("ListItems").withItemsToPut(lystItemsToPut);
			BatchWriteItemOutcome outcome = dynamoDB.batchWriteItem(tableWrite);
			
			do {

	            // Check for unprocessed keys which could happen if you exceed provisioned throughput

	            Map<String, List<WriteRequest>> unprocessedItems = outcome.getUnprocessedItems();

	            if (outcome.getUnprocessedItems().size() == 0) {
	                System.out.println("No unprocessed items found");
	               for (int i=0; i<threshold;i++){
	            	   System.out.println("Inserted " + lystItems.get(0).getItemId());
	            	   lystItems.remove(0);
	               }
	            } else {
	                System.out.println("Retrieving the unprocessed items");
	                outcome = dynamoDB.batchWriteItemUnprocessed(unprocessedItems);
	            }

	        } while (outcome.getUnprocessedItems().size() > 0);
			lystItemsToPut.clear();
			}
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
	}

	public LystItem getListItem(String belongingList, String itemName) {
		// TODO Auto-generated method stub
		Table table = dynamoDB.getTable("ListItems");
		Item item = table.getItem("ItemName", itemName,"BelongingList",belongingList);
		String picPath = item.getString("PicPath");
		int listId = item.getInt("ListID");
		int itemId = item.getInt("ItemID");
		int overall = item.getInt("Overall");
		LystItem lystItem = new LystItem(itemName, belongingList, picPath, overall, listId, itemId);
		return lystItem;
		
	}
	
	public int getListId (String listName){
		Table lists = dynamoDB.getTable("Lists");
		Index index = lists.getIndex("ListName-index");
		QuerySpec spec = new QuerySpec().withKeyConditionExpression("ListName = :v_name")
				.withValueMap(new ValueMap().withString(":v_name", listName));
		ItemCollection<QueryOutcome> items = index.query(spec);
		Iterator<Item> iter = items.iterator();
		Item theList = iter.next();
		int id = theList.getInt("Id");
		return id;
	}

}
