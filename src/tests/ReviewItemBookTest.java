package tests;

import opinion.ISocialNetwork;
import opinion.SocialNetwork;

import exceptions.BadEntryException;
import exceptions.ItemBookAlreadyExistsException;
import exceptions.MemberAlreadyExistsException;
import exceptions.NotItemException;
import exceptions.NotMemberException;
import exceptions.NotTestReportException;

/**
 * Tests for the SocialNetwork.<i>reviewItemBook()</i> method.
 *
 * @author LAHOUASSA Wissam, ZIANI
 * @version V1.0 - April 2026
 */
public class ReviewItemBookTest {

	/**
	 * Check that trying to review this book raises a BadEntry exception.
	 */
	private static int reviewItemBookBadEntryTest(ISocialNetwork sn, String login,
			String password, String title, float mark, String comment,
			String testId, String errorMessage) {
		try {
			sn.reviewItemBook(login, password, title, mark, comment);
			System.out.println("Err " + testId + " : " + errorMessage);
			return 1;
		} catch (BadEntryException e) {
			return 0;
		} catch (Exception e) {
			System.out.println("Err " + testId + " : unexpected exception. " + e);
			e.printStackTrace();
			return 1;
		}
	}

	/**
	 * Check that trying to review this book raises a NotMember exception.
	 */
	private static int reviewItemBookNotMemberTest(ISocialNetwork sn, String login,
			String password, String title, float mark, String comment,
			String testId, String errorMessage) {
		try {
			sn.reviewItemBook(login, password, title, mark, comment);
			System.out.println("Err " + testId + " : " + errorMessage);
			return 1;
		} catch (NotMemberException e) {
			return 0;
		} catch (Exception e) {
			System.out.println("Err " + testId + " : unexpected exception. " + e);
			e.printStackTrace();
			return 1;
		}
	}

	/**
	 * Check that trying to review a non-existing book raises a NotItem exception.
	 */
	private static int reviewItemBookNotItemTest(ISocialNetwork sn, String login,
			String password, String title, float mark, String comment,
			String testId, String errorMessage) {
		try {
			sn.reviewItemBook(login, password, title, mark, comment);
			System.out.println("Err " + testId + " : " + errorMessage);
			return 1;
		} catch (NotItemException e) {
			return 0;
		} catch (Exception e) {
			System.out.println("Err " + testId + " : unexpected exception. " + e);
			e.printStackTrace();
			return 1;
		}
	}

	/**
	 * Check that this review can be (and <i>is</i>) successfully added,
	 * and that the returned mean mark matches the expected value.
	 */
	private static int reviewItemBookOKTest(ISocialNetwork sn, String login,
			String password, String title, float mark, String comment,
			float expectedMean, String testId) {
		try {
			float mean = sn.reviewItemBook(login, password, title, mark, comment);
			if (Math.abs(mean - expectedMean) > 0.001f) {
				System.out.println("Err " + testId
						+ " : expected mean mark " + expectedMean
						+ " but got " + mean);
				return 1;
			}
			return 0;
		} catch (Exception e) {
			System.out.println("Err " + testId + " : unexpected exception " + e);
			e.printStackTrace();
			return 1;
		}
	}

	/**
	 * <i>reviewItemBook()</i> main test :
	 * <ul>
	 * <li>check that incorrect parameters raise BadEntry exception</li>
	 * <li>check that unregistered/wrong credentials raise NotMember exception</li>
	 * <li>check that unknown book titles raise NotItem exception</li>
	 * <li>check that a valid review is added and the returned mean mark is correct</li>
	 * <li>check that a second review by the same member replaces the previous one</li>
	 * </ul>
	 *
	 * @return a summary of the performed tests
	 */
	public static TestReport test() {

		ISocialNetwork sn = new SocialNetwork();

		int nbTests = 0;
		int nbErrors = 0;

		System.out.println("Testing reviewItemBook()");

		// Set up : 2 members and 1 book
		try {
			sn.addMember("Alice", "alice1234", "amatrice de romans");
			sn.addMember("Bob", "bob5678", "fan de science-fiction");
			sn.addItemBook("Alice", "alice1234", "Dune", "SF",
					"Frank Herbert", 412);
		} catch (BadEntryException | MemberAlreadyExistsException
				| NotMemberException | ItemBookAlreadyExistsException e) {
			System.out.println("Unexpected error while setting up test data: " + e);
			e.printStackTrace();
		}

		// === Test 1 : BadEntry exceptions ===

		nbTests++;
		nbErrors += reviewItemBookBadEntryTest(sn, null, "alice1234", "Dune",
				3.0f, "good", "1.1",
				"reviewItemBook() doesn't reject null logins");
		nbTests++;
		nbErrors += reviewItemBookBadEntryTest(sn, " ", "alice1234", "Dune",
				3.0f, "good", "1.2",
				"reviewItemBook() doesn't reject blank logins");
		nbTests++;
		nbErrors += reviewItemBookBadEntryTest(sn, "Alice", null, "Dune",
				3.0f, "good", "1.3",
				"reviewItemBook() doesn't reject null passwords");
		nbTests++;
		nbErrors += reviewItemBookBadEntryTest(sn, "Alice", "  a ", "Dune",
				3.0f, "good", "1.4",
				"reviewItemBook() doesn't reject passwords shorter than 4 characters (ignoring blanks)");
		nbTests++;
		nbErrors += reviewItemBookBadEntryTest(sn, "Alice", "alice1234", null,
				3.0f, "good", "1.5",
				"reviewItemBook() doesn't reject null titles");
		nbTests++;
		nbErrors += reviewItemBookBadEntryTest(sn, "Alice", "alice1234", "  ",
				3.0f, "good", "1.6",
				"reviewItemBook() doesn't reject blank titles");
		nbTests++;
		nbErrors += reviewItemBookBadEntryTest(sn, "Alice", "alice1234", "Dune",
				-0.5f, "good", "1.7",
				"reviewItemBook() doesn't reject negative marks");
		nbTests++;
		nbErrors += reviewItemBookBadEntryTest(sn, "Alice", "alice1234", "Dune",
				5.5f, "good", "1.8",
				"reviewItemBook() doesn't reject marks greater than 5.0");

		// === Test 2 : NotMember exceptions ===

		nbTests++;
		nbErrors += reviewItemBookNotMemberTest(sn, "Unknown", "unkn5678", "Dune",
				3.0f, "good", "2.1",
				"reviewItemBook() doesn't reject an unregistered login");
		nbTests++;
		nbErrors += reviewItemBookNotMemberTest(sn, "Alice", "wrongpass", "Dune",
				3.0f, "good", "2.2",
				"reviewItemBook() doesn't reject a wrong password for a registered member");

		// === Test 3 : NotItem exceptions ===

		nbTests++;
		nbErrors += reviewItemBookNotItemTest(sn, "Alice", "alice1234",
				"UnknownBook", 3.0f, "good", "3.1",
				"reviewItemBook() doesn't reject an unregistered title");

		// === Test 4 : Successful reviews ===

		// Alice reviews Dune with 4.0 -> mean = 4.0
		nbTests++;
		nbErrors += reviewItemBookOKTest(sn, "Alice", "alice1234", "Dune",
				4.0f, "great book", 4.0f, "4.1");

		// Bob reviews Dune with 2.0 -> mean = (4.0 + 2.0) / 2 = 3.0
		nbTests++;
		nbErrors += reviewItemBookOKTest(sn, "Bob", "bob5678", "Dune",
				2.0f, "not for me", 3.0f, "4.2");

		// Alice replaces her own review with 3.0 -> mean = (3.0 + 2.0) / 2 = 2.5
		nbTests++;
		nbErrors += reviewItemBookOKTest(sn, "Alice", "alice1234", "Dune",
				3.0f, "changed my mind", 2.5f, "4.3");

		// Display final state of 'sn'
		System.out.println("Final state of the social network : " + sn);

		// Print a summary of the tests and return test results
		try {
			TestReport tr = new TestReport(nbTests, nbErrors);
			System.out.println("ReviewItemBookTest : " + tr);
			return tr;
		} catch (NotTestReportException e) {
			System.out.println("Unexpected error in ReviewItemBookTest test code - Can't return valuable test results");
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
