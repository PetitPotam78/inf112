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
 * Implementation of ISocialNetwork that manages members, books and films.
 *
 *
 * Responsabilities of this class :
 *   - manage members (adding, checking for duplicates)
 *   - manage books (adding, checking for duplicates)
 *   - validate all incomming parameters before any operation
 */
public class SocialNetwork implements ISocialNetwork {

	// we use constants to avoid "magic numbers" in the code (rule P13)
	// this way if we want to change the min length of a login or password,
	// we only have to change it in one place
	private static final int MIN_LOGIN_LENGTH = 1;
	private static final int MIN_TITLE_LENGTH = 1;
	private static final int MIN_PASSWORD_LENGTH = 4;

	/** List of members registred in the social network, never null */
	private List<Member> members = new LinkedList<Member>();

	/** List of books added to the social network, never null */
	private List<Book> books = new LinkedList<Book>();

	@Override
	public int nbMembers() {
		return members.size();
	}

	@Override
	public int nbFilms() {
		// TODO : films are not implementd yet
		return 0;
	}

	@Override
	public int nbBooks() {
		return books.size();
	}

	/**
	 * Checks that the login and password respect the basic rules.
	 *
	 * We put this in a seperate method to avoid repeating the same code
	 * in addMember and addItemBook (rule P8 - write a piece of code only once).
	 *
	 * @param login    the login to check, must have at least 1 non-space character
	 * @param password the password to check, must have at least 4 non-space characters
	 * @throws BadEntryException if login or password is invalid
	 */
	public void loginCheck(String login, String password) throws BadEntryException {
		// login must exist and have at least one real character (not just spaces)
		if (login == null || login.trim().length() < MIN_LOGIN_LENGTH) {
			throw new BadEntryException("invalid login");
		}
		// password must have at least 4 characters, spaces at the start/end dont count
		if (password == null || password.trim().length() < MIN_PASSWORD_LENGTH) {
			throw new BadEntryException("invalid password");
		}
	}

	/**
	 * Checks that the book informations are complete and valid.
	 *
	 * Same idea as loginCheck : we group the validation here
	 * so we dont have to duplicat it in multiple places (rule P8).
	 *
	 * @param title   the book title, must have at least 1 non-space character
	 * @param kind    the book genre, cannot be null
	 * @param author  the author, cannot be null
	 * @param nbPages the number of pages, must be strictly positive
	 * @throws BadEntryException if any of the fields is invalid
	 */
	public void bookCheck(String title, String kind, String author, int nbPages) throws BadEntryException {
		if (title == null || title.trim().length() < MIN_TITLE_LENGTH) {
			throw new BadEntryException("invalid title");
		}
		if (kind == null) {
			throw new BadEntryException("invalid kind");
		}
		if (author == null) {
			throw new BadEntryException("invalid author");
		}
		// number of pages must be strictly positive, a book with 0 pages doesn't make sense
		if (nbPages <= 0) {
			throw new BadEntryException("invalid number of pages");
		}
	}

	/**
	 * Adds a new member after validating login, password and profile,
	 * and checking that no member with the same login already exists.
	 */
	@Override
	public void addMember(String login, String password, String profile)
			throws BadEntryException, MemberAlreadyExistsException {
		// first we validate the basic parameters
		loginCheck(login, password);
		if (profile == null) {
			throw new BadEntryException("invalid profil");
		}
		// we ask each member if it has the same login (delegation to Member)
		// SocialNetwork does not need to know how the comparison is done
		for (Member m : members) {
			if (m.hasSameLoginAs(login)) {
				throw new MemberAlreadyExistsException();
			}
		}
		// everything is valid, we can create and add the new member
		members.add(new Member(login, password, profile));
	}

	@Override
	public void addItemFilm(String login, String password, String title, String kind, String director,
			String scenarist, int duration)
			throws BadEntryException, NotMemberException, ItemFilmAlreadyExistsException {
		// TODO : this method is not implementd yet
	}

	/**
	 * Adds a new book after validating all parameters, authenticating the member,
	 * and checking that no book with the same title already exists.
	 */
	@Override
	public void addItemBook(String login, String password, String title,
			String kind, String author, int nbPages) throws BadEntryException,
			NotMemberException, ItemBookAlreadyExistsException {

		// validate all parameters before doing anything else
		loginCheck(login, password);
		bookCheck(title, kind, author, nbPages);

		// ask each member if the credentials match (delegation to Member)
		// authentication logic lives in Member, not here
		boolean memberFound = false;
		for (Member m : members) {
			if (m.matches(login, password)) {
				memberFound = true;
			}
		}
		if (!memberFound) {
			throw new NotMemberException("user do not exist");
		}

		// ask each book if it has the same title (delegation to Book)
		for (Book b : books) {
			if (b.hasSameTitleAs(title)) {
				throw new ItemBookAlreadyExistsException();
			}
		}

		// everything is valid, we can add the book
		books.add(new Book(title, kind, author, nbPages));
		
	}

	@Override
	public float reviewItemFilm(String login, String password, String title,
			float mark, String comment) throws BadEntryException,
			NotMemberException, NotItemException {
		// TODO : film reviews are not implementd yet
		return 0;
	}

	@Override
	public float reviewItemBook(String login, String password, String title,
			float mark, String comment) throws BadEntryException,
			NotMemberException, NotItemException {

		// validate parameters first
		loginCheck(login, password);
		if (title == null || title.trim().length() < MIN_TITLE_LENGTH) {
			throw new BadEntryException("invalid title");
		}
		if (mark < 0.0f || mark > 5.0f) {
			throw new BadEntryException("invalid mark");
		}
		if (comment == null) {
			throw new BadEntryException("invalid comment");
		}

		// authenticate the member (login AND password must match)
		Member author = null;
		for (Member m : members) {
			if (m.matches(login, password)) {
				author = m;
				break;
			}
		}
		if (author == null) {
			throw new NotMemberException("user does not exist or wrong password");
		}

		// find the book; only throw NotItem after scanning the whole list
		for (Book b : books) {
			if (b.hasSameTitleAs(title)) {
				if (b.hasSameReviewAuthor(author)) {
					b.editReview(author, mark, comment);
				} else {
					b.addReview(new Review(mark, comment, author));
				}
				return b.getMeanMark();
			}
		}
		throw new NotItemException("Book doesn't exist");
	}

	@Override
	public LinkedList<String> consultItems(String title) throws BadEntryException {
		if (title == null || title.trim().length() < MIN_TITLE_LENGTH) {
			throw new BadEntryException("Title not instanciated or item doesn't exist");
		}
		LinkedList<String> itemsList = new LinkedList<String>();
		for (Book b : books) {
			if (b.hasSameTitleAs(title)) {
				itemsList.add("Name : " + b.getTitle()
						+ "\nCategory : Book"
						+ "\nMark : " + b.getMeanMark());
			}
		}
		return itemsList;
	}

	/**
	 * Returns a summary of the social network : number of members, films, books and their names.
	 * Usefull for debuging and quickly checking the state of the network.
	 */
	@Override
	public String toString() {
		String s = "SocialNetwork : [" + nbMembers() + " membre(s), "
				+ nbFilms() + " movie(s), " + nbBooks() + " book(s)]\n";
		s += "Membres : ";
		for (Member m : members) {
			s += m.getLogin() + " ";
		}
		s += "Books : ";
		for (Book b : books) {
			s += b.getTitle() + " ";
		}
		return s;
	}

	/**
	 * Main entry point - not used directly, run SocialNetworkTest instead.
	 * @param args not used
	 */
	public static void main(String[] args) {
		// TODO : use SocialNetworkTest to run the tests
	}

}
