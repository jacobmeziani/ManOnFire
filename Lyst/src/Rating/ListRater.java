package Rating;

import java.util.ArrayList;
import java.util.Collections;

import Data.Attribute;
import Data.Lyst;
import Database.DatabaseAccessor;

public class ListRater {

	public static void main(String[] args) {
		DatabaseAccessor db = new DatabaseAccessor();
		ArrayList<Lyst> lysts = db.getAllLists();
		//Goes through each list
		for(int i=0; i< lysts.size(); i++){
			Lyst currentLyst = lysts.get(i);
			int listNumber = currentLyst.getListIndex();
			String listName = currentLyst.getListName();
			//add one for overall
			int numberOfAttributes = currentLyst.getAttributes().size()+1;
			//Goes through each attribute in the list
			for(int j=0; j<numberOfAttributes; j++){
				if (listNumber==0){
				ArrayList<Attribute> currentAttributes = db.getAttributes(listNumber,j);
				Collections.sort(currentAttributes);
				int rank =1;
				currentAttributes.get(0).ranking = rank;
				for(int a =1; a<currentAttributes.size(); a++){
					if(currentAttributes.get(a-1).getAverage()== currentAttributes.get(a).getAverage()){
						// do nothing
					}
					else{
						rank = a+1;
					}
					currentAttributes.get(a).ranking = rank;
				}
				
				double highestAverage = currentAttributes.get(0).getAverage();
				double lowestAverage = currentAttributes.get(currentAttributes.size()-1).getAverage();
				double range = highestAverage - lowestAverage;
				
				for (int b=0; b <currentAttributes.size(); b++){
					currentAttributes.get(b).rating = generateRating(currentAttributes.get(b).getAverage(),lowestAverage, range);
					System.out.println(currentAttributes.get(b).getItemId() + ":" + currentAttributes.get(b).name + " Average:"+ currentAttributes.get(b).getAverage()+" Ranking:" + currentAttributes.get(b).ranking +" Rating:" + currentAttributes.get(b).rating);
				}
				db.UpdateAttributes(currentAttributes);
				
				//Update Overall ratings
				if(j==0){
					db.updateOverallRatings(currentAttributes, listName);
				}
				}
			}
		}

	}
	
	private static int generateRating(double averageScore, double lowestScore, double range){
		int rating = (int)Math.ceil(((averageScore - lowestScore)*100)/range);		
		return rating;
	}

}
