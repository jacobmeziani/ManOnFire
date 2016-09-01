package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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
import Data.RatingsProcessor;
import Data.Attribute;
import Database.DatabaseAccessor;
import Display.ResultAttributes;

/**
 * Servlet implementation class Authentication
 */
@WebServlet("/lystservlet")
public class LystServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String categoryHtml = "";

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
		String requestAction = request.getParameter("action");
		System.out.println(requestAction);
		HttpSession session = request.getSession();
		DatabaseAccessor d = new DatabaseAccessor();
		Cookie[] freshBatch = request.getCookies();

		if (requestAction != null) {
			if (requestAction.equals("newRandom") || requestAction.equals("initial")) {

				Boolean list = false;
				String currentCategory = "Everything";
				Cookie currentCategoryCookie = getCookie(freshBatch, "currentCategory");
				if(currentCategoryCookie != null){
					currentCategory = currentCategoryCookie.getValue();
					Cookie isListCookie = getCookie(freshBatch, "isList");
					if(isListCookie != null){
					String listBool = getCookie(freshBatch, "isList").getValue();
					if (listBool.equals("true")) {
						list = true;
					} else {
						list = false;
					}
					}
				}
				if (requestAction.equals("newRandom")) {
					currentCategory = request.getParameter("currentCategory");
					String listString = request.getParameter("isList");
					if (listString.equals("true")) {
						list = true;
					}
				} 
				Object[] items = d.getNextCombatants(currentCategory, list);
				Cookie storeListIdCookie = new Cookie("listId", Integer.valueOf(((Lyst)items[0]).getListIndex()).toString());
				request.setAttribute("currentList", items[0]);
				request.setAttribute("leftItem", items[1]);
				request.setAttribute("rightItem", items[2]);

				if (categoryHtml == "") {
					categoryHtml = d.getMenu();
				}
				request.setAttribute("CategoryHTML", categoryHtml);

				String categoryName = currentCategory;
				if (list) {
					categoryName = ((Lyst) request.getAttribute("currentList")).getListName();
				}
				request.setAttribute("categoryName", categoryName);
				request.setAttribute("currentCategory", currentCategory);
				request.setAttribute("isList", list);
				Cookie storeCatNameCookie = new Cookie("categoryName", categoryName);
				Cookie storeCategoryCookie = new Cookie("currentCategory", currentCategory);
				Cookie storeIsListCookie = new Cookie("isList", list.toString());
				response.addCookie(storeListIdCookie);
				response.addCookie(storeCatNameCookie);
				response.addCookie(storeCategoryCookie);
				response.addCookie(storeIsListCookie);
				if (requestAction.equals("initial")) {
					request.getRequestDispatcher("/home.jsp").forward(request, response);
				}else {
					request.getRequestDispatcher("/newmatchup.jsp").forward(request, response);
				}

			} else if (requestAction.equals("vs")) {
				Cookie listIdCookie = getCookie(freshBatch,"listId");
				String leftItemName = request.getParameter("leftItemName");
				String rightItemName =  request.getParameter("rightItemName");
				String listName = request.getParameter("listName");
				Cookie listCookie = new Cookie("listName", listName);
				response.addCookie(listCookie);
				
				LystItem leftItem = d.getListItem(listName, leftItemName);
				LystItem rightItem = d.getListItem(listName, rightItemName);
				// return attributes, from DB to load up the js
				Object[] data = d.getAttributesAndStringPackages(Integer.parseInt(listIdCookie.getValue()));
				ArrayList<String> attributeNames = (ArrayList<String>) data[0];
				ArrayList<ResultAttributes> attributes = new ArrayList<ResultAttributes>();

				// add overall because it is not included in the rating game
				// part
				for (int i = 0; i < attributeNames.size(); i++) {
					ResultAttributes attr = new ResultAttributes();
					attr.setName(attributeNames.get(i));
					attributes.add(attr);
				}
				request.setAttribute("attributes", attributes);
				ArrayList<Integer> stringPackages = (ArrayList<Integer>) data[1];
				Object[] leftItemProps = new Object[]{leftItem.name, leftItem.belongingList,
						leftItem.picPath,leftItem.overallRating, leftItem.listId, leftItem.itemId};
				Object[] rightItemProps = new Object[]{rightItem.name, rightItem.belongingList,
						rightItem.picPath,rightItem.overallRating, rightItem.listId, rightItem.itemId};
				JSONObject json = new JSONObject();
				try {
					json.put("attributes", attributes);
					json.put("stringPackages", stringPackages);
					json.put("leftItemProps", leftItemProps);
					json.put("rightItemProps", rightItemProps);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				PrintWriter out = response.getWriter();
				out.print(json.toString());

			} else if (requestAction.equals("sliderDisplay")) {
				request.getRequestDispatcher("/slider.jsp").forward(request, response);
			} else if (requestAction.equals("results")) {
				Cookie listNameCookie = getCookie(freshBatch,"listName");
				String[] leftItemProps = request.getParameterValues("leftItemProps[]");
				String[] rightItemProps = request.getParameterValues("rightItemProps[]");
				LystItem leftItem = new LystItem(leftItemProps[0],leftItemProps[1],leftItemProps[2],
						Integer.parseInt(leftItemProps[3]),Integer.parseInt(leftItemProps[4]),
						Integer.parseInt(leftItemProps[5]));
				LystItem rightItem = new LystItem(rightItemProps[0],rightItemProps[1],rightItemProps[2],
						Integer.parseInt(rightItemProps[3]),Integer.parseInt(rightItemProps[4]),
						Integer.parseInt(rightItemProps[5]));
				Cookie listIdCookie = getCookie(freshBatch,"listId");
				if(listNameCookie != null){
				String[] requestScores = request.getParameterValues("scores[]");
				int[] scores = new int[requestScores.length];

				// Convert string items to ints and also compute the winner
				int winCounter = 0;
				for (int i = 0; i < requestScores.length; i++) {
					int currentScore = Integer.parseInt(requestScores[i]);
					winCounter += (currentScore - 5);
					scores[i] = currentScore;
				}
				if (winCounter < 0) {
					// Left item wins
					request.setAttribute("winningSide", "left");
				} else if (winCounter > 0) {
					// Right item wins
					request.setAttribute("winningSide", "right");
				} else {
					// Tie
					request.setAttribute("winningSide", "tie");
				}

				request.setAttribute("leftItem", leftItem);
				request.setAttribute("rightItem", rightItem);
				
				
				ArrayList<Attribute> leftWorldAttributes = d.getItemAttributes(leftItem);
				ArrayList<Attribute> rightWorldAttributes = d.getItemAttributes(rightItem);
				Object[] data = d.getAttributesAndStringPackages(Integer.parseInt(listIdCookie.getValue()));
				ArrayList<String> attributeNames = (ArrayList<String>) data[0];
				ArrayList<ResultAttributes> attributes = new ArrayList<ResultAttributes>();

				// add overall because it is not included in the rating game
				// part
				for (int i = 0; i < attributeNames.size(); i++) {
					ResultAttributes attr = new ResultAttributes();
					attr.setName(attributeNames.get(i));
					attributes.add(attr);
				}

				// Add overall to the attributes and scores
				ResultAttributes attr = new ResultAttributes();
				attr.setName("Overall");
				attributes.add(0, attr);
				int[] resultScores = new int[scores.length + 1];
				resultScores[0] = winCounter;
				for (int i = 0; i < scores.length; i++) {
					resultScores[i + 1] = scores[i];
				}

				// figure this bs out
				for (int i = 0; i < attributes.size(); i++) {
					attributes.get(i).setLeftItemWorldScore(leftWorldAttributes.get(i).rating);
					attributes.get(i).setRightItemWorldScore(rightWorldAttributes.get(i).rating);

					// Normalize the overall rating
					if (i == 0) {
						if (winCounter >= 10) {
							resultScores[i] = 10;
						} else if (winCounter >= 8) {
							resultScores[i] = 9;
						} else if (winCounter >= 6) {
							resultScores[i] = 8;
						} else if (winCounter >= 4) {
							resultScores[i] = 7;
						} else if (winCounter >= 1) {
							resultScores[i] = 6;
						} else if (winCounter >= 0) {
							resultScores[i] = 5;
						} else if (winCounter >= -2) {
							resultScores[i] = 4;
						} else if (winCounter >= -4) {
							resultScores[i] = 3;
						} else if (winCounter >= -6) {
							resultScores[i] = 2;
						} else if (winCounter >= -8) {
							resultScores[i] = 1;
						} else {
							resultScores[i] = 0;
						}

					}
					int userScore = resultScores[i] - 5;
					if (userScore < 0) {
						attributes.get(i).setLeftItemUserScore(-userScore);
						attributes.get(i).setRightItemUserScore(0);
					} else {
						attributes.get(i).setRightItemUserScore(userScore);
						attributes.get(i).setLeftItemUserScore(0);
					}
				}

				request.setAttribute("attributes", attributes);
				request.getRequestDispatcher("/results.jsp").forward(request, response);
				String currentList = listNameCookie.getValue();
				RatingsProcessor p = new RatingsProcessor(currentList, resultScores, leftWorldAttributes,
						rightWorldAttributes);
				p.start();
				}
			}
		}
		// look at url if no request action, used to view lists and list items
		else {
			StringBuffer stringBuffer = request.getRequestURL();
			String url = stringBuffer.toString();
			String[] split = url.split("/");
			String list = "";
			String listItem = "";
			for (int i = 0; i < split.length; i++) {
				if (split[i].equals("bro")) {
					if (i + 1 < split.length) {
						list = split[i + 1];
					}
					if (i + 2 < split.length) {
						listItem = split[i + 2];
					}
				}
			}
			if (!listItem.equals("")) {
				// navigate to the item page
				String[] splitItem = listItem.split("_");
				String itemName = splitItem[0];
				for (int i = 1; i < splitItem.length; i++) {
					itemName += " " + splitItem[i];
				}
				String[] splitList = list.split("_");
				String listName = splitList[0];
				for (int i = 1; i < splitList.length; i++) {
					listName += " " + splitList[i];
				}
				LystItem lystItem = d.getListItem(listName, itemName);
				ArrayList<Attribute> itemAttributes = d.getItemAttributes(lystItem);
				request.setAttribute("itemAttributes", itemAttributes);
				request.setAttribute("currentItem", lystItem);
				System.out.println("Oh hell yea");
				request.getRequestDispatcher("/listitem.jsp").forward(request, response);
			} else {
				// navigate to the list page
			}
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

	// public String getParameter(HttpServletRequest req) {
	// Enumeration<String> a = req.getParameterNames();
	// while (a.hasMoreElements()) {
	// System.out.println(a.nextElement());
	// }
	// return "";
	// }

	public Cookie getCookie(Cookie[] batch, String cookieName) {
		for (int i = 0; i < batch.length; i++) {
			if (batch[i].getName().equals(cookieName)) {
				return batch[i];
			}
		}
		return null;
	}

}
