package tests;
import exceptions.NotTestReportException;

/**
 * TestReport helps to convey information about performed tests
 * @author cousin
 *
 */
public class TestReport {
	
	private int nbTests;  // Total number of tests that were run
	private int nbErrors; // Total number of errors that were detected while running these tests
	
	/**
	 * 
	 * @param nbTests Total number of tests that were run.
	 * @param nbErrors Total number of errors that were detected while running these tests. 
	 * @throws NotTestReportException if nbTests and/or nbErrors are not complying with the following constraints :	nbTests and nbErrors should be positive, nbTests should be greater or equal to nbErrors
	 */
	public TestReport (int nbTests, int nbErrors) throws NotTestReportException {
		if ((nbTests<0) || (nbErrors <0) || (nbTests < nbErrors)) throw new NotTestReportException();
		this.nbTests = nbTests;
		this.nbErrors = nbErrors;
	}
	
	/**
	 * Get a String representation of a TestReport
	 */
	public String toString (){
		String s = "[ Nb of performed tests : " + nbTests + " / nb of detected error(s) : "+ nbErrors + " ]";
		return s;
	}
	
	/**
	 * Add to the current TestReport the results contained in an other TestReport 
	 * @param other another TestReport 
	 */
	public void add (TestReport other){
		this.nbTests += other.nbTests;
		this.nbErrors += other.nbErrors;
	}
	
	/**
	 * Two TestReport are equals if all their attributes are equals.
	 */
	public boolean equals (Object o){
		if (o instanceof TestReport){
			TestReport tr = (TestReport) o;
			return ((this.nbTests == tr.nbTests) && (this.nbErrors == tr.nbErrors));
		}
		return false;
	}

	/**
	 * This is only a (very) small auto-test for the TestReport class
	 * @param args not used
	 */
	public static void main(String[] args) {
		int nbErrors = 0;
		System.out.println("*** Launching auto-test for TestReport class ***");
		
		try {
			// basic tests
			TestReport tr1 = new TestReport(10,5);
			TestReport tr2 = new TestReport(3,0);
			tr1.add(tr2); // this should change tr1 attributes appropriately :
			if ((tr1.nbTests != 13)||(tr1.nbErrors != 5)){
				nbErrors++;
				System.out.println("Error :  TestReport.add() seems not to work appropriately");
			}		
			//
			TestReport tr3 = new TestReport(3,0);
			if (! tr3.equals(tr2)){
				nbErrors++;
				System.out.println("Autotest error : TestReport.equals() seems not to work appropriately");
			}
			//
			tr3.add(new TestReport(0,0));
			if (! tr3.equals(tr2)){
				nbErrors++;
				System.out.println("Autotest error : TestReport.add() seems not to work appropriately");
			}
			System.out.println("Here is how TestReport is converted into String :");
			System.out.println("--------------------------------");
			System.out.println("tr1 :" + tr1); 
			System.out.println("tr2 :" + tr2);
			System.out.println("--------------------------------");
		}
		catch (NotTestReportException e){
			nbErrors++;
			System.out.println ("Autotest error : NotTestReportException was thrown unexpectedly");
		}
		// check if TestReport() throws NotTestReportException for some inaccurate arguments
		try{
			TestReport tr = new TestReport(10,-5);
			// the end of this bloc shouldn't be executed
			nbErrors++;
			System.out.println ("Autotest error : NotTestReportException should have been thrown");
		}
		catch (NotTestReportException e){ // nothing to do here because it is what was awaited
		}
		
		if (nbErrors==0) {
			System.out.println("*** Auto-test was OK ***");
		}
		else {
			System.out.println("*** Errors were detected in the auto-test. The TestReport class shoudn't be used ***");
		}
		
	}

}
