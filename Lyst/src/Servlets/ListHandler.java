package Servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import org.json.simple.JSONObject;
//import org.json.simple.JSONArray;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONObject;

import Database.DatabaseAccessor;

/**
 * Servlet implementation class ListHandler
 */
public class ListHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	private static int n_lists;
	private static HashMap<String,ArrayList<Integer>> listIDs;
	private static DatabaseAccessor db;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		
		n_lists = 2;  // <---- set number of lists to retrieve at every retrieval 
		listIDs = new HashMap<String,ArrayList<Integer>> ();
		db = new DatabaseAccessor() ;
		Table lists = db.getDDB().getTable("Categories");
		ItemCollection<ScanOutcome> collection = lists.scan();
		LinkedHashSet<BigDecimal> tempBigDecimals;
		for (Item item:collection) {
			String name = item.getString("CategoryName");
			tempBigDecimals = (LinkedHashSet<BigDecimal>)item.get("ListIds");
			ArrayList<Integer> ids = new ArrayList<Integer> ();
			try {
				for (BigDecimal bigdecimal : tempBigDecimals) {
					ids.add(bigdecimal.intValue());
				}
				listIDs.put(name, ids);
			} catch (NullPointerException e) {
				listIDs.put(name, null);
			}
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//String dude = (String) request.getParameter("id");
		//System.out.println(dude);
		JSONObject json = new JSONObject();
		JSONArray return_lists = new JSONArray() ;
		JSONObject single_list;
		
		String category = (String) request.getParameter("category");
		String[] deliveredAsStrings = request.getParameterValues("delivered[]");
		ArrayList<Integer> delivered = new ArrayList<Integer>();
		if (deliveredAsStrings!=null) {
		for (String string:deliveredAsStrings) {
			delivered.add(Integer.parseInt(string));
		}}
		ArrayList<Integer> possible = (ArrayList<Integer>) listIDs.get(category).clone();
		ArrayList<Integer> result = null;
		boolean is_final = true;
		try {
			result = getRandomListNumbers(possible,delivered);
			is_final = checkLastReturn(result);
			if (is_final) {
				try{
					result.remove((int) n_lists);
				} catch(Exception e) {}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.addIntHeader("isFinal", (is_final?1:0));
		
		Integer ids_to_get[] = new Integer[result.size()];
		ids_to_get = result.toArray(ids_to_get);
		List<Item> items =  db.getListIDsFromCategoryTable(ids_to_get);
		
		try {
		for (Item item:items) {
			single_list = new JSONObject();
			single_list.put("ListName", item.getString("ListName"));
			single_list.put("PicPath", item.getString("PicPath"));
			single_list.put("Category", "current");//work here
			single_list.put("CurrentLeader", "Lebron James"); //work here too
			single_list.put("ListID", item.getInt("Id"));
			return_lists.put(single_list);
		}
		json.put("lists", return_lists);
		
		} catch (Exception e) {e.printStackTrace();}
		
		response.setContentType("application/json");
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		response.getWriter().write(json.toString());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	/**Returns an array of length <code>n_lists</code> of random list numbers
	 * @param allPossible - all possible list IDs possible 
	 * 
	*/
	private static ArrayList<Integer> getRandomListNumbers(ArrayList<Integer> allPossible, ArrayList<Integer> delivered) throws OutOfListsException {
		
		Random rando = new Random();
		allPossible.removeAll(delivered);
		int possible_values = allPossible.size();
		ArrayList<Integer> new_lists = new ArrayList<Integer> ();
		int newint;
		int newrando;
		try{
			for (int i = 0;i < n_lists;i++) {
				newrando = rando.nextInt(possible_values);
				newint = allPossible.get(newrando);
				allPossible.remove((int)newrando);
				new_lists.add(newint);
				possible_values--;
				if (possible_values == 0) {
					if (i==(n_lists-1)) {	
						new_lists.add(0);
					}
					break;
				}
			}
			return new_lists;
		} catch (Exception e) { //methinks think this is unused
			throw new OutOfListsException();
		}
	}
	
	private static boolean checkLastReturn(ArrayList<Integer> input) {
		if (input.size()==n_lists) {
			return false;
		}
		return true;
	}
	
	private static ArrayList<Integer> parseReturn (String delivered) {
		String [] stringint = delivered.split(",");
		ArrayList<Integer> result = new ArrayList<Integer> ();
		for (String string:stringint) {
			result.add(Integer.parseInt(string));
		}
		return result;
	}
	private static void Tester() {
		n_lists = 5;  // <---- set number of lists to retrieve at every retrieval 
		listIDs = new HashMap<String,ArrayList<Integer>> ();
		db = new DatabaseAccessor() ;
		Table lists = db.getDDB().getTable("Categories");
		ItemCollection<ScanOutcome> collection = lists.scan();
		LinkedHashSet<BigDecimal> tempBigDecimals;
		for (Item item:collection) {
			String name = item.getString("CategoryName");
			System.out.println("putting : "+name);
			tempBigDecimals = (LinkedHashSet<BigDecimal>)item.get("ListIds");
			ArrayList<Integer> ids = new ArrayList<Integer> ();
			try {
				for (BigDecimal bigdecimal : tempBigDecimals) {
					ids.add(bigdecimal.intValue());
				}
				listIDs.put(name,ids);
			} catch (NullPointerException e) {
				System.out.println("in exception");
				listIDs.put(name, null);
			}
		}
		ArrayList<Integer> delivered = new ArrayList<Integer> () ;
		ArrayList<Integer> result; 
		for (int i = 0;i<4;i++) {
			ArrayList<Integer> possible = (ArrayList<Integer>) (listIDs.get("Foreign Sports")).clone();
			System.out.println("getting items for the "+(i+1) + "th time");
			System.out.println("Posible: "+possible);
			System.out.println("Delivered:" + delivered);
			System.out.println("");
			try{
				result = getRandomListNumbers(possible,delivered);
				boolean last = checkLastReturn(result);

				if (checkLastReturn(result)){
					System.out.println("this is the last return");
					try {
						result.remove((int) n_lists);
					} catch(Exception e) {}
				}
				delivered.addAll(result);
				System.out.println("Items returned: " + result);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("printing original array: \n" + listIDs.get("Foreign Sports"));
		System.out.println(delivered);
	}
	
	
	public static void main (String [] args) {
		
	}

}
