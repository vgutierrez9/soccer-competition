package asgn1SoccerCompetition;

import java.util.ArrayList; 
import java.util.Collections;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


import asgn1Exceptions.LeagueException;
//import asgn1Exceptions.TeamException;
//import asgn1SportsUtils.WLD;

/**
 * A class to model a soccer league. Matches are played between teams and points awarded for a win,
 * loss or draw. After each match teams are ranked, first by points, then by goal difference and then
 * alphabetically. 
 * 
 * @author Alan Woodley
 * @author Vanessa Gutierrez
 * @version 1.0
 *
 */
public class SoccerLeague implements SportsLeague{
	// Specifies the number of team required/limit of teams for the league
	private int requiredTeams, registeredTeams;
	final static private int defaultNumTeams = 3;
	
	//Data structure containing teams in league
	private ArrayList<SoccerTeam> leagueTeams;
	
	// Specifies is the league is in the off season
	private boolean offSeason;
	
	
	/**
	 * Generates a model of a soccer team with the specified number of teams. 
	 * A season can not start until that specific number of teams has been added. 
	 * One that number of teams has been research no more teams can be added unless
	 * a team is first removed. 
	 * 
	 * @param requiredTeams The number of teams required/limit for the league.
	 */
	public SoccerLeague (int requiredTeams){
		//Check if parameter is valid -> set requiredTeam size
		if(requiredTeams > 1){
			this.requiredTeams = requiredTeams;
		}
		//Set requiredTeams to default league size if parameter is invalid (0, 1, or negative) 
		else{
			this.requiredTeams = defaultNumTeams;
		}
		leagueTeams = new ArrayList<SoccerTeam>(requiredTeams);
		registeredTeams = 0;
		offSeason = true;
	}

	
	/**
	 * Registers a team to the league.
	 * 
	 * @param team Registers a team to play in the league.
	 * @throws LeagueException If the season has already started, if the maximum number of 
	 * teams allowed to register has already been reached or a team with the 
	 * same official name has already been registered.
	 */
	public void registerTeam(SoccerTeam team) throws LeagueException {
		//Check if league has space
		if (registeredTeams < requiredTeams){
			//Check if league already has team
			if(leagueTeams.contains(team)){
					throw new LeagueException(new String(team.getOfficialName() + " is already registered in the league."));
			}
			if(!offSeason){
				throw new LeagueException(new String("The season has already started."));
			}
			//add team to league
			else{
				leagueTeams.add(team);
				registeredTeams++;
			}
		}
		else{
			throw new LeagueException(new String("The league is full; no more teams may be added."));
		}

	}
	
	/**
	 * Removes a team from the league.
	 * 
	 * @param team The team to remove
	 * @throws LeagueException if the season has not ended or if the team is not registered into the league.
	 */
	public void removeTeam(SoccerTeam team) throws LeagueException{
		//Remove team or throw exception if team does not exist in the league
		if(!leagueTeams.remove(team)){
			throw new LeagueException(new String(team.getOfficialName() + " cannot be removed as they are not a member of this league."));
		}

		registeredTeams--;
	}
	
	/** 
	 * Gets the number of teams currently registered to the league
	 * 
	 * @return the current number of teams registered
	 */
	public int getRegisteredNumTeams(){
		return registeredTeams;
	}
	
	/**
	 * Gets the number of teams required for the league to begin its 
	 * season which is also the maximum number of teams that can be registered
	 * to a league.

	 * @return The number of teams required by the league/maximum number of teams in the league
	 */
	public int getRequiredNumTeams(){
		return requiredTeams;
	}
	
	/** 
	 * Starts a new season by reverting all statistics for each times to initial values.
	 * 
	 * @throws LeagueException if the number of registered teams does not equal the required number of teams or if the season has already started
	 */
	public void startNewSeason() throws LeagueException{
		//If league has enough teams,
		if(registeredTeams == requiredTeams){
			//revert each teams stats
			for(int i = 0; i < requiredTeams; i++){
				leagueTeams.get(i).resetStats();
			}
			offSeason = false;
		}
		else{
			throw new LeagueException(new String("There are " + registeredTeams + " teams registered. This league requires " + requiredTeams + " registered teams to commence the season."));
		}
	}
	

	/**
	 * Ends the season.
	 * 
	 * @throws LeagueException if season has not started
	 */
	public void endSeason() throws LeagueException{
		if(offSeason){
			throw new LeagueException(new String("The season has not started yet."));
		}
		offSeason = true;
		this.sortTeams();		
	}
	
	/**
	 * Specifies if the league is in the off season (i.e. when matches are not played).
	 * @return True If the league is in its off season, false otherwise.
	 */
	public boolean isOffSeason(){
		return this.offSeason;
	}
	
	
	
	/**
	 * Returns a team with a specific name.
	 * 
	 * @param name The official name of the team to search for.
	 * @return The team object with the specified official name.
	 * @throws LeagueException if no team has that official name.
	 */
	public SoccerTeam getTeamByOfficalName(String name) throws LeagueException{		
		//Search for team by name in league
		for(int i = 0; i < requiredTeams; i++){
			if(leagueTeams.get(i).getOfficialName().equals(name)){
				return leagueTeams.get(i);
			}
		}
		//Finished search and team not found, so throw exception
		throw new LeagueException(new String("A team by the name of " + name + " cannot be found in this league."));
	}
		
	/**
	 * Plays a match in a specified league between two teams with the respective goals. After each match the teams are
	 * resorted.
     *
	 * @param homeTeamName The name of the home team.
	 * @param homeTeamGoals The number of goals scored by the home team.
	 * @param awayTeamName The name of the away team.
	 * @param awayTeamGoals The number of goals scored by the away team.
	 * @throws LeagueException If the season has not started or if both teams have the same official name. 
	 */
	public void playMatch(String homeTeamName, int homeTeamGoals, String awayTeamName, int awayTeamGoals) throws LeagueException{
		if(offSeason){
			throw new LeagueException(new String("Match cannot be played since league is in off season."));
		}
		if(homeTeamName.equals(awayTeamName)){
			throw new LeagueException(new String("Match cannot be played without two different teams in the league."));
		}
		if(!leagueTeams.contains(getTeamByOfficalName(homeTeamName)) || !leagueTeams.contains(getTeamByOfficalName(awayTeamName))){
			throw new LeagueException(new String("Match cannot be played with teams not members of this league."));
		}
		try {
			getTeamByOfficalName(homeTeamName).playMatch(homeTeamGoals, awayTeamGoals);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		try {
			getTeamByOfficalName(awayTeamName).playMatch(awayTeamGoals, homeTeamGoals);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
	}
	
	/**
	 * Displays a ranked list of the teams in the league  to the screen.
	 */
	public void displayLeagueTable(){
		System.out.println("LEAGUE STANDINGS:");
		System.out.println("Name" + '\t' + '\t' + "Nick name" + '\t' + "WLD" + '\t' + "Played" + '\t' + "Won" + '\t' + "Lost" + '\t' + "Drawn" + '\t' + "Scored" + '\t' + "Concd." + '\t' + "Diff." + '\t' + "Points");
		this.sortTeams();
		for(int i = 0; i < requiredTeams; i++){
			leagueTeams.get(i).displayTeamDetails();
		}
	}	
	
	/**
	 * Returns the highest ranked team in the league.
     *
	 * @return The highest ranked team in the league. 
	 * @throws LeagueException if the number of teams is zero or less than the required number of teams.
	 */
	public SoccerTeam getTopTeam() throws LeagueException{
		//Start index of max at first team
		int indexOfMax = 1;
		
		//Check for valid number of teams in league
		if(registeredTeams == 0){
			throw new LeagueException(new String("There are an invalid number of teams in the league."));
		}
		
		this.sortTeams();
		
		return leagueTeams.get(0);
	}
	
	/**
	 * Returns the lowest ranked team in the league.
     *
	 * @return The lowest ranked team in the league. 
	 * @throws LeagueException if the number of teams is zero or less than the required number of teams.
	 */
	public SoccerTeam getBottomTeam() throws LeagueException{
		//Start index of min at first team
		int indexOfMin = 1;
		
		//Check for valid number of teams in league
		if(registeredTeams == 0){
			throw new LeagueException(new String("There are an invalid number of teams in the league."));
		}
		
		this.sortTeams();
		
		return leagueTeams.get(registeredTeams - 1);
		
	}

	/** 
	 * Sorts the teams in the league.
	 * Highest ranking team at 0 in arrayList, lowest ranking team at i = requiredTeams-1
	 */
    public void sortTeams(){		
    	Collections.sort(leagueTeams);
    		
    /*	
		boolean swapped = false;
		
		//Bubble sort the teams, highest ranking team at 0 in arrayList, lowest ranking team at i = requiredTeams-1
    	do{
    		swapped = false;
    		for(int i = 0; i < requiredTeams - 1 ; i++){
    			
    			if(leagueTeams.get(i).compareTo(leagueTeams.get(i + 1)) < 0){
    				SoccerTeam temp = leagueTeams.get(i);
    				leagueTeams.set(i, leagueTeams.get(i + 1));
    				leagueTeams.set(i + 1, temp);
    				swapped = true;
    			}
    		}
    	}while(swapped);
	*/
    }
    
    /**
     * Specifies if a team with the given official name is registered to the league.
     * 
     * @param name The name of a team.
     * @return True if the team is registered to the league, false otherwise. 
     */
    public boolean containsTeam(String name){
    	for(int i = 0; i < registeredTeams; i++){
    		if(leagueTeams.get(i).getOfficialName().equals(name)){
    			return true;
    		}
    	}
		return false;
    }
    
}
