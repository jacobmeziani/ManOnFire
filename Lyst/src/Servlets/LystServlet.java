package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;

import Data.CategoryDB;
import Data.HTMLCategory;
import Data.Lyst;
import Data.LystItem;
import Database.DatabaseAccessor;
import Html.HtmlWriter;

/**
 * Servlet implementation class Authentication
 */
@WebServlet("/lystservlet")
public class LystServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LystServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestAction = getParameter(request);
		System.out.println(requestAction);
		HttpSession session = request.getSession();
		DatabaseAccessor d = new DatabaseAccessor();

		if (requestAction.equals("") || requestAction.equals("initial")) 
		{
			String currentCategory = (String) session.getAttribute("CurrentCategory");
			if (currentCategory == null || currentCategory.isEmpty()) {
				currentCategory = "Foreign Sports";
				session.setAttribute("CurrentCategory", "Everything");
			}
			currentCategory = "9";
			Object[] items = d.getNextCombatants(currentCategory,true);
			session.setAttribute("currentList", items[0]);
			session.setAttribute("leftItem", items[1]);
			session.setAttribute("rightItem", items[2]);
			// session.setAttribute("CurrentCategory",
			// ((String)request.getParameter("CurrentCategory")));
			//session.setAttribute("CategoryHTML", null);
			String testing_categories = (String) session.getAttribute("CategoryHTML");

			if (testing_categories == null) {
				String category_html = d.getMenu();
				session.setAttribute("CategoryHTML", category_html);
			}

			if (requestAction.equals("initial")) 
			{
				request.getRequestDispatcher("/home.jsp").forward(request, response);
			} 
			else 
			{
				request.getRequestDispatcher("/newmatchup.jsp").forward(request, response);
			}
			
		}
		else if (requestAction.equals("vs")) 
		{
			LystItem leftItem = (LystItem)session.getAttribute("leftItem");
			LystItem rightItem = (LystItem)session.getAttribute("rightItem");
			Lyst currentList = (Lyst)session.getAttribute("currentList");
			//return attributes, from DB to load up the js
			Object[] data = d.getAttributesAndStringPackages(currentList);
			ArrayList<String> attributes = (ArrayList<String>)data[0];
			ArrayList<Integer> stringPackages = (ArrayList<Integer>)data[1];
			JSONObject json = new JSONObject();
			try {
				json.put("attributes", attributes);
				json.put("stringPackages", stringPackages);
				json.put("leftItem", leftItem.getName());
				json.put("rightItem", rightItem.getName());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PrintWriter out = response.getWriter();
			out.print(json.toString());
			
		}
		else if (requestAction.equals("vsDisplay")) 
		{
			request.getRequestDispatcher("/slider.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("unused")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("shit");
		DatabaseAccessor db = new DatabaseAccessor();
		String name = request.getParameter("firstName") + " " + request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		String sauce = null;
		try {

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getParameter(HttpServletRequest req) {
		Enumeration<String> a = req.getParameterNames();
		if (a.hasMoreElements()) {
			return a.nextElement();
		}
		return "";
	}

}
