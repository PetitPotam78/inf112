package tests;



/** 
 * This class launches a test suite for the SocialNetwork
 * @author B. Prou, GO
 * @version V2.0 - April 2018
 * 
 */
public class SocialNetworkTest {

	/**
	 * @param args not used
	 */
	public static void main(String[] args) {

		try {
			TestReport testSuiteReport = new TestReport(0,0);
			TestReport tr;
			tr = InitTest.test();
			testSuiteReport.add(tr);
			System.out.println("\n\n **********************************************************************************************\n");
			//
			tr = AddMemberTest.test();
			testSuiteReport.add(tr);
			System.out.println("\n\n **********************************************************************************************\n");
			//
			// TODO : calls to other tests have to be added from here
			//
			
			
			// End of the test suite : give some feedback to the tester
			System.out.println("Global tests results :   \n" + testSuiteReport);
		}
		catch (Exception e) {System.out.println("ERROR : Some exception was throw unexpectedly");}
		
	}

}
