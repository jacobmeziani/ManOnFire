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
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableKeysAndAttributes;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.TableDescription;

import Data.Lyst;

public class DatabaseAccessor {

	static DynamoDB dynamoDB;
	Random random = new Random();

	public DatabaseAccessor() {
		Region usWest2 = Region.getRegion(Regions.US_WEST_2);
		AmazonDynamoDBClient dbClient = new AmazonDynamoDBClient(
				new ProfileCredentialsProvider("jmeziani"));
		dbClient.setRegion(usWest2);
		dynamoDB = new DynamoDB(dbClient);
	}

	public ArrayList<Lyst> getHomeLists(int numberOfLists) {
		BatchGetItemOutcome outcome = getListsFromDb(numberOfLists);

		ArrayList<Lyst> lysts = new ArrayList<Lyst>();
		for (String tableName : outcome.getTableItems().keySet()) {
			List<Item> items = outcome.getTableItems().get(tableName);
			for (Item item : items) {
				String name = (String) item.get("ListName");
				String categories = (String) item.get("Categories");
				LinkedHashSet<String> hashSet = (LinkedHashSet<String>) item
						.get("Members");
				ArrayList<String> members = new ArrayList<String>();
				if (hashSet != null) {
					members.addAll(hashSet);
				}
				String picPath = (String) item.get("PicPath");
				Lyst lyst = new Lyst(name, categories, members.size(), picPath);
				lysts.add(lyst);
			}
		}

		System.out.println();
		//Real
		return lysts;
		//Fake
//		ArrayList<Lyst> liists = new ArrayList<Lyst>();
//		liists.add(new Lyst("testName","testCat",null,"TestPath"));
//		return liists;
	}

	private BatchGetItemOutcome getListsFromDb(int numberOfLists) {
		Table lists = dynamoDB.getTable("Lists");
		TableDescription description = lists.describe();
		long count = description.getItemCount();
		int randomSeed = (int) count - numberOfLists;
		//REVERT
		//int index = random.nextInt(randomSeed);
		int index = 0;
		Object[] idsToGet = new Object[numberOfLists];
		for (int i = 0; i < numberOfLists; i++) {
			idsToGet[i] = index;
			index++;
		}
		TableKeysAndAttributes forumTableKeysAndAttributes = new TableKeysAndAttributes(
				"Lists");
		// Add a partition key
		forumTableKeysAndAttributes.addHashOnlyPrimaryKeys("Id", idsToGet);
		BatchGetItemOutcome outcome = dynamoDB
				.batchGetItem(forumTableKeysAndAttributes);
		return outcome;
	}
}
