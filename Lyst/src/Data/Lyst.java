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
	private ArrayList<String> attributes;
	private int size;
	private int listIndex;
	private String imagePath;

	public Lyst(String listName, int index, ArrayList<String> attributes, int members,
			String imagePath) {

		this.listName = listName;
		this.attributes = attributes;
		this.size = members;
		this.imagePath = imagePath;
		this.listIndex = index;
	}

	public String getListName() {
		return listName;
	}

	public void setListName(String name) {
		listName = name;
	}

	public ArrayList<String> getAttributes() {
		return attributes;
	}

	public void setAttributes(ArrayList<String> attr) {
		attributes = attr;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String image) {
		imagePath = image;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int members) {
		this.size = members;
	}
	
	public int getListIndex() {
		return listIndex;
	}

	public void setListIndex(int index) {
		this.listIndex = index;
	}

}
