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
import javax.servlet.http.HttpSession;

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
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListHandler() {
        super();
        // TODO Auto-generated constructor stub"
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		
		n_lists =6;  // <---- set number of lists to retrieve at every retrieval 
			}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//String dude = (String) request.getParameter("id");
		//System.out.println(dude);
		DatabaseAccessor db = new DatabaseAccessor();
		JSONObject json = new JSONObject();
		JSONArray return_lists = new JSONArray() ;
		JSONObject single_list;
		
		String category = (String) request.getParameter("category");
		String[] deliveredAsStrings = request.getParameterValues("delivered[]");
		ArrayList<Integer> delivered = new ArrayList<Integer>();
		if (deliveredAsStrings!=null) {
			for (String string:deliveredAsStrings) {
			delivered.add(Integer.parseInt(string));
			}
		}
		ArrayList<Integer> result = null;
		boolean is_final = true;
		try {
			result = db.getRandomListNumbers(category,delivered,n_lists);
			is_final = checkLastReturn(result);
			if (is_final) {
				try{
					result.remove((int) n_lists);
				} catch(Exception e) {}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Result: "+ result);
		
		response.addIntHeader("isFinal", (is_final?1:0));
		
		Integer ids_to_get[] = new Integer[result.size()];
		ids_to_get = result.toArray(ids_to_get);
		List<Item> items =  db.getListIDsFromCategoryTable(ids_to_get);
		
		try {
		for (Item item:items) {
			single_list = new JSONObject();
			single_list.put("ListName", item.getString("ListName"));
			single_list.put("PicPath", item.getString("PicPath"));
			single_list.put("Category", "Current");//work here
			single_list.put("CurrentLeader", "Lebron James"); //work here too
			single_list.put("ListID", item.getInt("Id"));
			return_lists.put(single_list);
		}
		
		json.put("lists", return_lists);
		
		} catch (Exception e) {e.printStackTrace();}
		
		response.setContentType("application/json");
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		response.getWriter().write(json.toString());
		HttpSession session = request.getSession();

		String testing_categories = (String) session.getAttribute("CategoryHTML");
		testing_categories = null; //comment out later
		
		if (testing_categories == null) {
			String category_html = db.getMenu();
			session.setAttribute("CategoryHTML", category_html);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	/**Returns an array of length <code>n_lists</code> of random list numbers
	 * @param allPossible - all possible list IDs possible 
	 * 
	*/
	
	public static int getNLists() {return n_lists;}
	
	private static boolean checkLastReturn(ArrayList<Integer> input) {
		if (input.size()==n_lists) {
			return false;
		}
		return true;
	}
	

	
	public static void main (String [] args) {
		
	}

}
