package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.Enumeration;
//import java.util.List;
//import java.util.Map;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.util.json.JSONArray;
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
	private static String categoryHtml = "";
	private static int n_lists = 6;

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
		if (categoryHtml == "") {
			categoryHtml = d.getMenu();
		}

		if (requestAction != null) {
			if (requestAction.equals("newRandom") || requestAction.equals("initial")) {

				Boolean list = false;
				String currentCategory = "Everything";
				Cookie currentCategoryCookie = getCookie(freshBatch, "currentCategory");
				if (currentCategoryCookie != null) {
					currentCategory = currentCategoryCookie.getValue();
					Cookie isListCookie = getCookie(freshBatch, "isList");
					if (isListCookie != null) {
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
					} else {
						list = false;
					}
				}
				Object[] items = d.getNextCombatants(currentCategory, list);
				Cookie storeListIdCookie = new Cookie("listId",
						Integer.valueOf(((Lyst) items[0]).getListIndex()).toString());
				request.setAttribute("currentList", items[0]);
				request.setAttribute("leftItem", items[1]);
				request.setAttribute("rightItem", items[2]);
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
				} else {
					request.getRequestDispatcher("/newmatchup.jsp").forward(request, response);
				}

			} else if (requestAction.equals("vs")) {
				Cookie listIdCookie = getCookie(freshBatch, "listId");
				String leftItemName = request.getParameter("leftItemName");
				String rightItemName = request.getParameter("rightItemName");
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
				Object[] leftItemProps = new Object[] { leftItem.name, leftItem.belongingList, leftItem.picPath,
						leftItem.overallRating, leftItem.listId, leftItem.itemId };
				Object[] rightItemProps = new Object[] { rightItem.name, rightItem.belongingList, rightItem.picPath,
						rightItem.overallRating, rightItem.listId, rightItem.itemId };
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
				Cookie listNameCookie = getCookie(freshBatch, "listName");
				String[] leftItemProps = request.getParameterValues("leftItemProps[]");
				String[] rightItemProps = request.getParameterValues("rightItemProps[]");
				LystItem leftItem = new LystItem(leftItemProps[0], leftItemProps[1], leftItemProps[2],
						Integer.parseInt(leftItemProps[3]), Integer.parseInt(leftItemProps[4]),
						Integer.parseInt(leftItemProps[5]));
				LystItem rightItem = new LystItem(rightItemProps[0], rightItemProps[1], rightItemProps[2],
						Integer.parseInt(rightItemProps[3]), Integer.parseInt(rightItemProps[4]),
						Integer.parseInt(rightItemProps[5]));
				Cookie listIdCookie = getCookie(freshBatch, "listId");
				if (listNameCookie != null) {
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
			} else if (requestAction.equals("viewLists")) {
				if (categoryHtml == null) {
					categoryHtml = d.getMenu();
				}
				request.setAttribute("CategoryHTML", categoryHtml);

				JSONObject json = new JSONObject();
				JSONArray return_lists = new JSONArray();
				JSONObject single_list;

				String category = (String) request.getParameter("category");
				if (category == null) {
					category = "Everything";
				}
				String[] deliveredAsStrings = request.getParameterValues("delivered[]");
				ArrayList<Integer> delivered = new ArrayList<Integer>();
				if (deliveredAsStrings != null) {
					for (String string : deliveredAsStrings) {
						delivered.add(Integer.parseInt(string));
					}
				}
				ArrayList<Integer> result = null;
				boolean is_final = true;
				Integer ids_to_get[];
				List<Item> items;
				try {
					int listId = Integer.parseInt(category);
					ids_to_get = new Integer[] { listId };
					items = d.getListIDsFromCategoryTable(ids_to_get);
				} catch (Exception e) {
					result = d.getRandomListNumbers(category, delivered, n_lists);
					is_final = checkLastReturn(result);
					if (is_final) {
						try {
							result.remove((int) n_lists);
						} catch (Exception ex) {
						}
					}
					response.addIntHeader("isFinal", (is_final ? 1 : 0));

					ids_to_get = new Integer[result.size()];
					ids_to_get = result.toArray(ids_to_get);
					items = d.getListIDsFromCategoryTable(ids_to_get);
				}

				try {
					for (Item item : items) {
						single_list = new JSONObject();
						single_list.put("ListName", item.getString("ListName"));
						single_list.put("PicPath", item.getString("PicPath"));
						single_list.put("Category", "Current");// work here
						single_list.put("CurrentLeader", "Lebron James"); // work
																			// here
																			// too
						single_list.put("ListID", item.getInt("Id"));
						return_lists.put(single_list);
					}

					json.put("lists", return_lists);

				} catch (Exception e) {
					e.printStackTrace();
				}

				response.setContentType("application/json");
				// response.getWriter().append("Served at:
				// ").append(request.getContextPath());
				response.getWriter().write(json.toString());
			} else if ((requestAction.equals("initialLoad")) || (requestAction.equals("load"))) {
				serveLists(requestAction, request, response, d);
			}
		}
		// look at url if no request action, used to view lists and list items
		else {
			StringBuffer stringBuffer = request.getRequestURL();
			String url = stringBuffer.toString();
			String[] split = url.split("/");
			String identifier = "";
			String listItem = "";
			for (int i = 0; i < split.length; i++) {
				if (split[i].equals("bro")) {
					if (i + 1 < split.length) {
						identifier = split[i + 1];
					}
					if (i + 2 < split.length) {
						listItem = split[i + 2];
					}
				}
			}
			if (identifier.equals("lists")) {
				if (categoryHtml == null) {
					categoryHtml = d.getMenu();
				}
				request.setAttribute("CategoryHTML", categoryHtml);
				request.getRequestDispatcher("/lists.jsp").forward(request, response);
			} else if (!listItem.equals("")) {
				// navigate to the item page
				String[] splitItem = listItem.split("_");
				String itemName = splitItem[0];
				for (int i = 1; i < splitItem.length; i++) {
					itemName += " " + splitItem[i];
				}
				String[] splitList = identifier.split("_");
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
				request.setAttribute("listName", identifier);
				int listId = d.getListId(identifier);
				request.setAttribute("listId", listId);
				request.getRequestDispatcher("/thelist.jsp").forward(request, response);
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

	private static boolean checkLastReturn(ArrayList<Integer> input) {
		if (input.size() == n_lists) {
			return false;
		}
		return true;
	}

	protected void serveLists(String action, HttpServletRequest request, HttpServletResponse response,
			DatabaseAccessor db) throws ServletException, IOException {
		
		if (action.equals("initialLoad")) {
			System.out.println("Made it here");
			response.setContentType("application/json");
			String templistid = request.getParameter("ListID");
			int listid = Integer.parseInt(templistid);

			String tempattributenumber = request.getParameter("Attribute");
			int attributenumber = Integer.parseInt(tempattributenumber);
			
			ArrayList<Integer> items = db.getRankedIDsOnlyIDs(listid, attributenumber);
			
			JSONArray jsonIDs = new JSONArray(items);
			
			JSONObject jsonO = new JSONObject();
			try{
			jsonO.put("sortedItemIDs", jsonIDs);
			}
			catch (JSONException e) {
				e.printStackTrace();
			}
			response.getWriter().write(jsonO.toString());
		
		} else if (action.equals("load")) {
			
			// first step -- getting the attributes for that particular list
			String templistid = request.getParameter("ListID");
			int listid = Integer.parseInt(templistid);
			String tempattributenumber = request.getParameter("Attribute");
			final int attributenumber = Integer.parseInt(tempattributenumber);
			
			
			String[] tempItems = request.getParameterValues("ItemsToGet[]");
			
			int lengthReturned = tempItems.length;
			
			//most likely this will be unused; server at this point has no idea what's final and what isnt. front end does
			boolean is_final = false;
			response.addIntHeader("isFinal", (is_final ? 1 : 0));

			Map<String, Object> tempItemMap;
			JSONArray itemAttributes = null;

			ArrayList<LystItem> lystItems = new ArrayList<LystItem>();
			for (int i = 0; i < lengthReturned; i++) {
				int itemID = Integer.parseInt(tempItems[i]);
				
				LystItem tmpItem = db.getItem(itemID);
				String itemname = tmpItem.name;
				String picpath = tmpItem.picPath;
				String belongingList = tmpItem.belongingList;
				
				ArrayList<Map<String, Object>> attributeList = db.getAttributeItem(itemID, listid);

				int num_attributes = attributeList.size();
				Map<String, Object> tempAttMap;

				ArrayList<Attribute> attributeArrayList = new ArrayList<Attribute>();
				for (int j = 0; j < num_attributes; j++) {
					tempAttMap = attributeList.get(j);
					int attributeNumber = (Integer) tempAttMap.get("AttributeNumber");
					int ranking = (Integer) tempAttMap.get("Ranking");
					int rating = (Integer) tempAttMap.get("Rating");
					String attributeName = (String) tempAttMap.get("AttributeName");
					if (attributeNumber != j) {
						System.out.println("Something might be wrongo");
					}
					Attribute attToAdd = new Attribute(attributeName, attributeNumber, rating, ranking);
					attributeArrayList.add(attToAdd);
				}
				// now to build the temp item Lystitem type
				LystItem item;
					item = new LystItem(itemname, belongingList, picpath, 0, listid, itemID);
					item.attributes = attributeArrayList;
					item.currentlySortedAttributeNumber = attributenumber;
					lystItems.add(item);

			}
			
//			Comparator<LystItem> attributeComparator = new Comparator<LystItem>() {
//				//i dont think this guy is necessary since the items are coming back sorted already
//				//the list that resides at the front end is already sorted
//				//we get back an array of IDs and the "load" just sends back items in that order
//				
//				public int compare(LystItem i1, LystItem i2) {
//					Attribute a1 = null;
//					Attribute a2 = null;
//					for (int i = 0; i < i1.attributes.size(); i++) {
//						if (i1.attributes.get(i).getAttributeNumber() == attributenumber) {
//							a1 = i1.attributes.get(i);
//						}
//					}
//					for (int i = 0; i < i1.attributes.size(); i++) {
//						if (i2.attributes.get(i).getAttributeNumber() == attributenumber) {
//							a2 = i2.attributes.get(i);
//						}
//					}
//
//					return a1.getRanking() - a2.getRanking();
//
//				}
//
//			};

			//lystItems.sort(attributeComparator);
			
			request.setAttribute("lystItems", lystItems);

			request.getRequestDispatcher("/thelist.jsp").forward(request, response);

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
