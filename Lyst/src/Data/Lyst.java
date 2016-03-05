package Data;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

import javax.imageio.ImageIO;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.xspec.SS;

public class Lyst implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String listName;
	private String categories;
	private ArrayList<String> members;
	private String imagePath;

	public Lyst(String listName, String categories, ArrayList<String> members,
			String imagePath) {

		this.listName = listName;
		this.categories = categories;
		this.members = members;
		this.imagePath = imagePath;
	}

	public String getListName() {
		return listName;
	}

	public void setListName(String name) {
		listName = name;
	}

	public String getCategories() {
		return categories;
	}

	public void setCategories(String name) {
		categories = name;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String image) {
		imagePath = image;
	}

	public ArrayList<String> getMembers() {
		return members;
	}

	public void setMembers(ArrayList<String> members) {
		this.members = members;
	}

}
