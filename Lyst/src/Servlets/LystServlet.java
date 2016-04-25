package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String initialRequest = getParameters(request);

		HttpSession session = request.getSession();
		String currentCategory = (String)session.getAttribute("CurrentCategory");
		if(currentCategory == null || currentCategory.isEmpty()){
			currentCategory = "Everything";
			session.setAttribute("CurrentCategory", "Everything");
			session.setAttribute("CurrentCategory", currentCategory);
		}		
		DatabaseAccessor d = new DatabaseAccessor();
		LystItem[] items = d.getNextCombatants(currentCategory);
		session.setAttribute("leftItem", items[0]);
		session.setAttribute("rightItem", items[1]);
		//session.setAttribute("CurrentCategory", ((String)request.getParameter("CurrentCategory")));
		session.setAttribute("CategoryHTML", null);
		String testing_categories = (String)session.getAttribute("CategoryHTML");
		
		if (testing_categories==null) {
			
			CategoryDB cdb = new CategoryDB(d);
			HTMLCategory top = HTMLCategory.buildit(cdb);
			String category_html = top.HTMLWriter();
			session.setAttribute("CategoryHTML", category_html);
		}
		if(initialRequest !=null && initialRequest.equals("true")){
			request.getRequestDispatcher("/home.jsp").forward(request, response);
		}
		else
		{
			request.getRequestDispatcher("/newmatchup.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("unused")
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("shit");
		DatabaseAccessor db = new DatabaseAccessor();
		String name = request.getParameter("firstName") + " "
				+ request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		String sauce = null;
		try {

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getParameters(HttpServletRequest req) {
		String a = req.getParameter("isInitial");
		return a;
	}

}
