package Data;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.LinkedHashSet;

public class HTMLCategory {

	String name;
	boolean leaf; //means it is final
	HTMLCategory[] subCategories;
	//String[] subLysts;
	LinkedHashSet<String>subLysts;
	int level;
	
	public static HTMLCategory buildit(CategoryDB cdb) {
		HTMLCategory top = new HTMLCategory("Top",cdb,0);
		return top;
	}
	//builds object categories based on Category from database collection 
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
		} else {
			this.leaf=true;
			this.subLysts = temp.subLysts;
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
		if (this.leaf==true) {
			String html = "<li class = \"menu-item final\"><a class = \"showmethemoney\">"+this.name+"</a></li>\n";
			return html;
		} else if (this.leaf==false) {
			if (this.name.equals("Top")) {
				String top_ul = "<ul id =\"top-nav\" class=\"showmethemoney\">\n";
				String html_first_li = "<li class = \"menu-item final\"><a class=\"showmethemoney\"> Everything </a></li>\n";
				String html_recurse = "";
				String temp;
				for (HTMLCategory category:subCategories) {
					temp = category.HTMLWriter();
					html_recurse = html_recurse+temp;
				}
				String html_response = top_ul+html_first_li+html_recurse+"</ul>\n";
				return html_response;
			} else if (!(this.name.equals("Top"))) { //writes all other Li except top
				String html_opening_li = "<li class = \"menu-item children\"><a class=\"showmethemoney testclass\">"+this.name+"</a>\n";
				String html_opening_ul = "<ul class = \"showmethemoney\">\n";
				String html_first_li = "<li class = \"menu-item final\"><a class=\"showmethemoney\"> All "+this.name+"</a></li>\n";
				String html_recurse = "";
				String temp;
				for (HTMLCategory category:subCategories) {
					temp = category.HTMLWriter();
					html_recurse = html_recurse+temp;
				}
				String html_response = html_opening_li+html_opening_ul+html_first_li+html_recurse+"</ul></li>\n";
				return html_response;
			} 
		}
		return "MISTEK";	
	}
}
