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
 */
public class SocialNetwork implements ISocialNetwork {

	private static final int MIN_LOGIN_LENGTH = 1;
	private static final int MIN_TITLE_LENGTH = 1;
	private static final int MIN_PASSWORD_LENGTH = 4;

	private final List<Member> members = new LinkedList<Member>();
	private final List<Book> books = new LinkedList<Book>();

	@Override
	public int nbMembers() {
		return members.size();
	}

	@Override
	public int nbFilms() {
		return 0;
	}

	@Override
	public int nbBooks() {
		return books.size();
	}

	@Override
	public void addMember(String login, String password, String profile)
			throws BadEntryException, MemberAlreadyExistsException {
		checkCredentials(login, password);
		if (profile == null) {
			throw new BadEntryException("invalid profil");
		}

		for (Member m : members) {
			if (m.hasSameLoginAs(login)) {
				throw new MemberAlreadyExistsException();
			}
		}

		members.add(new Member(login, password, profile));
	}

	@Override
	public void addItemFilm(String login, String password, String title, String kind, String director,
			String scenarist, int duration)
			throws BadEntryException, NotMemberException, ItemFilmAlreadyExistsException {
		// TODO : not implemented yet
	}

	@Override
	public void addItemBook(String login, String password, String title,
			String kind, String author, int nbPages) throws BadEntryException,
			NotMemberException, ItemBookAlreadyExistsException {

		checkCredentials(login, password);
		checkBookFields(title, kind, author, nbPages);
		getAuthenticatedMember(login, password);

		for (Book b : books) {
			if (b.hasSameTitleAs(title)) {
				throw new ItemBookAlreadyExistsException();
			}
		}

		books.add(new Book(title, kind, author, nbPages));
	}

	@Override
	public float reviewItemFilm(String login, String password, String title,
			float mark, String comment) throws BadEntryException,
			NotMemberException, NotItemException {
		// TODO : not implemented yet
		return 0;
	}

	@Override
	public float reviewItemBook(String login, String password, String title,
			float mark, String comment) throws BadEntryException,
			NotMemberException, NotItemException {

		checkCredentials(login, password);
		checkString(title, MIN_TITLE_LENGTH, "invalid title");
		if (mark < 0.0f || mark > 5.0f) {
			throw new BadEntryException("invalid mark");
		}
		if (comment == null) {
			throw new BadEntryException("invalid comment");
		}

		Member author = getAuthenticatedMember(login, password);

		for (Book b : books) {
			if (!b.hasSameTitleAs(title)) {
				continue;
			}
			if (b.hasSameReviewAuthor(author)) {
				b.editReview(author, mark, comment);
			} else {
				b.addReview(new Review(mark, comment, author));
			}
			return b.getMeanMark();
		}
		throw new NotItemException("Book doesn't exist");
	}

	@Override
	public LinkedList<String> consultItems(String title) throws BadEntryException {
		checkString(title, MIN_TITLE_LENGTH, "Title not instanciated or item doesn't exist");

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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("SocialNetwork : [")
		  .append(nbMembers()).append(" membre(s), ")
		  .append(nbFilms()).append(" movie(s), ")
		  .append(nbBooks()).append(" book(s)]\n");

		sb.append("Membres : ");
		for (Member m : members) {
			sb.append(m.getLogin()).append(' ');
		}

		sb.append("Books : ");
		for (Book b : books) {
			sb.append(b.getTitle()).append(' ');
		}
		return sb.toString();
	}

	/**
	 * Generic validation for a non-null string with a minimum length
	 * (leading/trailing blanks ignored).
	 */
	private void checkString(String value, int minLength, String errorMessage) throws BadEntryException {
		if (value == null || value.trim().length() < minLength) {
			throw new BadEntryException(errorMessage);
		}
	}

	private void checkCredentials(String login, String password) throws BadEntryException {
		checkString(login, MIN_LOGIN_LENGTH, "invalid login");
		checkString(password, MIN_PASSWORD_LENGTH, "invalid password");
	}

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
	 * Centralised authentication: returns the matching member or throws
	 * NotMemberException if no member matches the given credentials.
	 */
	private Member getAuthenticatedMember(String login, String password) throws NotMemberException {
		for (Member m : members) {
			if (m.matches(login, password)) {
				return m;
			}
		}
		throw new NotMemberException("user does not exist or wrong password");
	}

	public static void main(String[] args) {
		// run SocialNetworkTest to execute the tests
	}

}
