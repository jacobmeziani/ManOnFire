package Data;

import java.math.BigInteger;
import java.util.ArrayList;

import Database.DatabaseAccessor;

public class RatingsProcessor extends Thread {

	private String listName;

	private int[] scores;

	private ArrayList<Attribute> leftWorldAttributes;

	private ArrayList<Attribute> rightWorldAttributes;

	public void run() {
		
		DatabaseAccessor d = new DatabaseAccessor();
		for (int i = 0; i < scores.length; i++) {
			int currentScore = scores[i] - 5;
			if (currentScore >= 0) {
				int rightWorldScore = 10;
				int leftWorldScore = 10 - (currentScore * 2);
				rightWorldAttributes.get(i).points = rightWorldAttributes.get(i).points.add(BigInteger.valueOf(rightWorldScore));
				leftWorldAttributes.get(i).points = leftWorldAttributes.get(i).points.add(BigInteger.valueOf(leftWorldScore));
				rightWorldAttributes.get(i).wins = rightWorldAttributes.get(i).wins.add(BigInteger.valueOf(1));
				if (currentScore == 0) {
					leftWorldAttributes.get(i).wins = leftWorldAttributes.get(i).wins.add(BigInteger.valueOf(1));
				}
			}
			else{
				int leftWorldScore = 10;
				int rightWorldScore = 10 - (-currentScore * 2);
				rightWorldAttributes.get(i).points = rightWorldAttributes.get(i).points.add(BigInteger.valueOf(rightWorldScore));
				leftWorldAttributes.get(i).points = leftWorldAttributes.get(i).points.add(BigInteger.valueOf(leftWorldScore));
				leftWorldAttributes.get(i).wins = leftWorldAttributes.get(i).wins.add(BigInteger.valueOf(1));
			}
			rightWorldAttributes.get(i).entries = rightWorldAttributes.get(i).entries.add(BigInteger.valueOf(1));
			leftWorldAttributes.get(i).entries = leftWorldAttributes.get(i).entries.add(BigInteger.valueOf(1));
			rightWorldAttributes.get(i).computeAverage();
			leftWorldAttributes.get(i).computeAverage();
		}
		d.UpdateAttributes(rightWorldAttributes);
		d.UpdateAttributes(leftWorldAttributes);
		
		//Need to sort and rank
	}

	public RatingsProcessor(String listName, int[] scores, ArrayList<Attribute> leftWorldAttributes,
			ArrayList<Attribute> rightWorldAttributes) {
		this.listName = listName;
		this.scores = scores;
		this.leftWorldAttributes = leftWorldAttributes;
		this.rightWorldAttributes = rightWorldAttributes;
	}
	

}
