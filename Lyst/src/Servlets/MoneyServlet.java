package Servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Database.DatabaseAccessor;
import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;

/**
 * Servlet implementation class MoneyServlet
 */
public class MoneyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MoneyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String action = request.getParameter("action");
		if (action == null) {
			//this is the returno of the site itself ya dig
			//put in some shiznit to fuck with URL doe 
			return;
		} else {
			serveLists(action, request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	protected void serveLists(String action, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (action.equals("initialLoad")) {
			
			response.setContentType("application/json");
			//first step -- getting the attributes for that particular list
			String templistid = request.getParameter("ListID");
			int listid = Integer.parseInt(templistid);
			
			String tempattributenumber = request.getParameter("Attribute");
			int attributenumber = Integer.parseInt(tempattributenumber);
			
			String tempnextranking = request.getParameter("NextRanking");
			int nextranking = Integer.parseInt(tempnextranking);
			
			int lastrankdelivered = 0;
			int smallestRankNeeded = lastrankdelivered + 1;
			DatabaseAccessor db = new DatabaseAccessor();
			List<String> attributes = db.getAttributes(listid);
			
			ArrayList<Map<String, Object>> items = db.getRankedIDs(listid, attributenumber, smallestRankNeeded, nextranking);
			
			int lengthReturned = items.size();
			JSONObject[] tempjsonitems = new JSONObject[lengthReturned];
//			for (int ii = 0; ii < lengthReturned; ii++) {
//				tempjsonitems.add(null);  //otherwise it breaks elsewhere
//			}
//			
			//check for completed return. this is used by client side to stop fetching more results
			int testLength = nextranking - lastrankdelivered;
			System.out.print("test length: ");
			System.out.println(testLength);
			System.out.println(lengthReturned);
			boolean is_final;
			if (testLength <= lengthReturned) {
				//soooo this can break quite easily if the rankings change while hte list is being fetched.
				//so i put in the greater than or equal to to basically cut our chances of it breaking in half
				is_final = false;
			}
			else {
				is_final = true;
			}
			
			response.addIntHeader("isFinal", (is_final?1:0));
			
			
			Map<String, Object> tempItemMap;
			JSONArray itemAttributes = null;

			
			for (int i = 0; i < lengthReturned; i++) {
				tempItemMap = items.get(i);
				JSONObject tempitem = new JSONObject();
				int rankingAttributeWanted = (Integer) tempItemMap.get("Ranking");
				int itemID = (Integer) tempItemMap.get("ItemID");
				rankingAttributeWanted = rankingAttributeWanted - smallestRankNeeded;  //finding out where to put it inside array
				
				//String itemname = db.getItemName(itemID);  //TODO: must return picpath as well 
				String picpath = "swaggyp";
				ArrayList<Map<String, Object>> attributeList = db.getAttributeItem(itemID, listid);
				
				itemAttributes = new JSONArray();
				int num_attributes = attributeList.size();
				Map<String, Object> tempAttMap;
				
				for (int j = 0; j < num_attributes; j++) {
					tempAttMap = attributeList.get(j);
					int attributeNumber = (Integer)tempAttMap.get("AttributeNumber");
					int ranking = (Integer)tempAttMap.get("Ranking");
					int rating = (Integer)tempAttMap.get("Rating");
					if (attributeNumber != j)  {
						System.out.println("Something might be wrongo");
					}
					
					itemAttributes.put(rating);
				}
				//now to build the temp item to later add to array
				
				try {
					tempitem.put("ItemName", "");
					tempitem.put("ItemID", itemID);
					tempitem.put("PicPath", picpath);
					tempitem.put("Ratings", itemAttributes);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				tempjsonitems = fixrankingbullshit(rankingAttributeWanted, tempjsonitems, tempitem);
				
				//tempjsonitems[rankingAttributeWanted] = tempitem;
			}
			
			JSONArray JSONItems = null;
			try {
				JSONItems = new JSONArray(tempjsonitems);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			JSONObject jsonObject = new JSONObject();
			JSONArray jsonAttributes = new JSONArray(attributes);
			
			try {
				jsonObject.put("Items", JSONItems);
				jsonObject.put("Attributes", jsonAttributes);
			} catch (JSONException e) {
				System.out.println("Something wrongo wit de json");
				e.printStackTrace();
			}
			
			response.getWriter().write(jsonObject.toString());
			
		} else if (action.equals("load")) {
			
			response.setContentType("application/json");
			//first step -- getting the attributes for that particular list
			String templistid = request.getParameter("ListID");
			int listid = Integer.parseInt(templistid);
			
			String tempattributenumber = request.getParameter("Attribute");
			int attributenumber = Integer.parseInt(tempattributenumber);
			
			String tempnextranking = request.getParameter("NextRanking");
			int nextranking = Integer.parseInt(tempnextranking);
			
			String templastrank = request.getParameter("LastRankDelivered");
			int lastrankdelivered = Integer.parseInt(templastrank);
			
			int smallestRankNeeded = lastrankdelivered + 1;
			DatabaseAccessor db = new DatabaseAccessor();
			List<String> attributes = db.getAttributes(listid);
			
			ArrayList<Map<String, Object>> items = db.getRankedIDs(listid, attributenumber, smallestRankNeeded, nextranking);
			
			int lengthReturned = items.size();
			JSONObject[] tempjsonitems = new JSONObject[lengthReturned];
//			for (int ii = 0; ii < lengthReturned; ii++) {
//				tempjsonitems.add(null);  //otherwise it breaks elsewhere
//			}
//			
			//check for completed return. this is used by client side to stop fetching more results
			int testLength = nextranking - lastrankdelivered;
			System.out.print("test length: ");
			System.out.println(testLength);
			System.out.println(lengthReturned);
			boolean is_final;
			if (testLength <= lengthReturned) {
				//soooo this can break quite easily if the rankings change while hte list is being fetched.
				//so i put in the greater than or equal to to basically cut our chances of it breaking in half
				is_final = false;
			}
			else {
				is_final = true;
			}
			
			response.addIntHeader("isFinal", (is_final?1:0));
			
			
			Map<String, Object> tempItemMap;
			JSONArray itemAttributes = null;

			
			for (int i = 0; i < lengthReturned; i++) {
				tempItemMap = items.get(i);
				JSONObject tempitem = new JSONObject();
				int rankingAttributeWanted = (Integer) tempItemMap.get("Ranking");
				int itemID = (Integer) tempItemMap.get("ItemID");
				rankingAttributeWanted = rankingAttributeWanted - smallestRankNeeded;  //finding out where to put it inside array
				
				//String itemname = db.getItemName(itemID);  //TODO: must return picpath as well 
				String picpath = "swaggyp";
				ArrayList<Map<String, Object>> attributeList = db.getAttributeItem(itemID, listid);
				
				itemAttributes = new JSONArray();
				int num_attributes = attributeList.size();
				Map<String, Object> tempAttMap;
				
				for (int j = 0; j < num_attributes; j++) {
					tempAttMap = attributeList.get(j);
					int attributeNumber = (Integer)tempAttMap.get("AttributeNumber");
					int ranking = (Integer)tempAttMap.get("Ranking");
					int rating = (Integer)tempAttMap.get("Rating");
					if (attributeNumber != j)  {
						System.out.println("Something might be wrongo");
					}
					
					itemAttributes.put(rating);
				}
				//now to build the temp item to later add to array
				
				try {
					tempitem.put("ItemName", "");
					tempitem.put("ItemID", itemID);
					tempitem.put("PicPath", picpath);
					tempitem.put("Ratings", itemAttributes);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				tempjsonitems = fixrankingbullshit(rankingAttributeWanted, tempjsonitems, tempitem);

				//tempjsonitems[rankingAttributeWanted] = tempitem;
			}
			
			JSONArray JSONItems = null;
			try {
				JSONItems = new JSONArray(tempjsonitems);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			JSONObject jsonObject = new JSONObject();
			JSONArray jsonAttributes = new JSONArray(attributes);
			
			try {
				jsonObject.put("Items", JSONItems);
				jsonObject.put("Attributes", jsonAttributes);
			} catch (JSONException e) {
				System.out.println("Something wrongo wit de json");
				e.printStackTrace();
			}
			
			response.getWriter().write(jsonObject.toString());
		
		}
		
	}
	
	private JSONObject[] fixrankingbullshit(int index, JSONObject[] inputArray, JSONObject item) {
		
		int total = inputArray.length;
		
		JSONObject current = null;
		try {
			current = inputArray[index];
		} catch (Exception e) {
			return fixrankingbullshit(0, inputArray, item);
		}
		if (current == null) {
			inputArray[index] = item;
			return inputArray;
		} else {
			return fixrankingbullshit(index + 1, inputArray, item);
		}
		
	}
	
	
}
