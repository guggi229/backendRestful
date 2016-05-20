package test;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.guggi.models.App;


public class TestAppModel {

	@Test
	public void test() {
		
		/*
		 * Erster Test: Normaler Vergleich
		 * 
		 */
		App junitApp1 = new App();
		App junitApp2 = new App();
		App junitApp3 = new App();
		
		junitApp1.setAppScore(10);
		junitApp2.setAppScore(8);
		junitApp3.setAppScore(null);
		
		
		/*
		 * Test 1
		 */
		int result = junitApp1.compareTo(junitApp2);
		assertEquals( 1, result);
		
		/*
		 * Test 2 (null Pointer Test)
		 */
		
		result = junitApp3.compareTo(junitApp2);
		assertEquals( -1, result);
		
		/*
		 * Test 3 (null Pointer Test)
		 */
		
		result = junitApp2.compareTo(junitApp3);
		assertEquals( -1, result);
	}

}
