package Data;

import java.io.Serializable;
import java.util.ArrayList;

public class LystItem implements Serializable {
	private static final long serialVersionUID = 1L;

	public String name;

	public String picPath;

	public ArrayList<Attribute> attributes;

	public String belongingList;

	public LystItem(String name, String belongingList, String picPath) {
		this.name = name;
		this.belongingList = belongingList;
		this.picPath = picPath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getBelongingList() {
		return belongingList;
	}

	public void setBelongingList(String belongingList) {
		this.belongingList = belongingList;
	}
	
	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

}
