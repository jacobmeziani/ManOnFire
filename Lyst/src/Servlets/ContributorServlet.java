package Servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONObject;
//import com.amazonaws.util.json.
import importing.FullReader;

import Data.Contributor;

/**
 * Servlet implementation class ContributorServlet
 */
public class ContributorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContributorServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = (String) request.getParameter("login");
		
		if (action.equals("login")) {
			String loginid = (String) request.getParameter("loginid");
			String personname = Contributor.getContributor(loginid);
			if (personname==null) {
				response.getWriter().append("<h1>Invalid Credentials</h1>");
				return;
			}
			HttpSession session = request.getSession();
			
			Cookie logincookie = new Cookie("loginid",loginid);
			
			session.setAttribute("loginid", loginid);
			session.setAttribute("username", personname);
			
			response.addCookie(logincookie);
			request.getRequestDispatcher("/contributor_home.jsp").forward(request, response);
			
			//forward the page and then return
			return;
		}
		
		System.out.println("bruh");
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String type = request.getParameter("input");
		System.out.println(type);
		
		try {
			JSONObject jsonObject = new JSONObject(type);
			String user = jsonObject.getString("user");
			JSONObject attributes = jsonObject.getJSONObject("attributes");
			System.out.println(attributes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
