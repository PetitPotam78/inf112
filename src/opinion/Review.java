package opinion;

/**
 * Represents a review posted by a {@link Member} on a {@link Book}.
 *
 * <p>
 * A review aggregates three pieces of information:
 * <ul>
 *   <li>{@link #mark} — a numerical mark in the range {@code [0.0 ; 5.0]};</li>
 *   <li>{@link #comment} — a free-text comment associated with the mark;</li>
 *   <li>{@link #author} — the {@link Member} who posted the review.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <b>Mutability:</b> {@link #mark} and {@link #comment} can be updated after
 * construction (used when a member overwrites their own review). The
 * {@link #author} on the other hand is set once at construction and never
 * changes — a review's authorship is part of its identity.
 * </p>
 *
 * <p>
 * <b>Validation:</b> field values are not validated here; callers (typically
 * {@link SocialNetwork#reviewItemBook}) are responsible for enforcing the
 * range and non-{@code null} constraints.
 * </p>
 *
 * @see Book#addReview(Review)
 * @see Book#editReview(Member, float, String)
 */
public class Review {

	/** Mark of the review, expected within {@code [0.0f ; 5.0f]}. */
	private float mark;

	/** Free-text comment associated with the mark, never {@code null}. */
	private String comment;

	/** Author of the review, set at construction and immutable thereafter. */
	private Member author;

	/**
	 * Creates a new review with its mark, comment and author.
	 *
	 * <p>
	 * Parameters are <em>not</em> validated here: callers must ensure
	 * {@code mark} is within range, {@code comment} is non-{@code null}, and
	 * {@code author} is a registered {@link Member}.
	 * </p>
	 *
	 * @param mark    the numerical mark.
	 * @param comment the textual comment.
	 * @param author  the member who posted the review.
	 */
	Review(float mark, String comment, Member author) {
		// Trust the caller to have validated the inputs.
		this.mark = mark;
		this.comment = comment;
		this.author = author;
	}

	/** @return the mark of the review. */
	public float getMark() {
		return this.mark;
	}

	/** @return the comment of the review. */
	public String getComment() {
		return this.comment;
	}

	/** @return the author of the review. */
	public Member getAuthor() {
		return this.author;
	}

	/**
	 * Updates the review's mark.
	 *
	 * <p>
	 * Used when a member overwrites their own previous review on a book.
	 * The new mark must lie within {@code [0.0f ; 5.0f]}; this method does
	 * not enforce the range.
	 * </p>
	 *
	 * @param mark the new mark to assign.
	 */
	public void setMark(float mark) {
		this.mark = mark;
	}

	/**
	 * Updates the review's comment.
	 *
	 * <p>
	 * Used when a member overwrites their own previous review on a book.
	 * The new comment must not be {@code null}; this method does not enforce
	 * that constraint.
	 * </p>
	 *
	 * @param comment the new comment to assign.
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

}
