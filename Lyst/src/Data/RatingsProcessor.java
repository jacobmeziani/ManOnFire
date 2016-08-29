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
		double rightAverage =0;
		double leftAverage =0;
		if (scores.length >= 1) {
			for (int i = 1; i < scores.length; i++) {
				int leftWorldScore = 0;
				int rightWorldScore =0;
				int currentScore = scores[i] - 5;
				if (currentScore >= 0) {
					rightWorldScore = 10;
					leftWorldScore = 10 - (currentScore * 2);
					rightWorldAttributes.get(i).points = rightWorldAttributes.get(i).points
							.add(BigInteger.valueOf(rightWorldScore));
					leftWorldAttributes.get(i).points = leftWorldAttributes.get(i).points
							.add(BigInteger.valueOf(leftWorldScore));
					rightWorldAttributes.get(i).wins = rightWorldAttributes.get(i).wins.add(BigInteger.valueOf(1));
					if (currentScore == 0) {
						leftWorldAttributes.get(i).wins = leftWorldAttributes.get(i).wins.add(BigInteger.valueOf(1));
					}
				} else {
					leftWorldScore = 10;
					rightWorldScore = 10 - (-currentScore * 2);
					rightWorldAttributes.get(i).points = rightWorldAttributes.get(i).points
							.add(BigInteger.valueOf(rightWorldScore));
					leftWorldAttributes.get(i).points = leftWorldAttributes.get(i).points
							.add(BigInteger.valueOf(leftWorldScore));
					leftWorldAttributes.get(i).wins = leftWorldAttributes.get(i).wins.add(BigInteger.valueOf(1));
				}
				rightWorldAttributes.get(i).entries = rightWorldAttributes.get(i).entries.add(BigInteger.valueOf(1));
				leftWorldAttributes.get(i).entries = leftWorldAttributes.get(i).entries.add(BigInteger.valueOf(1));
				rightWorldAttributes.get(i).computeAverage();
				leftWorldAttributes.get(i).computeAverage();
				
				rightAverage+=rightWorldScore;
				leftAverage += leftWorldScore;
			}
		}
		//Now we do overall, which is a bit of a mess
		rightAverage = rightAverage/(scores.length-1);
		leftAverage = leftAverage/(scores.length-1);
		if(rightAverage <= leftAverage){
			leftWorldAttributes.get(0).wins = leftWorldAttributes.get(0).wins.add(BigInteger.valueOf(1));	
		}
		if(rightAverage == leftAverage){
			rightWorldAttributes.get(0).wins = rightWorldAttributes.get(0).wins.add(BigInteger.valueOf(1));	
		}
		if(rightAverage > leftAverage){
			rightWorldAttributes.get(0).wins = rightWorldAttributes.get(0).wins.add(BigInteger.valueOf(1));	
		}
		double points = rightWorldAttributes.get(0).getAverage() * rightWorldAttributes.get(0).getEntries().intValue();
		points += rightAverage;
		double newAverage = points/(rightWorldAttributes.get(0).getEntries().intValue() +1);
		rightWorldAttributes.get(0).setAverage(newAverage);
		points = leftWorldAttributes.get(0).getAverage() * leftWorldAttributes.get(0).getEntries().intValue();
		points += leftAverage;
		newAverage = points/(leftWorldAttributes.get(0).getEntries().intValue() +1);
		leftWorldAttributes.get(0).setAverage(newAverage);		
		rightWorldAttributes.get(0).entries = rightWorldAttributes.get(0).entries.add(BigInteger.valueOf(1));
		leftWorldAttributes.get(0).entries = leftWorldAttributes.get(0).entries.add(BigInteger.valueOf(1));
		
		d.UpdateAttributes(rightWorldAttributes);
		d.UpdateAttributes(leftWorldAttributes);

	}

	public RatingsProcessor(String listName, int[] scores, ArrayList<Attribute> leftWorldAttributes,
			ArrayList<Attribute> rightWorldAttributes) {
		this.listName = listName;
		this.scores = scores;
		this.leftWorldAttributes = leftWorldAttributes;
		this.rightWorldAttributes = rightWorldAttributes;
	}

}
