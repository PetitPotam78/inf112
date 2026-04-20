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
 * Implementation of ISocialNetwork managing members, books and films.
 */
public class SocialNetwork implements ISocialNetwork {

	/** Registered members of the social network. */
	private List<Member> members = new LinkedList<Member>();

	/** Books added to the social network. */
	private List<Book> books = new LinkedList<Book>();

	@Override
	public int nbMembers() {
		return members.size();
	}

	@Override
	public int nbFilms() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int nbBooks() {
		return books.size();
	}

	public void loginCheck(String login, String password) throws BadEntryException {
		if (login == null || login.trim().length() < 1)
			throw new BadEntryException("invalid login");
		if (password == null || password.trim().length() < 4)
			throw new BadEntryException("invalid password");
	}

	public void bookCheck(String title, String kind, String author, int nbPages) throws BadEntryException{
		if (title == null || title.trim().length() < 1)
			throw new BadEntryException("invalid title");
		if (kind == null)
			throw new BadEntryException("invalid kind");
		if (author == null)
			throw new BadEntryException("invalid author");
		if (nbPages <= 0)
			throw new BadEntryException("invalid number of pages");
	}

	/**
	 * Adds a new member after validating login, password and profile,
	 * and checking no member with the same login already exists.
	 */
	@Override
	public void addMember(String login, String password, String profile)
			throws BadEntryException, MemberAlreadyExistsException {
		// Checking login
		loginCheck(login, password);	
		if (profile == null)
			throw new BadEntryException("invalid profil");
		// Reject duplicate logins (case-insensitive)
		for (Member m : members) {
			if (login.trim().equalsIgnoreCase(m.getLogin().trim()))
				throw new MemberAlreadyExistsException();
		}
		members.add(new Member(login, password, profile));
	}

	@Override
	public void addItemFilm(String login, String password, String title, String kind, String director, String scenarist, int duration)
			throws BadEntryException, NotMemberException,
			ItemFilmAlreadyExistsException {
		// TODO Auto-generated method stub

	}

	/**
	 * Adds a new book after validating all parameters, authenticating the member,
	 * and checking no book with the same title already exists.
	 */
	@Override
	public void addItemBook(String login, String password, String title,
			String kind, String author, int nbPages) throws BadEntryException,
			NotMemberException, ItemBookAlreadyExistsException {

		boolean exist = false;

		loginCheck(login, password);
		bookCheck(title, kind, author, nbPages);
		// Check that the login/password pair matches a registered member
		for (Member m : members) {
			if (login.trim().equalsIgnoreCase(m.getLogin().trim())
			&& password.trim().equalsIgnoreCase(m.getPassword().trim()))
				exist = true;
		}
		if (!exist)
			throw new NotMemberException("user do not exist");
		// Reject duplicate book titles (case-insensitive)
		for (Book b : books) {
			if (title.trim().equalsIgnoreCase(b.getTitle().trim()))
				throw new ItemBookAlreadyExistsException();
		}
		books.add(new Book(title, kind, author, nbPages));
	}

	@Override
	public float reviewItemFilm(String login, String password, String title,
			float mark, String comment) throws BadEntryException,
			NotMemberException, NotItemException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float reviewItemBook(String login, String password, String title,
			float mark, String comment) throws BadEntryException,
			NotMemberException, NotItemException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public LinkedList<String> consultItems(String title)
			throws BadEntryException {
		return new LinkedList<String>();
	}

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
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
