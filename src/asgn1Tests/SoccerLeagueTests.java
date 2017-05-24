package asgn1Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn1Exceptions.LeagueException;
import asgn1Exceptions.TeamException;
import asgn1SoccerCompetition.SoccerLeague;
import asgn1SoccerCompetition.SoccerTeam;


/**
 * A set of JUnit tests for the asgn1SoccerCompetition.SoccerLeage class
 *
 * @author Vanessa Gutierrez
 *
 */
public class SoccerLeagueTests {
	private SoccerLeague testLeagueA, testLeagueB;
	private static int leagueSize = 4;
	private SoccerTeam teamA, teamB, teamC, teamD, teamE;
	
	@Before
	public void constructTeamA(){
		try {
			teamA = new SoccerTeam("Official Name", "Nick Name");
			teamB = new SoccerTeam("officialName", "nickName");
			teamC = new SoccerTeam("official_Name", "nick_Name");
			teamD = new SoccerTeam("oName", "nName");
			teamE = new SoccerTeam("oN", "nN");
		} catch (TeamException e) {
			e.printStackTrace();
		}
		testLeagueA = new SoccerLeague(leagueSize);
	}
	
	@Test
	public void testGetRequiredNumTeams(){
		testLeagueB = new SoccerLeague(10);
		assertEquals(10, testLeagueB.getRequiredNumTeams());
	}
	
	@Test
	public void testRegisterTeam(){
		assertEquals(0, testLeagueA.getRegisteredNumTeams());
		try {
			testLeagueA.registerTeam(teamA);
		} catch (LeagueException e) {
			e.printStackTrace();
		}
		assertEquals(1, testLeagueA.getRegisteredNumTeams());
	}
	
	@Test(expected = LeagueException.class)
	public void testRegister_Duplicate_Team() throws LeagueException{
		assertEquals(0, testLeagueA.getRegisteredNumTeams());
	
		testLeagueA.registerTeam(teamA);
		testLeagueA.registerTeam(teamA);
	}
	
	@Test(expected = LeagueException.class)
	public void testRegisterTeam_FullLeague() throws LeagueException{
		assertEquals(0, testLeagueA.getRegisteredNumTeams());
	
		testLeagueA.registerTeam(teamA);
		testLeagueA.registerTeam(teamB);
		testLeagueA.registerTeam(teamC);
		testLeagueA.registerTeam(teamD);
		testLeagueA.registerTeam(teamE);
	
	}
	
	@Test
	public void testRemoveTeam(){
		assertEquals(0, testLeagueA.getRegisteredNumTeams());
		
		try {
			testLeagueA.registerTeam(teamA);
		} catch (LeagueException e) {
			e.printStackTrace();
		}
		assertEquals(1, testLeagueA.getRegisteredNumTeams());
		
		try {
			testLeagueA.removeTeam(teamA);
		} catch (LeagueException e) {
			e.printStackTrace();
		}
		assertEquals(0, testLeagueA.getRegisteredNumTeams());
	}
	
	@Test(expected = LeagueException.class)
	public void testRemoveTeam_TeamNotRegistered() throws LeagueException{
		assertEquals(0, testLeagueA.getRegisteredNumTeams());
		
		testLeagueA.removeTeam(teamB);
	}
	
	@Test(expected = LeagueException.class)
	public void testStartNewSeason_NotEnoughTeams() throws LeagueException{
		assertEquals(0, testLeagueA.getRegisteredNumTeams());
		testLeagueA.startNewSeason();		
	}
	
	@Test
	public void testStartNewSeason_ValidNumTeams(){
		testLeagueB = new SoccerLeague(2);
		
		assertTrue(testLeagueB.isOffSeason());
	
		try {
			testLeagueB.registerTeam(teamA);
			testLeagueB.registerTeam(teamB);

			testLeagueB.startNewSeason();
		} catch (LeagueException e) {
			e.printStackTrace();
		}
		
		assertEquals(false, testLeagueB.isOffSeason());
	}
	
	@Test(expected = LeagueException.class)
	public void testStartNewSeason_InvalidNumTeams() throws LeagueException{
		testLeagueB = new SoccerLeague(3);
		
		assertTrue(testLeagueB.isOffSeason());
		
		try {
			testLeagueB.registerTeam(teamA);
			testLeagueB.registerTeam(teamB);
		} catch (LeagueException e) {
			e.printStackTrace();
		}
		
		testLeagueB.startNewSeason();
	}
	
	@Test(expected = LeagueException.class)
	public void testEndSeason_SeasonNotStarted() throws LeagueException{
		testLeagueB = new SoccerLeague(3);
		
		assertTrue(testLeagueB.isOffSeason());
		
		testLeagueB.endSeason();
	}
	
	@Test
	public void testEndSeason(){
		testLeagueB = new SoccerLeague(3);
		
		assertTrue(testLeagueB.isOffSeason());
		try {
			testLeagueB.registerTeam(teamA);
			testLeagueB.registerTeam(teamB);
			testLeagueB.registerTeam(teamC);
			testLeagueB.startNewSeason();
			
			testLeagueB.endSeason();
		} catch (LeagueException e) {
			e.printStackTrace();
		}
		
		assertTrue(testLeagueB.isOffSeason());
	}
	
	@Test
	public void testGetTeamByOfficialName(){
		testLeagueB = new SoccerLeague(2);
		
		try {
			testLeagueB.registerTeam(teamA);
			testLeagueB.registerTeam(teamB);
			
			testLeagueB.getTeamByOfficalName("Official Name");
		} catch (LeagueException e) {
			e.printStackTrace();
		}		
		
	}

	@Test(expected = LeagueException.class)
	public void testGetTeamByOfficialName_NameNotFound() throws LeagueException{
		testLeagueB = new SoccerLeague(2);
		
		try {
			testLeagueB.registerTeam(teamC);
			testLeagueB.registerTeam(teamB);
		} catch (LeagueException e) {
			e.printStackTrace();
		}	
		
		testLeagueB.getTeamByOfficalName("Official Name");	
	}
	
	@Test(expected = LeagueException.class)
	public void testPlayMatch_OffSeason() throws LeagueException{
		testLeagueB = new SoccerLeague(2);
		
		try {
			testLeagueB.registerTeam(teamA);
			testLeagueB.registerTeam(teamB);
		} catch (LeagueException e) {
			e.printStackTrace();
		}	
		testLeagueB.playMatch("Official Name", 2, "officialName", 1);
	}
	
	@Test(expected = LeagueException.class)
	public void testPlayMatch_SameTeams() throws LeagueException{
		testLeagueB = new SoccerLeague(2);
		
		try {
			testLeagueB.registerTeam(teamA);
			testLeagueB.registerTeam(teamB);
			
			testLeagueB.startNewSeason();
		} catch (LeagueException e) {
			e.printStackTrace();
		}	
		testLeagueB.playMatch("Official Name", 2, "Official Name", 1);
	}
	
	@Test(expected = LeagueException.class)
	public void testPlayMatch_TeamsNotInLeague() throws LeagueException{
		testLeagueB = new SoccerLeague(2);
		
		try {
			testLeagueB.registerTeam(teamA);
			testLeagueB.registerTeam(teamB);
			
			testLeagueB.startNewSeason();
		} catch (LeagueException e) {
			e.printStackTrace();
		}	
		testLeagueB.playMatch("Official Name", 2, "oName", 1);
	}
	
	@Test
	public void testPlayMatch(){
		testLeagueB = new SoccerLeague(2);
		
		try {
			testLeagueB.registerTeam(teamA);
			testLeagueB.registerTeam(teamB);
			testLeagueB.startNewSeason();
			
			testLeagueB.playMatch("Official Name", 2, "officialName", 1);
		} catch (LeagueException e) {
			e.printStackTrace();
		}	
	}
	
	@Test(expected = LeagueException.class)
	public void testGetTopTeam_NotEnoughTeams() throws LeagueException{
		testLeagueB = new SoccerLeague(2);
		
		testLeagueB.getTopTeam();
	}
	
	@Test
	public void testGetTopTeam(){
		testLeagueB = new SoccerLeague(2);
		
		try {
			testLeagueB.registerTeam(teamA);
			testLeagueB.registerTeam(teamB);
			testLeagueB.startNewSeason();
			
			testLeagueB.playMatch("Official Name", 2, "officialName", 1);
			
			assertEquals(teamA, testLeagueB.getTopTeam());
		} catch (LeagueException e) {
			e.printStackTrace();
		}	
		
	}
	
	@Test(expected = LeagueException.class)
	public void testGetBottomTeam_NotEnoughTeams() throws LeagueException{
		testLeagueB = new SoccerLeague(2);
		
		testLeagueB.getBottomTeam();
	}
	
	@Test
	public void testGetBottomTeam(){
		testLeagueB = new SoccerLeague(2);
		
		try {
			testLeagueB.registerTeam(teamA);
			testLeagueB.registerTeam(teamB);
			testLeagueB.startNewSeason();
			
			testLeagueB.playMatch("Official Name", 2, "officialName", 1);
			
			assertEquals(teamB, testLeagueB.getBottomTeam());
		} catch (LeagueException e) {
			e.printStackTrace();
		}	
		
	}
	
	
	@Test
	public void testSortTeams(){
		testLeagueB = new SoccerLeague(2);
		
		try {
			testLeagueB.registerTeam(teamA);
			testLeagueB.registerTeam(teamB);
			testLeagueB.startNewSeason();
			
			testLeagueB.playMatch("Official Name", 2, "officialName", 10);
			
			testLeagueB.sortTeams();
			
			assertEquals(testLeagueB.getTopTeam(), teamB);
		} catch (LeagueException e) {
			e.printStackTrace();
		}	
		
	}
	
	@Test
	public void testContainsTeam(){
		testLeagueB = new SoccerLeague(2);
		
		try {
			testLeagueB.registerTeam(teamA);
			testLeagueB.registerTeam(teamB);
		} catch (LeagueException e) {
			e.printStackTrace();
		}	
		
		assertTrue(testLeagueB.containsTeam("Official Name"));
	}
}



