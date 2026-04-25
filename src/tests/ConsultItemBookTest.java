package tests;

import java.util.LinkedList;

import opinion.ISocialNetwork;
import opinion.SocialNetwork;

import exceptions.BadEntryException;
import exceptions.ItemBookAlreadyExistsException;
import exceptions.MemberAlreadyExistsException;
import exceptions.NotMemberException;
import exceptions.NotTestReportException;

/**
 * Tests for the SocialNetwork.<i>consultItems()</i> method, focused on books.
 *
 * @author LAHOUASSA Wissam, ZIANI
 * @version V1.0 - April 2026
 */
public class ConsultItemBookTest {

	/**
	 * Check that calling consultItems() with this title raises a BadEntry exception.
	 */
	private static int consultItemsBadEntryTest(ISocialNetwork sn, String title,
			String testId, String errorMessage) {
		try {
			sn.consultItems(title);
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
	 * Check that consultItems() returns a list whose size matches the expected one
	 * and where every entry mentions the expected title (when provided).
	 */
	private static int consultItemsOKTest(ISocialNetwork sn, String title,
			int expectedSize, String expectedSubstring, String testId) {
		try {
			LinkedList<String> result = sn.consultItems(title);
			if (result == null) {
				System.out.println("Err " + testId
						+ " : consultItems() returned null");
				return 1;
			}
			if (result.size() != expectedSize) {
				System.out.println("Err " + testId
						+ " : expected " + expectedSize
						+ " result(s) but got " + result.size());
				return 1;
			}
			if (expectedSubstring != null) {
				for (String s : result) {
					if (!s.contains(expectedSubstring)) {
						System.out.println("Err " + testId
								+ " : a returned entry does not mention '"
								+ expectedSubstring + "' : " + s);
						return 1;
					}
				}
			}
			return 0;
		} catch (Exception e) {
			System.out.println("Err " + testId + " : unexpected exception " + e);
			e.printStackTrace();
			return 1;
		}
	}

	/**
	 * <i>consultItems()</i> main test :
	 * <ul>
	 * <li>check that incorrect titles raise BadEntry exception</li>
	 * <li>check that consulting an empty network returns an empty list</li>
	 * <li>check that consulting an existing book returns at least one entry
	 * mentioning the title</li>
	 * <li>check that consulting an unregistered title returns an empty list</li>
	 * </ul>
	 *
	 * @return a summary of the performed tests
	 */
	public static TestReport test() {

		ISocialNetwork sn = new SocialNetwork();

		int nbTests = 0;
		int nbErrors = 0;

		System.out.println("Testing consultItems() for books");

		// === Test 1 : BadEntry on an empty network ===

		nbTests++;
		nbErrors += consultItemsBadEntryTest(sn, null, "1.1",
				"consultItems() doesn't reject null titles");
		nbTests++;
		nbErrors += consultItemsBadEntryTest(sn, "  ", "1.2",
				"consultItems() doesn't reject blank titles");

		// === Test 2 : consulting an empty network returns an empty list ===

		nbTests++;
		nbErrors += consultItemsOKTest(sn, "Dune", 0, null, "2.1");

		// === Set up : 2 members and 2 books ===
		try {
			sn.addMember("Alice", "alice1234", "amatrice de romans");
			sn.addMember("Bob", "bob5678", "fan de science-fiction");
			sn.addItemBook("Alice", "alice1234", "Dune", "SF",
					"Frank Herbert", 412);
			sn.addItemBook("Bob", "bob5678", "1984", "Dystopie",
					"George Orwell", 328);
		} catch (BadEntryException | MemberAlreadyExistsException
				| NotMemberException | ItemBookAlreadyExistsException e) {
			System.out.println("Unexpected error while setting up test data: " + e);
			e.printStackTrace();
		}

		// === Test 3 : consulting an existing book returns at least 1 entry ===

		nbTests++;
		try {
			LinkedList<String> result = sn.consultItems("Dune");
			if (result == null || result.isEmpty()) {
				System.out.println("Err 3.1 : consultItems(\"Dune\") returned no result");
				nbErrors++;
			} else {
				boolean found = false;
				for (String s : result) {
					if (s.contains("Dune")) {
						found = true;
						break;
					}
				}
				if (!found) {
					System.out.println("Err 3.1 : consultItems(\"Dune\") didn't mention 'Dune' in any entry");
					nbErrors++;
				}
			}
		} catch (Exception e) {
			System.out.println("Err 3.1 : unexpected exception " + e);
			e.printStackTrace();
			nbErrors++;
		}

		// === Test 4 : the result must mention the score of the item ===

		nbTests++;
		try {
			LinkedList<String> result = sn.consultItems("Dune");
			boolean mentionsScore = false;
			for (String s : result) {
				if (s.toLowerCase().contains("mark")
						|| s.toLowerCase().contains("score")
						|| s.toLowerCase().contains("note")) {
					mentionsScore = true;
					break;
				}
			}
			if (!mentionsScore) {
				System.out.println("Err 4.1 : consultItems(\"Dune\") result doesn't mention any score/mark");
				nbErrors++;
			}
		} catch (Exception e) {
			System.out.println("Err 4.1 : unexpected exception " + e);
			e.printStackTrace();
			nbErrors++;
		}

		// === Test 5 : consulting a non-registered title returns an empty list ===
		// (per spec : the result must contain items "matching the searched name")

		nbTests++;
		nbErrors += consultItemsOKTest(sn, "Unknown Title", 0, null, "5.1");

		// Display final state of 'sn'
		System.out.println("Final state of the social network : " + sn);

		// Print a summary of the tests and return test results
		try {
			TestReport tr = new TestReport(nbTests, nbErrors);
			System.out.println("ConsultItemBookTest : " + tr);
			return tr;
		} catch (NotTestReportException e) {
			System.out.println("Unexpected error in ConsultItemBookTest test code - Can't return valuable test results");
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
