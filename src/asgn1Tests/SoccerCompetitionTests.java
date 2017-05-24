package asgn1Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn1Exceptions.CompetitionException;
import asgn1Exceptions.LeagueException;
import asgn1Exceptions.TeamException;
import asgn1SoccerCompetition.SoccerCompetition;
import asgn1SoccerCompetition.SoccerLeague;
import asgn1SoccerCompetition.SoccerTeam;

/**
 * A set of JUnit tests for the asgn1SoccerCompetition.SoccerCompetition class
 *
 * @author Vanessa Gutierrez
 *
 */
public class SoccerCompetitionTests {
	private SoccerCompetition testComp, testComp1;
	private SoccerTeam teamA, teamB, teamC, teamD, teamE, teamF;
	
	@Before
	public void constructCompetition(){
		testComp = new SoccerCompetition("Competition!", 3, 2);
		
		try {
			teamA = new SoccerTeam("Team A", "A");
			teamB = new SoccerTeam("Team B", "B");
			teamC = new SoccerTeam("Team C", "C");
			teamD = new SoccerTeam("Team D", "D");
			teamE = new SoccerTeam("Team E", "E");
			teamF = new SoccerTeam("Team F", "F");

			testComp.getLeague(0).registerTeam(teamA);
			testComp.getLeague(0).registerTeam(teamB);
			testComp.getLeague(1).registerTeam(teamC);
			testComp.getLeague(1).registerTeam(teamD);
			testComp.getLeague(2).registerTeam(teamE);
			testComp.getLeague(2).registerTeam(teamF);
		} catch (CompetitionException e) {
			e.printStackTrace();
		} catch (LeagueException e) {
			e.printStackTrace();
		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = CompetitionException.class)
	public void testGetLeague_InvalidLeagueOver() throws CompetitionException{
		testComp.getLeague(3);
	}
	
	@Test(expected = CompetitionException.class)
	public void testGetLeague_InvalidLeagueUnder() throws CompetitionException{
		testComp.getLeague(-1);
	}
	
	@Test
	public void testGetLeague() throws CompetitionException{
		testComp.getLeague(0);
	}
	
	@Test
	public void testStartSeason(){
		testComp.startSeason();
		try {
			assertTrue(!testComp.getLeague(1).isOffSeason());
			assertTrue(!testComp.getLeague(0).isOffSeason());
			assertTrue(!testComp.getLeague(2).isOffSeason());
		} catch (CompetitionException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testEndSeason_OffandOnSeason(){
		try {
			assertTrue(testComp.getLeague(1).isOffSeason());
			assertTrue(testComp.getLeague(0).isOffSeason());
			assertTrue(testComp.getLeague(2).isOffSeason());
		} catch (CompetitionException e) {
			e.printStackTrace();
		}
		
		testComp.startSeason();
		
		try {
			assertEquals(false, testComp.getLeague(1).isOffSeason());
			assertEquals(false, testComp.getLeague(0).isOffSeason());
			assertEquals(false, testComp.getLeague(2).isOffSeason());
		} catch (CompetitionException e) {
			e.printStackTrace();
		}
		
		testComp.endSeason();
		try {
			assertTrue(testComp.getLeague(1).isOffSeason());
			assertTrue(testComp.getLeague(0).isOffSeason());
			assertTrue(testComp.getLeague(2).isOffSeason());
		} catch (CompetitionException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testEndSeason_OneLeaguePromoteandRelegate(){
		testComp1 = new SoccerCompetition("Soccer Competition 1", 1, 3);
		
		try {
			testComp1.getLeague(0).registerTeam(teamA);
			testComp1.getLeague(0).registerTeam(teamB);
			testComp1.getLeague(0).registerTeam(teamC);
			
		} catch (LeagueException | CompetitionException e) {
			e.printStackTrace();
		}
		
		testComp1.startSeason();
		
		try {
			testComp1.getLeague(0).playMatch("Team B", 1, "Team C", 3);
			testComp1.getLeague(0).playMatch("Team B", 3, "Team A", 1);
		} catch (LeagueException | CompetitionException e) {
			e.printStackTrace();
		}
		
		testComp1.endSeason();
		
		try {
			assertEquals(teamA, testComp.getLeague(0).getBottomTeam());
			assertEquals(teamB, testComp.getLeague(0).getTopTeam());
		} catch (LeagueException | CompetitionException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testEndSeason_PromoteandRelegate(){
		testComp.startSeason();
		try {
			testComp.getLeague(0).playMatch("Team A", 2, "Team B", 6);
			testComp.getLeague(1).playMatch("Team C", 2, "Team D", 3);
			testComp.getLeague(2).playMatch("Team E", 10, "Team F", 1);
		} catch (LeagueException e) {
			e.printStackTrace();
		} catch (CompetitionException e) {
			e.printStackTrace();
		}
		
		testComp.endSeason();
		try {
			assertTrue(testComp.getLeague(1).isOffSeason());
			assertTrue(testComp.getLeague(0).isOffSeason());
			assertTrue(testComp.getLeague(2).isOffSeason());
		} catch (CompetitionException e) {
			e.printStackTrace();
		}
		
		try {
			assertEquals(teamB, testComp.getLeague(0).getTopTeam());
			assertEquals(teamD, testComp.getLeague(0).getBottomTeam());
			assertEquals(teamE, testComp.getLeague(1).getTopTeam());
			assertEquals(teamA, testComp.getLeague(1).getBottomTeam());
			assertEquals(teamC, testComp.getLeague(2).getTopTeam());
			assertEquals(teamF, testComp.getLeague(2).getBottomTeam());
			
		} catch (LeagueException | CompetitionException e) {
			e.printStackTrace();
		}
	}
	
}

