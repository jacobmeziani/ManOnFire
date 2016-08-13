package Display;

public class ResultAttributes {

	private int leftItemWorldScore;

	private int rightItemWorldScore;

	private int leftItemUserScore;

	private int rightItemUserScore;

	private String name;

	private boolean leftUserWins;
	
	private boolean leftWorldWins;

	public boolean isLeftWorldWins() {
		leftWorldWins = (leftItemWorldScore - rightItemWorldScore) > 0;
		return leftWorldWins;
	}

	public void setLeftWorldWins(boolean leftWorldWins) {
		this.leftWorldWins = leftWorldWins;
	}

	private int userBarPercent;
	
	private int worldBarPercent;

	public int getWorldBarPercent() {
		if(leftWorldWins){
			worldBarPercent = leftItemWorldScore - rightItemWorldScore;
		}
		else{
			worldBarPercent = rightItemWorldScore - leftItemWorldScore;
		}
		return worldBarPercent;
	}

	public void setWorldBarPercent(int worldBarPercent) {
		this.worldBarPercent = worldBarPercent;
	}

	public int getUserBarPercent() {
		if (leftUserWins) {
			userBarPercent = leftItemUserScore * 20;
		} else {
			userBarPercent = rightItemUserScore * 20;
		}
		return userBarPercent;
	}

	public void setUserBarPercent(int userBarPercent) {
		this.userBarPercent = userBarPercent;
	}

	public boolean isLeftUserWins() {
		leftUserWins = (leftItemUserScore - rightItemUserScore) > 0;
		return leftUserWins;
	}

	public void setLeftWins(boolean leftWins) {
		this.leftUserWins = leftWins;
	}

	public int getLeftItemWorldScore() {
		return leftItemWorldScore;
	}

	public void setLeftItemWorldScore(int leftItemWorldScore) {
		this.leftItemWorldScore = leftItemWorldScore;
	}

	public int getRightItemWorldScore() {
		return rightItemWorldScore;
	}

	public void setRightItemWorldScore(int rightItemWorldScore) {
		this.rightItemWorldScore = rightItemWorldScore;
	}

	public int getLeftItemUserScore() {
		return leftItemUserScore;
	}

	public void setLeftItemUserScore(int leftItemUserScore) {
		this.leftItemUserScore = leftItemUserScore;
	}

	public int getRightItemUserScore() {
		return rightItemUserScore;
	}

	public void setRightItemUserScore(int rightItemUserScore) {
		this.rightItemUserScore = rightItemUserScore;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
