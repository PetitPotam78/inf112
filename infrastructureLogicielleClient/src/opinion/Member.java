package opinion;

/**
 * Represents a member of the SocialNetwork.
 */
class Member {

	private String login;
	private String password;
	private String profile;

	/**
	 * @param login   the member's login
	 * @param password the member's password
	 * @param profile  the member's profile description
	 */
	Member(String login, String password, String profile) {
		this.login = login;
		this.password = password;
		this.profile = profile;
	}

	String getLogin() {
		return login;
	}

	String getPassword() {
		return password;
	}

	String getProfile() {
		return profile;
	}
}
