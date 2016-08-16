package Database;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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

import Data.CategoryDB;
import Data.HTMLCategory;
import Data.Lyst;
import Data.LystItem;
import Data.Attribute;
import Servlets.OutOfListsException;

public class DatabaseAccessor {

	private DynamoDB dynamoDB;
	Random random = new Random();
	static AmazonDynamoDBClient client;
	private static String htmlmenu;
	static private HashMap<String, ArrayList<Integer>> CategoryListIDs;

	public DatabaseAccessor() {
		Region usWest2 = Region.getRegion(Regions.US_WEST_2);
		AmazonDynamoDBClient dbClient = new AmazonDynamoDBClient(new ProfileCredentialsProvider("jmeziani"));
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

	public ItemCollection<ScanOutcome> getLists() {
		Table lists = dynamoDB.getTable("Lists");
		return lists.scan();
	}

	public ArrayList<Lyst> getRandomLists(String category, int numberOfLists) { // drama
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

	public ArrayList<Integer> getRandomListNumbers(String category, ArrayList<Integer> delivered, int n_lists)
			throws OutOfListsException {

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
			return new_lists;
		} catch (Exception e) { // methinks think this is unused
			throw new OutOfListsException();
		}
	}

	public List<Item> getListIDsFromCategoryTable(Integer[] ids_to_get) {
		TableKeysAndAttributes KandA = new TableKeysAndAttributes("Lists");
		KandA.addHashOnlyPrimaryKeys("Id", ids_to_get);

		BatchGetItemOutcome result = dynamoDB.batchGetItem(KandA);
		List<Item> items = result.getTableItems().get("Lists");

		return items;
	}

	public Object[] getAttributesAndStringPackages(Lyst currentList) {
		Table lists = dynamoDB.getTable("Lists");
		Item list = lists.getItem("Id", currentList.getListIndex());
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
			BigInteger wins = attribute.getBigInteger("Wins");
			BigInteger entries = attribute.getBigInteger("Entries");
			BigInteger points  = attribute.getBigInteger("Points");
			String attributeNumberString = attribute.getString("ListAttribute").split("-")[1];
			int attributeNumber = Integer.parseInt(attributeNumberString);
			Attribute att = new Attribute(name,attributeNumber,ranking,rating,wins,entries,points);
			returnAttributes.add(att);
		}
		
		Collections.sort(returnAttributes);
		return returnAttributes;
	}

	// public static void main (String [] args ) {
	// DatabaseAccessor db = new DatabaseAccessor();
	// System.out.println(db.random);
	// System.out.println(db.dynamodb);
	// System.out.println(DatabaseAccessor.dynamoDB);
	// }
}
