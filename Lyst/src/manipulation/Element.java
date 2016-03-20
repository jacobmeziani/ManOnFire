package manipulation;

public class Element {
	
	static private float opponent_sens = 5;
	private String name;
	private double score;
	private int num_scores;
	
	public Element (String name, double score,int num) {
		this.name = name;
		this.score = score;
		this.num_scores = num;
	}
	
	public void matchup(Element opponent, int rating, boolean winner) {
		//rating is from -5 to 5
		//following lines are there to make sure no scores go over 100 or under 50. basically the closer scores are to the limits, the less
		//sensitive the calculation becomes
		double mult_loc;
		double opp_score = opponent.getScore();
		if (winner) {
			mult_loc=((100-this.getScore())/50);
		} else {
			mult_loc=((this.getScore()-50)/50);
		}
		
		double val_add = (this.getScore()+ (opp_score*(10+(opponent_sens*rating)*mult_loc))/10)/2;
		this.addScore(val_add);
		//System.out.println(mult_loc);
		//System.out.print(" -<VAL_ADD: ");
		//System.out.println(val_add);
	}
	
	void printstuff () {
		System.out.print(name);
		System.out.print(" score : ");
		System.out.println(this.getScore());
		
	}
	public double getScore () {
		//returns simple score
		double score_ = score/num_scores;
		return (double)score_;
	}
	
	
	public void addScore(double add) {
		this.score = this.score + add;
		this.num_scores=this.num_scores + 1;
	}
	
	
	String getName (){
		return this.name;
	}
	
}
