package manipulation;

public class Matchup {
	
	static void run (Element winner, Element loser, int rating) {
		winner.printstuff();
		loser.printstuff();
		winner.matchup(loser, rating, true);
		loser.matchup(winner, (-1)*rating, false);
	}
}
