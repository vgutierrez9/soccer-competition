package asgn1Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn1Exceptions.TeamException;
import asgn1SoccerCompetition.SoccerTeam;
import asgn1SoccerCompetition.SportsTeamForm;



/**
 * A set of JUnit tests for the asgn1SoccerCompetition.SoccerLeage class
 *
 * @author Vanessa Gutierrez
 *
 */
public class SoccerTeamTests {
	private String teamOName, teamNName;
	private SoccerTeam testTeamA, testTeamB, testTeamC;
	
	@Before
	public void constructTeamA(){
		teamOName = "Official Name";
		teamNName = "Nick Name";
		try {
			testTeamA = new SoccerTeam(teamOName, teamNName);
		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
	
	@Before
	public void constructTeamC(){
		try {
			testTeamC = new SoccerTeam("official_Name", "nick_Name");
		} catch (TeamException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test(expected = TeamException.class)
	public void test_constructTeam_InvalidNames() throws TeamException{
		testTeamB = new SoccerTeam("", "");
	}
	
	@Test
	public void testGetOfficialName(){
		assertEquals(testTeamA.getOfficialName(), "Official Name");
	}
	
	@Test
	public void testGetNickName(){
		assertEquals(testTeamA.getNickName(), "Nick Name");
	}
	
	@Test(expected = TeamException.class)
	public void testPlayMatch_invalidLessGoalsFor() throws TeamException{
		testTeamA.playMatch(-1, 1);
	}
	
	@Test(expected = TeamException.class)
	public void testPlayMatch_invalidLessGoalsAgainst() throws TeamException{
		testTeamA.playMatch(1, -1);
	}
	
	@Test(expected = TeamException.class)
	public void testPlayMatch_invalidMoreGoalsFor() throws TeamException{
		testTeamA.playMatch(21, 1);
	}
	
	@Test(expected = TeamException.class)
	public void testPlayMatch_invalidMoreGoalsAgainst() throws TeamException{
		testTeamA.playMatch(1, 21);
	}
	
	@Test
	public void testPlayMatch_ValidGoals() {
		try {
			testTeamA.playMatch(2, 3);
		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testPlayMatch_ZeroGoals() {
		try {
			testTeamA.playMatch(0, 0);
		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetGoalsScored(){
		assertEquals(0, testTeamA.getGoalsScoredSeason());
		
		try {
			testTeamA.playMatch(5, 1);
		} catch (TeamException e) {
			e.printStackTrace();
		}
		
		assertEquals(5, testTeamA.getGoalsScoredSeason());
	}
	
	@Test
	public void testGetGoalsConceded(){
		assertEquals(0, testTeamA.getGoalsConcededSeason());
		
		try {
			testTeamA.playMatch(5, 1);
		} catch (TeamException e) {
			e.printStackTrace();
		}
		
		assertEquals(1, testTeamA.getGoalsConcededSeason());
	}
	
	@Test
	public void testGetMatchesWon(){
		assertEquals(0, testTeamA.getMatchesWon());
		
		try {
			testTeamA.playMatch(5, 1);
			testTeamA.playMatch(3, 1);
			
		} catch (TeamException e) {
			e.printStackTrace();
		}
		
		assertEquals(2, testTeamA.getMatchesWon());
	}
	
	@Test
	public void testGetMatchesLost(){
		assertEquals(0, testTeamA.getMatchesLost());
		
		try {
			testTeamA.playMatch(2, 5);
			testTeamA.playMatch(1, 3);
			testTeamA.playMatch(2, 5);
			testTeamA.playMatch(1, 3);
		} catch (TeamException e) {
			e.printStackTrace();
		}
		
		assertEquals(4, testTeamA.getMatchesLost());
	}
	
	@Test
	public void testGetMatchesDrawn(){
		assertEquals(0, testTeamA.getMatchesDrawn());
		
		try {
			testTeamA.playMatch(2, 2);
		} catch (TeamException e) {
			e.printStackTrace();
		}
		
		assertEquals(1, testTeamA.getMatchesDrawn());
	}	
	
	@Test
	public void testGetCompetitionPoints_Win(){
		assertEquals(0, testTeamA.getCompetitionPoints());
		
		try {
			testTeamA.playMatch(5, 1);
		} catch (TeamException e) {
			e.printStackTrace();
		}
		
		assertEquals(3, testTeamA.getCompetitionPoints());
	}

	@Test
	public void testGetCompetitionPoints_Lose(){
		assertEquals(0, testTeamA.getCompetitionPoints());
		
		try {
			testTeamA.playMatch(0, 1);
		} catch (TeamException e) {
			e.printStackTrace();
		}
		
		assertEquals(0, testTeamA.getCompetitionPoints());
	}
	
	@Test
	public void testGetCompetitionPoints_Draw(){
		assertEquals(0, testTeamA.getCompetitionPoints());
		
		try {
			testTeamA.playMatch(1, 1);
		} catch (TeamException e) {
			e.printStackTrace();
		}
		
		assertEquals(1, testTeamA.getCompetitionPoints());
	}
	
	@Test
	public void testGoalDifference(){
		assertEquals(0, testTeamA.getGoalDifference());
		
		try {
			testTeamA.playMatch(5, 1);
			testTeamA.playMatch(2, 1);
			testTeamA.playMatch(3, 6);
		} catch (TeamException e) {
			e.printStackTrace();
		}
		
		assertEquals(2, testTeamA.getGoalDifference());
	}
	
	@Test
	public void testGetFormString(){
		assertEquals("-----", testTeamA.getFormString());
		
		try {
			testTeamA.playMatch(5, 1);
			testTeamA.playMatch(2, 1);
			testTeamA.playMatch(3, 6);
		} catch (TeamException e) {
			e.printStackTrace();
		}

		assertEquals("LWW--", testTeamA.getFormString());		
	}
	
	@Test
	public void testCompareTo_Positive(){
		try {
			testTeamA.playMatch(2, 1);
			testTeamC.playMatch(6, 1);
		} catch (TeamException e) {
			e.printStackTrace();
		}
		
		assertTrue(testTeamA.compareTo(testTeamC) > 0);
	}
	
	
	@Test
	public void testCompareTo_Negative(){
		try {
			testTeamA.playMatch(7, 1);
			testTeamC.playMatch(1, 1);
		} catch (TeamException e) {
			e.printStackTrace();
		}
		
		assertTrue(testTeamA.compareTo(testTeamC) < 0);
	}
	
	@Test
	public void testCompareTo_Zero(){
		try {
			testTeamB = new SoccerTeam("Official Name", "Nick Name");
		} catch (TeamException e1) {
			e1.printStackTrace();
		}
		try {
			testTeamA.playMatch(1, 1);
			testTeamB.playMatch(1, 1);
		} catch (TeamException e) {
			e.printStackTrace();
		}
		
		assertTrue(testTeamA.compareTo(testTeamB) == 0);
	}
	
	@Test
	public void testCompareTo_SecondaryComparisonPositive(){
		try {
			testTeamA.playMatch(2, 1);
			testTeamC.playMatch(2, 1);
			testTeamA.playMatch(2, 1);
			testTeamC.playMatch(6, 1);
		} catch (TeamException e) {
			e.printStackTrace();
		}
		
		assertTrue(testTeamA.compareTo(testTeamC) > 0);
	}

	@Test
	public void testCompareTo_SecondaryComparisonNegative(){
		try {
			testTeamA.playMatch(2, 1);
			testTeamC.playMatch(2, 1);
			testTeamA.playMatch(2, 1);
			testTeamC.playMatch(6, 1);
		} catch (TeamException e) {
			e.printStackTrace();
		}
		
		assertTrue(testTeamC.compareTo(testTeamA) < 0);
	}
	
	
	@Test
	public void testCompareTo_TertiaryComparisonNegative(){
		try {
			testTeamA.playMatch(2, 1);
			testTeamC.playMatch(2, 1);
		} catch (TeamException e) {
			e.printStackTrace();
		}
		
		assertTrue(testTeamA.compareTo(testTeamC) < 0);
	}
	
	@Test
	public void testCompareTo_TertiaryComparisoPositive(){
		try {
			testTeamA.playMatch(2, 1);
			testTeamC.playMatch(2, 1);
		} catch (TeamException e) {
			e.printStackTrace();
		}
		
		assertTrue(testTeamC.compareTo(testTeamA) > 0);
	}

	
	@Test
	public void testResetStats(){
		assertEquals(0, testTeamA.getGoalsScoredSeason());
		assertEquals(0, testTeamA.getGoalsConcededSeason());
		assertEquals(0, testTeamA.getMatchesWon());
		assertEquals(0, testTeamA.getMatchesLost());
		assertEquals(0, testTeamA.getMatchesDrawn());
		assertEquals(0, testTeamA.getCompetitionPoints());
		assertEquals("-----", testTeamA.getFormString());
	}
}
