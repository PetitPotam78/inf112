package opinion;

/**
 * Represents a member registred in the social network.
 *
 *
 * Attributes :
 *   - login    : unique identifier of the member
 *   - password : used to authenticate the member
 *   - profile  : free text describing the member
 */
class Member {

	/** Unique identifier of the member, never null */
	private String login;

	/** Password of the member, never null */
	private String password;

	/** Free text describing the member's profile, never null */
	private String profile;

	/**
	 * Creates a new member with its basic informations.
	 * Parameters must already be validated before calling this constructor.
	 *
	 * @param login    the member's login
	 * @param password the member's password
	 * @param profile  the member's profile description
	 */
	Member(String login, String password, String profile) {
		this.login = login;
		this.password = password;
		this.profile = profile;
	}

	/** @return the member's login */
	String getLogin() {
		return login;
	}

	/** @return the member's password */
	String getPassword() {
		return password;
	}

	/** @return the member's profile */
	String getProfile() {
		return profile;
	}

	/**
	 * Tells if this member has the same login as the one given.
	 * Comparison ignores case and leading/trailing spaces, so that
	 * "alice", "ALICE" and " Alice " are considered the same login.
	 *
	 * @param otherLogin the login to compare to
	 * @return true if the logins match
	 */
	boolean hasSameLoginAs(String otherLogin) {
		return this.login.trim().equalsIgnoreCase(otherLogin.trim());
	}

	/**
	 * Tells if the given credentials match this member's login and password.
	 * Both login and password are compared ignoring case and surrounding spaces.
	 *
	 * @param otherLogin    the login to check
	 * @param otherPassword the password to check
	 * @return true if the credentials identify this member
	 */
	boolean matches(String otherLogin, String otherPassword) {
		return hasSameLoginAs(otherLogin)
				&& this.password.trim().equalsIgnoreCase(otherPassword.trim());
	}

}
