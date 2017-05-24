/**
 * 
 */
package asgn1SoccerCompetition;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import asgn1Exceptions.CompetitionException;
import asgn1Exceptions.LeagueException;

/**
 * A class to model a soccer competition. The competition contains one or more number of leagues, 
 * each of which contain a number of teams. Over the course a season matches are played between
 * teams in each league. At the end of the season a premier (top ranked team) and wooden spooner 
 * (bottom ranked team) are declared for each league. If there are multiple leagues then relegation 
 * and promotions occur between the leagues.
 * 
 * @author Alan Woodley
 * @version 1.0
 *
 */
public class SoccerCompetition implements SportsCompetition{
	private String name;
	private int numLeagues, numTeams;
	
	//Data structure containing leagues in competition
	private ArrayList<SoccerLeague> compLeagues;
	
	
	/**
	 * Creates the model for a new soccer competition with a specific name,
	 * number of leagues, and number of teams in each league.
	 * 
	 * @param name The name of the competition.
	 * @param numLeagues The number of leagues in the competition.
	 * @param numTeams The number of teams in each league.
	 */
	public SoccerCompetition(String name, int numLeagues, int numTeams){
		this.name = name;
		this.numLeagues = numLeagues;
		this.numTeams = numTeams;
			
		//Initialize competition data structure
		compLeagues = new ArrayList<SoccerLeague>(numLeagues);
		//Add league objects to data structure 
		for(int i = 0; i < numLeagues; i++){
			compLeagues.add(new SoccerLeague(numTeams));
		}
	}
	
	/**
	 * Retrieves a league with a specific number (indexed from 0). Returns an exception if the 
	 * league number is invalid.
	 * 
	 * @param leagueNum The number of the league to return.
	 * @return A league specified by leagueNum.
	 * @throws CompetitionException if the specified league number is less than 0.
	 *  or equal to or greater than the number of leagues in the competition.
	 */
	public SoccerLeague getLeague(int leagueNum) throws CompetitionException{
		//Check if league number is valid
		if(leagueNum >= 0 && leagueNum < numLeagues){
			return compLeagues.get(leagueNum);
		}
		else{
			throw new CompetitionException(new String(leagueNum + " is an invalid league index. League indexes must be between 0 and " + (numLeagues-1)));
		}
	}
	

	/**
	 * Starts a new soccer season for each league in the competition.
	 */
	public void startSeason() {
		for(int i = 0; i < numLeagues; i++){
			try {
				compLeagues.get(i).startNewSeason();
			} catch (LeagueException e) {
				e.printStackTrace();
			}
		}
	}

	
	/** 
	 * Ends the season of each of the leagues in the competition. 
	 * If there is more than one league then it handles promotion
	 * and relegation between the leagues.  
	 * 
	 */
	public void endSeason()  {
		for(int i = 0; i < numLeagues; i++){
			try {
				compLeagues.get(i).endSeason();
			} catch (LeagueException e) {
				e.printStackTrace();
			}
		}
		if(numLeagues > 1){
			BlockingQueue<SoccerTeam> relegatedTeams, promotedTeams;
			relegatedTeams = new LinkedBlockingQueue<SoccerTeam>(numLeagues-1);
			promotedTeams = new LinkedBlockingQueue<SoccerTeam>(numLeagues-1);
			SoccerTeam bottomTeam, topTeam;
			for(int i = 0; i < numLeagues-1; i++){
				try {
					bottomTeam = compLeagues.get(i).getBottomTeam();
					relegatedTeams.offer(bottomTeam);
					compLeagues.get(i).removeTeam(bottomTeam);
				} catch (LeagueException e) {
					e.printStackTrace();
				}
			}
			
			for(int i = 1; i < numLeagues; i++){
				try {
					topTeam = compLeagues.get(i).getTopTeam();
					promotedTeams.offer(topTeam);
					compLeagues.get(i).removeTeam(topTeam);
				} catch (LeagueException e) {
					e.printStackTrace();
				}
			}
			for(int i = 0; i < numLeagues-1; i++){
				try {
					compLeagues.get(i).registerTeam(promotedTeams.remove());
				} catch (LeagueException e) {
					e.printStackTrace();
				}
			}
			for(int i = 1; i < numLeagues; i++){
				try {
					compLeagues.get(i).registerTeam(relegatedTeams.remove());
				} catch (LeagueException e) {
					e.printStackTrace();
				}
			}
		}
		for(int i = 0; i < numLeagues; i++){
			compLeagues.get(i).sortTeams();
		}
	}

	/** 
	 * For each league displays the competition standings.
	 */
	public void displayCompetitionStandings(){
		System.out.println("+++++" + this.name + "+++++");
		
		for(int i = 0; i < numLeagues; i++){
			System.out.println("---- League" + (i +1) + " ----");
			compLeagues.get(i).displayLeagueTable();
		}
	}
	

}
