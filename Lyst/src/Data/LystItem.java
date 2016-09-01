package Data;

import java.io.Serializable;
import java.util.ArrayList;

public class LystItem implements Serializable {
	private static final long serialVersionUID = 1L;

	public String name;

	public String picPath;

	public int overallRating;

	public int itemId;

	public int listId;

	public ArrayList<Attribute> attributes;

	public String belongingList;

	public LystItem(String name, String belongingList, String picPath, int overallRating, int listId, int itemId) {
		this.name = name;
		this.belongingList = belongingList;
		if (picPath != null) {
			this.picPath = picPath;
		} else {
			this.picPath = "empty";
		}
		this.overallRating = overallRating;
		this.itemId = itemId;
		this.listId = listId;
	}

	public String getName() {
		return name;
	}
	
	public String getNameUrl() {
		String[] nameSplit = name.split(" ");
		String nameUrl ="";
		for (int i=0; i<nameSplit.length; i++){
			if(i==0){
				nameUrl += nameSplit[i];}
				else{
					nameUrl +="_"+ nameSplit[i];	
				}
		}
		return nameUrl;
	}
	
	public String getListUrl() {
		String[] listSplit = belongingList.split(" ");
		String listUrl ="";
		for (int i=0; i<listSplit.length; i++){
			if(i==0){
			listUrl += listSplit[i];}
			else{
				listUrl +="_"+ listSplit[i];	
			}
		}
		return listUrl;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOverallRating() {
		return overallRating;
	}

	public void setOverallRating(int ovr) {
		this.overallRating = ovr;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int id) {
		this.itemId = id;
	}

	public int getListId() {
		return listId;
	}

	public void setListId(int id) {
		this.listId = id;
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

	public ArrayList<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(ArrayList<Attribute> attr) {
		this.attributes = attr;
	}

}
