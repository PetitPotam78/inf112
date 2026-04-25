package tests;

/**
 * Launches the full test suite for the SocialNetwork.
 *
 * To add new tests, create a test class and call its test() method here,
 * just like we do for AddMemberTest and AddItemBookTest
 * (rule P14 - test step by step as you go).
 *
 * @author B. Prou, GO
 * @version V2.0 - April 2018
 */
public class SocialNetworkTest {

	/**
	 * Runs all tests and prints a global summary.
	 * @param args not used
	 */
	public static void main(String[] args) {

		try {
			// start with an empty report, we will add results as we go
			TestReport testSuiteReport = new TestReport(0, 0);
			TestReport tr;

			// test the initialisation of the social network
			tr = InitTest.test();
			testSuiteReport.add(tr);
			System.out.println("\n\n **********************************************************************************************\n");

			// test adding members
			tr = AddMemberTest.test();
			testSuiteReport.add(tr);
			System.out.println("\n\n **********************************************************************************************\n");

			// test adding books
			tr = AddItemBookTest.test();
			testSuiteReport.add(tr);
			System.out.println("\n\n **********************************************************************************************\n");

			// test reviewing books
			tr = ReviewItemBookTest.test();
			testSuiteReport.add(tr);
			System.out.println("\n\n **********************************************************************************************\n");

			// test consulting items
			tr = ConsultItemBookTest.test();
			testSuiteReport.add(tr);
			System.out.println("\n\n **********************************************************************************************\n");

			// TODO : add tests for films when they are implemented

			// print the global summary of all tests
			System.out.println("Global tests results :   \n" + testSuiteReport);
		} catch (Exception e) {
			System.out.println("ERROR : Some exception was throw unexpectedly");
		}

	}

}
