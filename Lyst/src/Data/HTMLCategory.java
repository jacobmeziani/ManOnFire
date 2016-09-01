package Data;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.LinkedHashSet;
import java.math.BigDecimal;

public class HTMLCategory {

	String name;
	boolean leaf; //means it is final
	HTMLCategory[] subCategories;
	//String[] subLysts;
	ArrayList<ListServerInit>subLysts;
	int level;
	
	public static HTMLCategory buildit(CategoryDB cdb) {
		HTMLCategory top = new HTMLCategory("Everything",cdb,0);
		return top;
	}
	//builds object categories based on Category from database collection 
	boolean isLystHere (int value) {
		try {
			for (ListServerInit lyst:subLysts) {
				if (lyst.getID()==value) return true;
			} return false;
		} catch (NullPointerException e) {
			return false;
		}
	}
	
	HTMLCategory (String name,CategoryDB cdb,int level) {
		Category temp = cdb.findCategory(name);
		this.name = name;
		this.level = level;
		if (temp.subCats!=null) {
			this.leaf = false; 
			LinkedHashSet<String> subCats = temp.subCats;
			int s = subCats.size();
			subCategories = new HTMLCategory[s];
			Iterator<String> iterator = subCats.iterator();
			int i = 0;
			String new_name;
			while (iterator.hasNext()) {
				new_name = iterator.next();
				HTMLCategory new_cat = new HTMLCategory (new_name,cdb,level+1);
				subCategories[i]=new_cat;
				i++;
			}
			if (temp.subLysts!=null) {
				Iterator <BigDecimal> iteratornum = temp.subLysts.iterator();
				int temp_int;
				ArrayList<ListServerInit> templist = new ArrayList<ListServerInit>();
				while(iteratornum.hasNext()) {
					//String bigint= iteratornum.next().getClass().getName();
					//System.out.println(bigint);
					int list_id = iteratornum.next().intValue(); //intValueExact
					//temp_int = Integer.parseInt(list_id);
					templist.add(cdb.getListObject(list_id));
					System.out.println("list: " + cdb.getListObject(list_id).getName());
				}
				this.subLysts = templist;
			}
		} else {
			this.leaf = true;
			if (temp.subLysts!=null) {
				Iterator <BigDecimal> iteratornum = temp.subLysts.iterator();
				int temp_int;
				ArrayList<ListServerInit> templist = new ArrayList<ListServerInit>();
				while(iteratornum.hasNext()) {
					//String bigint= iteratornum.next().getClass().getName();
					//System.out.println(bigint);
					int list_id = iteratornum.next().intValue(); //intValueExact
					//temp_int = Integer.parseInt(list_id);
					templist.add(cdb.getListObject(list_id));
					//System.out.println("list: " + cdb.getListObject(list_id).getName());
				}
				this.subLysts = templist;
			}
		}

		//		else if (temp.subLysts!=null) {//must delete if later.
		//			this.leaf = true;
		//			LinkedHashSet<String> subLysts = temp.subLysts;
		//			int s = subLysts.size();
		//			this.subLysts = new String[s];
		//			Iterator<String> iterator = subLysts.iterator();
		//			int i = 0;
		//			String new_lyst;
		//			while (iterator.hasNext()) {
		//				new_lyst=iterator.next();
		//				this.subLysts[i]=new_lyst;
		//				i++;
		//				}
		//			}


	}
	
	public String HTMLWriter() {
		System.out.println("bruhh");
		System.out.println(this.name);
		if (this.leaf==true) {
			System.out.println("inhere1");
			if (subLysts!=null) {
			String html_opening_li = "<li class = \"menu-item children finalcategory\"><a class=\"showmethemoney \">"+this.name+"</a>\n";
			String html_opening_ul = "<ul class = \"showmethemoney\">\n";
			String up_html = "<li class = \"final upmenu\"><a class = \"showmethemoney\"><span class=\"glyphicon glyphicon-arrow-left\"></span>Back</a></li>\n";
			String close_html = "<li class = \"final closemenu\"><a class = \"showmethemoney\"><span class=\"glyphicon glyphicon-remove\"></span>Close</a></li>\n";
			String html_first_li = "<li class = \"menu-item list-item final finalcategory all_class\"><a class=\"showmethemoney all_class\"> All "+this.name+"</a></li>\n";
			String html_lysts = "";
			String temp;
			for (ListServerInit list:subLysts) {
				temp = "<li class = \"list-item menu-item final\"><a class = \"showmethemoney\" database-id=\"" + list.getID() + "\">"+list.getName()+"</a></li>\n";
				html_lysts = html_lysts+temp;
			}
			String html = html_opening_li+html_opening_ul+up_html+close_html+html_first_li+html_lysts+"</ul></li>\n";
			return html;
			}
		} else if (this.leaf==false) {
			System.out.println("in here 3");
			if (this.name.equals("Everything")) {
				System.out.println("inhere2");
				String top_ul = "<ul id =\"top-nav\" class=\"showmethemoney\">\n";
				String close_html = "<li class = \"final closemenu\"><a class = \"showmethemoney\"><span class=\"glyphicon glyphicon-remove\"></span>Close</a></li>\n";
				String html_first_li = "<li class = \"menu-item final\"><a class=\"showmethemoney\">Everything</a></li>\n";
				String html_recurse = "";
				String temp;
				for (HTMLCategory category:subCategories) {
					temp = category.HTMLWriter();
					html_recurse = html_recurse+temp;
				}
				String html_response = top_ul+close_html+html_first_li+html_recurse+"</ul>\n";
				return html_response;
			} else { //writes all other Li except top
				String html_opening_li = "<li class = \"menu-item children\"><a class=\"showmethemoney testclass\">"+this.name+"</a>\n";
				String html_opening_ul = "<ul class = \"showmethemoney\">\n";
				String up_html = "<li class = \"final upmenu\"><a class = \"showmethemoney\"><span class=\"glyphicon glyphicon-arrow-left\"></span>Back</a></li>\n";
				String close_html = "<li class = \"final closemenu\"><a class = \"showmethemoney\"><span class=\"glyphicon glyphicon-remove\"></span>Close</a></li>\n";
				String html_first_li = "<li class = \"menu-item final all_class\"><a class=\"showmethemoney\"> All "+this.name+"</a></li>\n";
				String html_recurse = "";
				String temp;
				for (HTMLCategory category:subCategories) {
					temp = category.HTMLWriter();
					html_recurse = html_recurse+temp;
				}
				String html_response = html_opening_li+html_opening_ul+up_html+close_html+html_first_li+html_recurse+"</ul></li>\n";
				return html_response;
			}
		}
		return null;	
	}
	
	public ArrayList<Integer> checkLists (ArrayList<String> missingString) {
		//for use with checkCategories.java
		ArrayList<Integer> all_inside = new ArrayList<Integer>();
		if (this.leaf) {
			try {
			for (ListServerInit lyst:subLysts) {
				all_inside.add(lyst.getID());
			}
			} catch (NullPointerException e) {
				missingString.add("Missing,"+ this.name); 
			}
		} else if (!this.leaf) {
			for (HTMLCategory subcat:subCategories) {
				all_inside.addAll(subcat.checkLists(missingString));
			}
			for (Integer i:all_inside) {
				if (!(isLystHere(i))) {
					String temp = "Category,"+ this.name + "," + i;
					missingString.add(temp);
					System.out.println("inside HTMLCategory --> " + this.name + " missing "+ i);
				}
			}
		}
		return all_inside;
	}
}
