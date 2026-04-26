package opinion;

import java.util.LinkedList;
import java.util.List;

import exceptions.BadEntryException;
import exceptions.ItemBookAlreadyExistsException;
import exceptions.ItemFilmAlreadyExistsException;
import exceptions.MemberAlreadyExistsException;
import exceptions.NotItemException;
import exceptions.NotMemberException;

/**
 * Concrete implementation of {@link ISocialNetwork}.
 *
 * <p>
 * A {@code SocialNetwork} represents an opinion-sharing network where members
 * can register, add items (books and films) to the catalog, and publish
 * reviews (mark + comment) about those items.
 * </p>
 *
 * <p>
 * This class centralises:
 * <ul>
 *   <li>the list of registered members ({@link #members});</li>
 *   <li>the list of referenced books ({@link #books}).</li>
 * </ul>
 * Films are not implemented yet (see {@link #addItemFilm} and
 * {@link #reviewItemFilm}).
 * </p>
 *
 * <p>
 * <b>Invariants:</b>
 * <ul>
 *   <li>Two members cannot share the same login (case-insensitive, see
 *       {@link Member#hasSameLoginAs(String)}).</li>
 *   <li>Two books cannot share the same title.</li>
 *   <li>A given member cannot publish two distinct reviews on the same book:
 *       a new review by the same author overrides the previous one.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <b>Input validation:</b> every public method validates its arguments
 * <em>before</em> any state change and throws a {@link BadEntryException} on
 * invalid input. Validation logic is factored out into private helpers
 * ({@link #checkString}, {@link #checkCredentials}, {@link #checkBookFields}).
 * </p>
 *
 * @see ISocialNetwork
 * @see Member
 * @see Book
 * @see Review
 */
public class SocialNetwork implements ISocialNetwork {

	// ------------------------------------------------------------------
	// Validation constants
	// ------------------------------------------------------------------

	/** Minimum length (after trim) of a valid login. */
	private static final int MIN_LOGIN_LENGTH = 1;

	/** Minimum length (after trim) of a valid item title. */
	private static final int MIN_TITLE_LENGTH = 1;

	/**
	 * Minimum length (after trim) of a valid password.
	 * Imposed by the network specification (at least 4 <i>significant</i>
	 * characters).
	 */
	private static final int MIN_PASSWORD_LENGTH = 4;

	// ------------------------------------------------------------------
	// Internal state
	// ------------------------------------------------------------------

	/**
	 * List of members registered on the network.
	 *
	 * <p>
	 * A {@link LinkedList} is used because all common operations (registration,
	 * lookup by login, authentication scan) are sequential traversals; random
	 * access is never needed.
	 * </p>
	 *
	 * <p>
	 * The reference is {@code final}: the list itself is never replaced, only
	 * its contents evolve.
	 * </p>
	 */
	private final List<Member> members = new LinkedList<Member>();

	/**
	 * List of books referenced on the network.
	 *
	 * <p>
	 * Same rationale as {@link #members}: {@link LinkedList} for sequential
	 * access, {@code final} to preserve the collection identity.
	 * </p>
	 */
	private final List<Book> books = new LinkedList<Book>();

	// ------------------------------------------------------------------
	// Inspection methods (collection sizes)
	// ------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 *
	 * @return the number of members currently registered on the network.
	 */
	@Override
	public int nbMembers() {
		return members.size();
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * <b>Not implemented:</b> always returns {@code 0} until film management
	 * is developed.
	 * </p>
	 *
	 * @return always {@code 0} (placeholder).
	 */
	@Override
	public int nbFilms() {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @return the number of books currently referenced on the network.
	 */
	@Override
	public int nbBooks() {
		return books.size();
	}

	// ------------------------------------------------------------------
	// Member management
	// ------------------------------------------------------------------

	/**
	 * Registers a new member on the network.
	 *
	 * <p>
	 * Validation performed (in this order):
	 * <ol>
	 *   <li>{@code login} and {@code password} via
	 *       {@link #checkCredentials(String, String)};</li>
	 *   <li>{@code profile} not {@code null} (may be empty or whitespace);</li>
	 *   <li>uniqueness of the login among already registered members.</li>
	 * </ol>
	 * </p>
	 *
	 * @param login    unique identifier of the new member.
	 * @param password password of the new member.
	 * @param profile  free-text description of the member (may be empty,
	 *                 must not be {@code null}).
	 * @throws BadEntryException            if any argument violates the
	 *                                      format constraints.
	 * @throws MemberAlreadyExistsException if a member with this login is
	 *                                      already registered (comparison via
	 *                                      {@link Member#hasSameLoginAs(String)}).
	 */
	@Override
	public void addMember(String login, String password, String profile)
			throws BadEntryException, MemberAlreadyExistsException {
		// Validate the login / password pair.
		checkCredentials(login, password);

		// Profile may be empty but must not be null.
		if (profile == null) {
			throw new BadEntryException("invalid profil");
		}

		// Reject duplicate logins (linear scan, O(n)).
		for (Member m : members) {
			if (m.hasSameLoginAs(login)) {
				throw new MemberAlreadyExistsException();
			}
		}

		// All checks passed: register the new member.
		members.add(new Member(login, password, profile));
	}

	// ------------------------------------------------------------------
	// Film management (not implemented)
	// ------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * <b>Not implemented:</b> declared by {@link ISocialNetwork} but not
	 * developed in this version. The body is intentionally empty so the class
	 * still compiles and tests unrelated to films can run.
	 * </p>
	 *
	 * @param login     login of the member requesting the addition.
	 * @param password  associated password.
	 * @param title     title of the film.
	 * @param kind      genre of the film.
	 * @param director  name of the director.
	 * @param scenarist name of the scenarist.
	 * @param duration  duration of the film, in minutes.
	 */
	@Override
	public void addItemFilm(String login, String password, String title, String kind, String director,
			String scenarist, int duration)
			throws BadEntryException, NotMemberException, ItemFilmAlreadyExistsException {
		// TODO: not implemented yet
	}

	// ------------------------------------------------------------------
	// Book management
	// ------------------------------------------------------------------

	/**
	 * Adds a new book to the network catalog.
	 *
	 * <p>
	 * The member providing the credentials must be registered, otherwise a
	 * {@link NotMemberException} is thrown. Validation order:
	 * <ol>
	 *   <li>{@code login} and {@code password} via
	 *       {@link #checkCredentials(String, String)};</li>
	 *   <li>book-specific fields via
	 *       {@link #checkBookFields(String, String, String, int)};</li>
	 *   <li>member authentication via
	 *       {@link #getAuthenticatedMember(String, String)};</li>
	 *   <li>title uniqueness in the catalog.</li>
	 * </ol>
	 * </p>
	 *
	 * @param login    login of the member adding the book.
	 * @param password password of the member.
	 * @param title    title of the book (must be unique in the catalog).
	 * @param kind     genre / category of the book.
	 * @param author   name of the book's author.
	 * @param nbPages  number of pages (strictly positive).
	 * @throws BadEntryException              if any argument is malformed.
	 * @throws NotMemberException             if no member matches the
	 *                                        credentials.
	 * @throws ItemBookAlreadyExistsException if a book with the same title is
	 *                                        already present in the catalog.
	 */
	@Override
	public void addItemBook(String login, String password, String title,
			String kind, String author, int nbPages) throws BadEntryException,
			NotMemberException, ItemBookAlreadyExistsException {

		// Format check on credentials (no authentication here yet).
		checkCredentials(login, password);

		// Validate book-specific fields.
		checkBookFields(title, kind, author, nbPages);

		// Authenticate the member; the returned reference is unused but the
		// call is kept for its side effect (NotMemberException on failure).
		getAuthenticatedMember(login, password);

		// Reject duplicate titles.
		for (Book b : books) {
			if (b.hasSameTitleAs(title)) {
				throw new ItemBookAlreadyExistsException();
			}
		}

		// All checks passed: add the book.
		books.add(new Book(title, kind, author, nbPages));
	}

	// ------------------------------------------------------------------
	// Reviews (films - not implemented)
	// ------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * <b>Not implemented:</b> returns {@code 0} until film management is
	 * developed.
	 * </p>
	 *
	 * @return always {@code 0} (placeholder).
	 */
	@Override
	public float reviewItemFilm(String login, String password, String title,
			float mark, String comment) throws BadEntryException,
			NotMemberException, NotItemException {
		// TODO: not implemented yet
		return 0;
	}

	// ------------------------------------------------------------------
	// Reviews (books)
	// ------------------------------------------------------------------

	/**
	 * Publishes or updates a member's review on a book.
	 *
	 * <p>
	 * If the member already published a review for this book, that review is
	 * overwritten (mark and comment replaced); otherwise a new review is
	 * appended. In both cases the updated mean mark is returned.
	 * </p>
	 *
	 * <p>
	 * Validation order:
	 * <ol>
	 *   <li>format of {@code login} and {@code password};</li>
	 *   <li>format of {@code title};</li>
	 *   <li>{@code mark} within {@code [0.0 ; 5.0]};</li>
	 *   <li>{@code comment} not {@code null} (may be empty);</li>
	 *   <li>member authentication.</li>
	 * </ol>
	 * </p>
	 *
	 * @param login    login of the reviewing member.
	 * @param password password of the member.
	 * @param title    title of the reviewed book.
	 * @param mark     mark to assign, between {@code 0.0f} and {@code 5.0f}
	 *                 (bounds included).
	 * @param comment  associated comment (must not be {@code null}).
	 * @return the updated arithmetic mean of the book's marks after
	 *         insertion or update.
	 * @throws BadEntryException  if any input is malformed.
	 * @throws NotMemberException if no member matches the credentials.
	 * @throws NotItemException   if no referenced book has this title.
	 */
	@Override
	public float reviewItemBook(String login, String password, String title,
			float mark, String comment) throws BadEntryException,
			NotMemberException, NotItemException {

		// Format check on credentials.
		checkCredentials(login, password);

		// Title format check.
		checkString(title, MIN_TITLE_LENGTH, "invalid title");

		// Mark must be within [0.0, 5.0].
		if (mark < 0.0f || mark > 5.0f) {
			throw new BadEntryException("invalid mark");
		}

		// Comment may be empty but must not be null.
		if (comment == null) {
			throw new BadEntryException("invalid comment");
		}

		// Authenticate; the resulting member becomes the review's author.
		Member author = getAuthenticatedMember(login, password);

		// Locate the book by title, then insert or update the review.
		for (Book b : books) {
			if (!b.hasSameTitleAs(title)) {
				continue;
			}

			// Existing review by this author -> edit; otherwise append.
			if (b.hasSameReviewAuthor(author)) {
				b.editReview(author, mark, comment);
			} else {
				b.addReview(new Review(mark, comment, author));
			}

			// Return the recomputed mean mark in either case.
			return b.getMeanMark();
		}

		// No book matched the requested title.
		throw new NotItemException("Book doesn't exist");
	}

	// ------------------------------------------------------------------
	// Consultation
	// ------------------------------------------------------------------

	/**
	 * Looks up referenced items whose title exactly matches the one given.
	 *
	 * <p>
	 * For every matching item, a formatted string is appended to the returned
	 * list, containing the title, the category ({@code Book}) and the mean
	 * mark.
	 * </p>
	 *
	 * <p>
	 * <b>Note:</b> only the book collection is inspected for now (films are
	 * not yet implemented).
	 * </p>
	 *
	 * @param title title to look up. Must satisfy {@link #MIN_TITLE_LENGTH}
	 *              after {@code trim()}.
	 * @return the (possibly empty) list of textual descriptions of matching
	 *         items.
	 * @throws BadEntryException if {@code title} is {@code null} or only
	 *                           whitespace.
	 */
	@Override
	public LinkedList<String> consultItems(String title) throws BadEntryException {
		// Title must be non-null and non-empty after trim().
		checkString(title, MIN_TITLE_LENGTH, "Title not instanciated or item doesn't exist");

		// Result list: an empty LinkedList means "no match" per the contract.
		LinkedList<String> itemsList = new LinkedList<String>();

		// Format and collect every matching book.
		for (Book b : books) {
			if (b.hasSameTitleAs(title)) {
				itemsList.add("Name : " + b.getTitle()
						+ "\nCategory : Book"
						+ "\nMark : " + b.getMeanMark());
			}
		}
		return itemsList;
	}

	// ------------------------------------------------------------------
	// Textual representation
	// ------------------------------------------------------------------

	/**
	 * Human-readable representation of the network's state.
	 *
	 * <p>
	 * The output starts with a numeric summary, then lists member logins and
	 * book titles. Mostly intended for debugging and test output.
	 * </p>
	 *
	 * @return a string describing the current state of the social network.
	 */
	@Override
	public String toString() {
		// StringBuilder avoids allocating intermediate Strings for each append.
		StringBuilder sb = new StringBuilder();

		// Header: numeric summary.
		sb.append("SocialNetwork : [")
		  .append(nbMembers()).append(" membre(s), ")
		  .append(nbFilms()).append(" movie(s), ")
		  .append(nbBooks()).append(" book(s)]\n");

		// Member section: space-separated logins.
		sb.append("Membres : ");
		for (Member m : members) {
			sb.append(m.getLogin()).append(' ');
		}

		// Book section: space-separated titles.
		sb.append("Books : ");
		for (Book b : books) {
			sb.append(b.getTitle()).append(' ');
		}
		return sb.toString();
	}

	// ------------------------------------------------------------------
	// Private helpers (validation and authentication)
	// ------------------------------------------------------------------

	/**
	 * Generic validation for a non-null string of at least {@code minLength}
	 * <em>significant</em> characters (leading/trailing whitespace ignored
	 * via {@link String#trim()}).
	 *
	 * <p>
	 * This helper is the foundation of every format check performed by the
	 * class (login, password, title, etc.).
	 * </p>
	 *
	 * @param value        string to validate.
	 * @param minLength    minimum length required after {@code trim()}.
	 * @param errorMessage message embedded in the exception on failure.
	 * @throws BadEntryException if {@code value} is {@code null} or too
	 *                           short once trimmed.
	 */
	private void checkString(String value, int minLength, String errorMessage) throws BadEntryException {
		if (value == null || value.trim().length() < minLength) {
			throw new BadEntryException(errorMessage);
		}
	}

	/**
	 * Validates the {@code (login, password)} pair format using
	 * {@link #checkString}. The members list is not consulted here: this is a
	 * purely syntactic check.
	 *
	 * @param login    login to validate.
	 * @param password password to validate.
	 * @throws BadEntryException if either string fails its minimum-length
	 *                           constraint.
	 */
	private void checkCredentials(String login, String password) throws BadEntryException {
		checkString(login, MIN_LOGIN_LENGTH, "invalid login");
		checkString(password, MIN_PASSWORD_LENGTH, "invalid password");
	}

	/**
	 * Validates the book-specific fields, in the order expected by the tests:
	 * <ol>
	 *   <li>{@code title} not empty,</li>
	 *   <li>{@code kind} not {@code null} (may be empty),</li>
	 *   <li>{@code author} not {@code null} (may be empty),</li>
	 *   <li>{@code nbPages} strictly positive.</li>
	 * </ol>
	 *
	 * @param title   book title.
	 * @param kind    book genre.
	 * @param author  book author.
	 * @param nbPages number of pages.
	 * @throws BadEntryException if any field violates its contract.
	 */
	private void checkBookFields(String title, String kind, String author, int nbPages) throws BadEntryException {
		checkString(title, MIN_TITLE_LENGTH, "invalid title");
		if (kind == null) {
			throw new BadEntryException("invalid kind");
		}
		if (author == null) {
			throw new BadEntryException("invalid author");
		}
		if (nbPages <= 0) {
			throw new BadEntryException("invalid number of pages");
		}
	}

	/**
	 * Centralised member authentication.
	 *
	 * <p>
	 * Iterates over the members list and returns the one matching the given
	 * {@code (login, password)} pair (cf.
	 * {@link Member#hasSameCredentials(String, String)}). If no member
	 * matches, a {@link NotMemberException} is thrown.
	 * </p>
	 *
	 * <p>
	 * Used by both {@link #addItemBook} and {@link #reviewItemBook}:
	 * centralising the logic here avoids duplication and ensures uniform
	 * behaviour.
	 * </p>
	 *
	 * @param login    submitted login.
	 * @param password submitted password.
	 * @return the matching {@link Member}.
	 * @throws NotMemberException if no member matches.
	 */
	private Member getAuthenticatedMember(String login, String password) throws NotMemberException {
		for (Member m : members) {
			if (m.hasSameCredentials(login, password)) {
				return m;
			}
		}
		throw new NotMemberException("user does not exist or wrong password");
	}

	// ------------------------------------------------------------------
	// Entry point
	// ------------------------------------------------------------------

	/**
	 * Empty entry point: the class is used as a library by the tests. Run
	 * {@code tests.SocialNetworkTest} (and the other classes in the
	 * {@code tests} package) to execute the functional scenarios.
	 *
	 * @param args command-line arguments (ignored).
	 */
	public static void main(String[] args) {
		// run SocialNetworkTest to execute the tests
	}

}
