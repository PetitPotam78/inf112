package opinion;

/**
 * Represents a member registred in the social network.
 *
 * This class has one single responsability : storing the identity
 * informations of a member (login, password, profile).
 * Data validation is done outside, in SocialNetwork.
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

}
