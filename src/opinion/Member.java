package opinion;

/**
 * Represents a member registered in the social network.
 *
 * <p>
 * A {@code Member} is the authoring entity of the network: only registered
 * members can add items to the catalog and post reviews. Each member is
 * uniquely identified by their {@link #login}.
 * </p>
 *
 * <p>
 * <b>Equality semantics:</b> identity comparison between members is performed
 * via {@link #hasSameLoginAs(String)} and is <em>case-insensitive</em> after
 * trimming surrounding whitespace. So {@code "alice"}, {@code "ALICE"} and
 * {@code " Alice "} all designate the same member.
 * </p>
 *
 * <p>
 * <b>Immutability:</b> in this version, {@code Member}'s fields are never
 * modified after construction. They are not declared {@code final} purely for
 * historical reasons.
 * </p>
 *
 * <p>
 * <b>Visibility:</b> the class is package-private — only the {@code opinion}
 * package needs to manipulate {@code Member} instances directly; callers go
 * through {@link SocialNetwork}.
 * </p>
 *
 * <p>
 * <b>Attributes:</b>
 * <ul>
 *   <li>{@link #login} — unique identifier of the member;</li>
 *   <li>{@link #password} — credential used for authentication;</li>
 *   <li>{@link #profile} — free-text description of the member.</li>
 * </ul>
 * </p>
 */
class Member {

	/** Unique identifier of the member, never {@code null}. */
	private String login;

	/** Password of the member, never {@code null}. */
	private String password;

	/** Free-text profile description, never {@code null} (may be empty). */
	private String profile;

	/**
	 * Creates a new member with its basic information.
	 *
	 * <p>
	 * Parameters are <em>not</em> validated here: callers (typically
	 * {@link SocialNetwork#addMember}) must ensure they meet the network's
	 * format constraints.
	 * </p>
	 *
	 * @param login    the member's login.
	 * @param password the member's password.
	 * @param profile  the member's profile description.
	 */
	Member(String login, String password, String profile) {
		// Trust the caller to have validated the inputs.
		this.login = login;
		this.password = password;
		this.profile = profile;
	}

	/** @return the member's login. */
	String getLogin() {
		return login;
	}

	/** @return the member's password. */
	String getPassword() {
		return password;
	}

	/** @return the member's profile description. */
	String getProfile() {
		return profile;
	}

	/**
	 * Tells whether this member shares the given login.
	 *
	 * <p>
	 * Comparison ignores case and surrounding whitespace, so {@code "alice"},
	 * {@code "ALICE"} and {@code " Alice "} are considered the same login.
	 * </p>
	 *
	 * @param login the login to compare against.
	 * @return {@code true} if the logins match.
	 */
	boolean hasSameLoginAs(String login) {
		// Case-insensitive, whitespace-tolerant comparison.
		return this.login.trim().equalsIgnoreCase(login.trim());
	}

	/**
	 * Tells whether the given credentials identify this member.
	 *
	 * <p>
	 * Both login and password are compared ignoring case and surrounding
	 * whitespace, mirroring {@link #hasSameLoginAs(String)}.
	 * </p>
	 *
	 * @param login    the login to check.
	 * @param password the password to check.
	 * @return {@code true} if both login and password match this member.
	 */
	boolean hasSameCredentials(String login, String password) {
		// Reuse the login comparison, then check the password the same way.
		return hasSameLoginAs(login)
				&& this.password.trim().equalsIgnoreCase(password.trim());
	}

}
