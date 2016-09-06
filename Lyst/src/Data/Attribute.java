package Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;

public class Attribute implements Serializable, Comparable<Attribute> {
	private static final long serialVersionUID = 1L;

	public String name;

	public int ranking;

	public int rating;

	public BigInteger wins;
	
	public BigInteger entries;
	
	public BigInteger points;
	
	private int attributeNumber;
	
	private double average;
	
	private int listId;
	
	private int itemId;

	public String getRankingString(){
		if(ranking%10 ==1){
			if(ranking ==11){
				return ranking +"th";
			}
			return ranking +"st";
		}
		else if(ranking%10 ==2){
			if(ranking ==12){
				return ranking +"th";
			}
			return ranking +"nd";
		}
		else if(ranking%10 ==3){
			if(ranking ==13){
				return ranking +"th";
			}
			return ranking +"rd";
		}
		return ranking +"th";
	}
	
	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getListId() {
		return listId;
	}

	public void setListId(int listId) {
		this.listId = listId;
	}

	public double getAverage() {
		return average;
	}

	public void setAverage(double average) {
		this.average = average;
	}

	public Attribute(int itemId, String name, int listId, int attributeNumber,
			int ranking,int rating, BigInteger wins, BigInteger entries, BigInteger points, double average) {
		this.name = name;
		this.itemId = itemId;
		this.ranking = ranking;
		this.rating = rating;
		this.wins = wins;		
		this.entries = entries;		
		this.points = points;
		this.attributeNumber = attributeNumber;
		this.listId = listId;
		this.average = average;
	}
	
	public Attribute(String name, int attributeNumber, int rating, int ranking){
		this.name = name;
		this.attributeNumber = attributeNumber;
		this.rating = rating;
		this.ranking = ranking;
	}

	public void computeAverage(){
		if (entries.doubleValue() !=0){
			average = points.doubleValue()/entries.doubleValue();
		}
		else{
			average = 0;
		}
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}
	
	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
	
	public BigInteger getWins() {
		return wins;
	}

	public void setWins(BigInteger wins) {
		this.wins = wins;
	}
	
	public BigInteger getEntries() {
		return entries;
	}

	public void setEntries(BigInteger entries) {
		this.entries = entries;
	}
	
	public BigInteger getPoints() {
		return points;
	}

	public void setPoints(BigInteger points) {
		this.points = points;
	}

	public int getAttributeNumber() {
		return attributeNumber;
	}

	public void setAttributeNumber(int attributeNumber) {
		this.attributeNumber = attributeNumber;
	}

	public int compareTo(Attribute a) {
		// We want to return descending 
		double result = a.average - average;
		if(result > 0){
			return 1;
		}
		else if(result <0){
			return -1;
		}
		return 0;
	}

}
