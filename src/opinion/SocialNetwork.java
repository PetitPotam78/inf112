package opinion;

import java.util.LinkedList;

import exceptions.BadEntryException;
import exceptions.ItemBookAlreadyExistsException;
import exceptions.ItemFilmAlreadyExistsException;
import exceptions.MemberAlreadyExistsException;
import exceptions.NotItemException;
import exceptions.NotMemberException;

/**
 * Skeleton for the SocialNetwork
 * 
 */
public class SocialNetwork implements ISocialNetwork {

	private LinkedList<Member> members = new LinkedList<Member>();

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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addMember(String login, String password, String profile)
			throws BadEntryException, MemberAlreadyExistsException {
		if (login == null || login.trim().length() < 1)
			throw new BadEntryException("login invalide");
		if (password == null || password.trim().length() < 4)
			throw new BadEntryException("password invalide");
		if (profile == null)
			throw new BadEntryException("profile invalide");
		for (Member m : members) {
			if (login.trim().equalsIgnoreCase(m.getLogin().trim()))
				throw new MemberAlreadyExistsException();
		}
		members.add(new Member(login, password, profile));
	}

	@Override
	public void addItemFilm(String login, String password, String title,
			String kind, String director, String scenarist, int duration)
			throws BadEntryException, NotMemberException,
			ItemFilmAlreadyExistsException {
		// TODO Auto-generated method stub

	}

	@Override
	public void addItemBook(String login, String password, String title,
			String kind, String author, int nbPages) throws BadEntryException,
			NotMemberException, ItemBookAlreadyExistsException {
		// TODO Auto-generated method stub

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
				+ nbFilms() + " film(s), " + nbBooks() + " livre(s)]\n";
		s += "Membres : ";
		for (Member m : members) {
			s += m.getLogin() + " ";
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
