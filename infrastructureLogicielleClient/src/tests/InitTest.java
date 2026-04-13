package tests;

import exceptions.NotTestReportException;
import opinion.ISocialNetwork;
import opinion.SocialNetwork;


/** 
 * Tests for the <i>SocialNetwork()</i> constructor.
 * @author B. Prou, E Cousin, GO
 * @version V2.0 - 2011-2018
 */

public class InitTest {



	/**
	 * Main test for the <i>SocialNetwork()</i> constructor.
	 * @return a summary of the performed tests
	 */
	public static TestReport test(){
		int nbTests = 0; // total number of performed tests 
		int nbErrors = 0; // total number of failed tests

		
		System.out.println("Testing brand new Social Networks...  ");
	
		
		try {

			// a fresh SocialNetwork should not contain any member, book or film
			ISocialNetwork sn = new SocialNetwork();
			nbTests++;
			if (sn.nbMembers()!= 0) {
				nbErrors++;
				System.out.println("Err 0.1 : non-zero number of members in a newly instanciated Social Network");	
			}
			nbTests++;
			if (sn.nbBooks() != 0) {
				nbErrors++;
				System.out.println("Err 0.2 : non-zero number of books in a newly instanciated Social Network");
			}
			nbTests++;
			if (sn.nbFilms() != 0) {
				nbErrors++;
				System.out.println("Err 0.3 : non-zero number of films in a newly instanciated Social Network");
			}

			
			// Show the Social Network, just in case the tester could detect a problem
			System.out.println("Here is the newly created SocialNetwork :");
			System.out.println(sn);
			
		}
		catch (Exception e) {
			System.out.println("Err 0.4 : Unexpected exception : " + e);
			e.printStackTrace();
			System.exit(1);
		}
		// Print a summary of the tests and return test results
		try{
			TestReport tr = new TestReport(nbTests, nbErrors);	
			System.out.println("InitTest : " + tr);
			return tr;	
		}
		catch (NotTestReportException e){ //This shouldn't happen
			System.out.println("Unexpected error in InitTest test code - Can't return valuable test results");
			return null;
			}
	}
	
	/**
	 * Launches test()
	 * @param args not used
	 */
	public static void main(String[] args) {
		test();
	}
	

	
}