package tests;

import opinion.ISocialNetwork;
import opinion.SocialNetwork;

import exceptions.BadEntryException;
import exceptions.ItemBookAlreadyExistsException;
import exceptions.MemberAlreadyExistsException;
import exceptions.NotMemberException;
import exceptions.NotTestReportException;

/**
 * Tests for the SocialNetwork.<i>addItemBook()</i> method.
 * Also verifies <i>nbBooks()</i> and <i>toString()</i>.
 *
 * @author LAHOUASSA Wissam, ZIANI
 * @version V1.0 - April 2026
 */
public class AddItemBookTest {

	/**
	 * Check that trying to add this book raises a BadEntry exception and does
	 * not change the content of the <i>ISocialNetwork</i>.
	 * If OK returns 0, otherwise displays an error message and returns 1.
	 */
	private static int addItemBookBadEntryTest(ISocialNetwork sn, String login,
			String password, String title, String kind, String author,
			int nbPages, String testId, String errorMessage) {

		int nbBooks = sn.nbBooks();
		try {
			sn.addItemBook(login, password, title, kind, author, nbPages);
			System.out.println("Err " + testId + " : " + errorMessage);
			return 1;
		} catch (BadEntryException e) {
			if (sn.nbBooks() != nbBooks) {
				System.out.println("Err " + testId
						+ " : BadEntry was thrown but the number of books was changed");
				return 1;
			} else
				return 0;
		} catch (Exception e) {
			System.out.println("Err " + testId + " : unexpected exception. " + e);
			e.printStackTrace();
			return 1;
		}
	}

	/**
	 * Check that trying to add this book raises a NotMember exception and does
	 * not change the content of the <i>ISocialNetwork</i>.
	 * If OK returns 0, otherwise displays an error message and returns 1.
	 */
	private static int addItemBookNotMemberTest(ISocialNetwork sn, String login,
			String password, String title, String kind, String author,
			int nbPages, String testId, String errorMessage) {

		int nbBooks = sn.nbBooks();
		try {
			sn.addItemBook(login, password, title, kind, author, nbPages);
			System.out.println("Err " + testId + " : " + errorMessage);
			return 1;
		} catch (NotMemberException e) {
			if (sn.nbBooks() != nbBooks) {
				System.out.println("Err " + testId
						+ " : NotMember was thrown but the number of books was changed");
				return 1;
			} else
				return 0;
		} catch (Exception e) {
			System.out.println("Err " + testId + " : unexpected exception. " + e);
			e.printStackTrace();
			return 1;
		}
	}

	/**
	 * Check that trying to add this book raises an ItemBookAlreadyExists
	 * exception and does not change the content of the <i>ISocialNetwork</i>.
	 * If OK returns 0, otherwise displays an error message and returns 1.
	 */
	private static int addItemBookAlreadyExistsTest(ISocialNetwork sn,
			String login, String password, String title, String kind,
			String author, int nbPages, String testId, String errorMessage) {

		int nbBooks = sn.nbBooks();
		try {
			sn.addItemBook(login, password, title, kind, author, nbPages);
			System.out.println("Err " + testId + " : " + errorMessage);
			return 1;
		} catch (ItemBookAlreadyExistsException e) {
			if (sn.nbBooks() != nbBooks) {
				System.out.println("Err " + testId
						+ " : ItemBookAlreadyExists was thrown but the number of books was changed");
				return 1;
			} else
				return 0;
		} catch (Exception e) {
			System.out.println("Err " + testId + " : unexpected exception. " + e);
			e.printStackTrace();
			return 1;
		}
	}

	/**
	 * Check that this book can be (and <i>is</i>) successfully added to the
	 * <i>ISocialNetwork</i>.
	 * If OK returns 0, otherwise displays an error message and returns 1.
	 */
	private static int addItemBookOKTest(ISocialNetwork sn, String login,
			String password, String title, String kind, String author,
			int nbPages, String testId) {

		int nbBooks = sn.nbBooks();
		try {
			sn.addItemBook(login, password, title, kind, author, nbPages);
			if (sn.nbBooks() != nbBooks + 1) {
				System.out.println("Err " + testId
						+ " : the number of books (" + nbBooks
						+ ") was not incremented");
				return 1;
			} else
				return 0;
		} catch (Exception e) {
			System.out.println("Err " + testId + " : unexpected exception " + e);
			e.printStackTrace();
			return 1;
		}
	}

	/**
	 * <i>addItemBook()</i> main test :
	 * <ul>
	 * <li>check that incorrect parameters raise BadEntry exception</li>
	 * <li>check that unregistered/wrong credentials raise NotMember exception</li>
	 * <li>check that books can be added by registered members</li>
	 * <li>check that duplicate titles raise ItemBookAlreadyExists exception</li>
	 * <li>check <i>nbBooks()</i> and <i>toString()</i> consistency</li>
	 * </ul>
	 *
	 * @return a summary of the performed tests
	 */
	public static TestReport test() {

		ISocialNetwork sn = new SocialNetwork();

		int nbBooks = sn.nbBooks();   // should be 0
		int nbFilms = sn.nbFilms();   // should be 0

		int nbTests = 0;
		int nbErrors = 0;

		System.out.println("Testing addItemBook()");

		// Register two members required for book-addition tests
		try {
			sn.addMember("Alice", "alice1234", "amatrice de romans");
			sn.addMember("Bob", "bob5678", "fan de science-fiction");
		} catch (BadEntryException | MemberAlreadyExistsException e) {
			System.out.println("Unexpected error while setting up test members: " + e);
			e.printStackTrace();
		}

		// === Test 1 : BadEntry exceptions ===

		nbTests++;
		nbErrors += addItemBookBadEntryTest(sn, null, "alice1234", "Dune", "SF",
				"Frank Herbert", 412, "1.1",
				"addItemBook() doesn't reject null logins");
		nbTests++;
		nbErrors += addItemBookBadEntryTest(sn, " ", "alice1234", "Dune", "SF",
				"Frank Herbert", 412, "1.2",
				"addItemBook() doesn't reject logins that contain only spaces");
		nbTests++;
		nbErrors += addItemBookBadEntryTest(sn, "Alice", null, "Dune", "SF",
				"Frank Herbert", 412, "1.3",
				"addItemBook() doesn't reject null passwords");
		nbTests++;
		nbErrors += addItemBookBadEntryTest(sn, "Alice", "  ab ", "Dune", "SF",
				"Frank Herbert", 412, "1.4",
				"addItemBook() doesn't reject passwords shorter than 4 characters (ignoring blanks)");
		nbTests++;
		nbErrors += addItemBookBadEntryTest(sn, "Alice", "alice1234", null, "SF",
				"Frank Herbert", 412, "1.5",
				"addItemBook() doesn't reject null titles");
		nbTests++;
		nbErrors += addItemBookBadEntryTest(sn, "Alice", "alice1234", "  ", "SF",
				"Frank Herbert", 412, "1.6",
				"addItemBook() doesn't reject titles that contain only spaces");
		nbTests++;
		nbErrors += addItemBookBadEntryTest(sn, "Alice", "alice1234", "Dune",
				null, "Frank Herbert", 412, "1.7",
				"addItemBook() doesn't reject null kinds");
		nbTests++;
		nbErrors += addItemBookBadEntryTest(sn, "Alice", "alice1234", "Dune",
				"SF", null, 412, "1.8",
				"addItemBook() doesn't reject null authors");
		nbTests++;
		nbErrors += addItemBookBadEntryTest(sn, "Alice", "alice1234", "Dune",
				"SF", "Frank Herbert", 0, "1.9",
				"addItemBook() doesn't reject nbPages = 0");
		nbTests++;
		nbErrors += addItemBookBadEntryTest(sn, "Alice", "alice1234", "Dune",
				"SF", "Frank Herbert", -5, "1.10",
				"addItemBook() doesn't reject negative nbPages");

		// === Test 2 : NotMember exceptions ===

		nbTests++;
		nbErrors += addItemBookNotMemberTest(sn, "Unknown", "unkn5678", "Dune",
				"SF", "Frank Herbert", 412, "2.1",
				"addItemBook() doesn't reject a login that is not registered");
		nbTests++;
		nbErrors += addItemBookNotMemberTest(sn, "Alice", "wrongpass", "Dune",
				"SF", "Frank Herbert", 412, "2.2",
				"addItemBook() doesn't reject a wrong password for a registered member");

		// === Test 3 : Successful additions ===

		nbTests++;
		nbErrors += addItemBookOKTest(sn, "Alice", "alice1234", "Dune", "SF",
				"Frank Herbert", 412, "3.1");
		nbTests++;
		nbErrors += addItemBookOKTest(sn, "Bob", "bob5678", "Le Petit Prince",
				"Conte", "Antoine de Saint-Exupéry", 96, "3.2");
		nbTests++;
		nbErrors += addItemBookOKTest(sn, "Alice", "alice1234", "1984",
				"Dystopie", "George Orwell", 328, "3.3");

		// === Test 4 : ItemBookAlreadyExists exceptions ===

		nbTests++;
		nbErrors += addItemBookAlreadyExistsTest(sn, "Alice", "alice1234",
				new String("Dune"), "SF", "Frank Herbert", 412, "4.1",
				"The title of the first added book was accepted again");
		nbTests++;
		nbErrors += addItemBookAlreadyExistsTest(sn, "Alice", "alice1234",
				new String("1984"), "Dystopie", "George Orwell", 328, "4.2",
				"The title of the last added book was accepted again");
		nbTests++;
		nbErrors += addItemBookAlreadyExistsTest(sn, "Bob", "bob5678",
				new String("dUnE"), "SF", "Frank Herbert", 412, "4.3",
				"An already registered title with different case was accepted");
		nbTests++;
		nbErrors += addItemBookAlreadyExistsTest(sn, "Bob", "bob5678",
				new String(" Dune "), "SF", "Frank Herbert", 412, "4.4",
				"An already registered title with leading/trailing blanks was accepted");
		nbTests++;
		nbErrors += addItemBookAlreadyExistsTest(sn, "Bob", "bob5678",
				"Du" + "ne", "SF", "Frank Herbert", 412, "4.5",
				"A String concatenation building an already registered title was accepted");

		// === Test 5 : nbBooks() and nbFilms() consistency ===

		nbTests++;
		if (sn.nbBooks() != nbBooks + 3) {
			System.out.println("Err 5.1 : expected " + (nbBooks + 3)
					+ " books, got " + sn.nbBooks());
			nbErrors++;
		}
		nbTests++;
		if (sn.nbFilms() != nbFilms) {
			System.out.println("Err 5.2 : the number of films was unexpectedly changed by addItemBook()");
			nbErrors++;
		}

		// === Test 6 : toString() ===

		String str = sn.toString();
		nbTests++;
		if (str == null || str.isEmpty()) {
			System.out.println("Err 6.1 : toString() returned null or empty string");
			nbErrors++;
		}
		nbTests++;
		if (str != null && !str.contains("Dune")) {
			System.out.println("Err 6.2 : toString() does not mention 'Dune'");
			nbErrors++;
		}
		nbTests++;
		if (str != null && !str.contains("Le Petit Prince")) {
			System.out.println("Err 6.3 : toString() does not mention 'Le Petit Prince'");
			nbErrors++;
		}
		nbTests++;
		if (str != null && !str.contains("1984")) {
			System.out.println("Err 6.4 : toString() does not mention '1984'");
			nbErrors++;
		}

		// Display final state of 'sn'
		System.out.println("Final state of the social network : " + sn);

		// Print a summary of the tests and return test results
		try {
			TestReport tr = new TestReport(nbTests, nbErrors);
			System.out.println("AddItemBookTest : " + tr);
			return tr;
		} catch (NotTestReportException e) {
			System.out.println("Unexpected error in AddItemBookTest test code - Can't return valuable test results");
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
