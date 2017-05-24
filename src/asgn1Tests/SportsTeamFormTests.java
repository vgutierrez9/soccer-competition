package asgn1Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn1SoccerCompetition.SportsTeamForm;
import asgn1SportsUtils.WLD;

/**
 * A set of JUnit tests for the asgn1SoccerCompetition.SoccerTeamForm class
 *
 * @author Vanessa Gutierrez
 *
 */
public class SportsTeamFormTests {
	private SportsTeamForm testForm;
	
	//Construct SportsTeamForm object to use in tests
	@Before
	public void constructForm(){
		testForm = new SportsTeamForm();
	}
	
	@Test
	public void testToString_NoGames(){
		assertEquals("-----", testForm.toString());
	}
	
	@Test
	public void testAdd_WIN_ResultToForm(){
		testForm.addResultToForm(WLD.valueOf("WIN"));
		assertEquals("W----", testForm.toString());
	}
	
	@Test
	public void testAdd_LOSS_ResultToForm(){
		testForm.addResultToForm(WLD.valueOf("LOSS"));
		assertEquals("L----", testForm.toString());
	}
	
	@Test
	public void testAdd_DRAW_ResultToForm(){
		testForm.addResultToForm(WLD.valueOf("DRAW"));
		assertEquals("D----", testForm.toString());
	}
	
	@Test
	public void testAddResultToForm_FiveGames(){
		testForm.addResultToForm(WLD.valueOf("WIN"));
		testForm.addResultToForm(WLD.valueOf("DRAW"));
		testForm.addResultToForm(WLD.valueOf("DRAW"));
		testForm.addResultToForm(WLD.valueOf("DRAW"));
		testForm.addResultToForm(WLD.valueOf("LOSS"));
		assertEquals("LDDDW", testForm.toString());
	}
	
	@Test
	public void testAddResultToForm_OverFiveGames(){
		testForm.addResultToForm(WLD.valueOf("WIN"));
		testForm.addResultToForm(WLD.valueOf("DRAW"));
		testForm.addResultToForm(WLD.valueOf("DRAW"));
		testForm.addResultToForm(WLD.valueOf("DRAW"));
		testForm.addResultToForm(WLD.valueOf("LOSS"));
		testForm.addResultToForm(WLD.valueOf("WIN"));
		assertEquals("WLDDD", testForm.toString());
	}
	
	@Test
	public void testGetNumGames_StartAtZero(){
		assertEquals(testForm.getNumGames(), 0);
	}
	
	@Test
	public void testGetNumGames_LessFiveGames(){
		testForm.addResultToForm(WLD.valueOf("WIN"));
		testForm.addResultToForm(WLD.valueOf("DRAW"));
		testForm.addResultToForm(WLD.valueOf("LOSS"));
		assertEquals(3, testForm.getNumGames());
	}
	
	@Test
	public void testGetNumGames_MoreFiveGames(){
		testForm.addResultToForm(WLD.valueOf("WIN"));
		testForm.addResultToForm(WLD.valueOf("DRAW"));
		testForm.addResultToForm(WLD.valueOf("LOSS"));
		testForm.addResultToForm(WLD.valueOf("WIN"));
		testForm.addResultToForm(WLD.valueOf("DRAW"));
		testForm.addResultToForm(WLD.valueOf("LOSS"));
		assertEquals(5, testForm.getNumGames());
	}
	
	@Test
	public void testResetForm_TotalNumGames(){
		testForm.addResultToForm(WLD.valueOf("WIN"));
		testForm.addResultToForm(WLD.valueOf("DRAW"));
		testForm.addResultToForm(WLD.valueOf("LOSS"));
		testForm.addResultToForm(WLD.valueOf("WIN"));
		testForm.addResultToForm(WLD.valueOf("DRAW"));
		testForm.addResultToForm(WLD.valueOf("LOSS"));
		
		testForm.resetForm();
		assertEquals(0, testForm.getNumGames());
	}

	@Test
	public void testResetForm_MatchResults(){
		testForm.addResultToForm(WLD.valueOf("WIN"));
		testForm.addResultToForm(WLD.valueOf("DRAW"));
		testForm.addResultToForm(WLD.valueOf("LOSS"));
		testForm.addResultToForm(WLD.valueOf("WIN"));
		testForm.addResultToForm(WLD.valueOf("DRAW"));
		testForm.addResultToForm(WLD.valueOf("LOSS"));
		
		testForm.resetForm();
		assertEquals("-----", testForm.toString());
	}
}

