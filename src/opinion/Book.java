package opinion;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a book item in the social network.
 *
 * <p>
 * A {@code Book} aggregates the descriptive metadata of a book (title, genre,
 * author, page count) together with the {@link Review reviews} posted by
 * members. The {@link #title} acts as the unique identifier of a book within
 * the network — two books cannot share the same title (cf.
 * {@link #hasSameTitleAs(String)}).
 * </p>
 *
 * <p>
 * <b>Reviews:</b> each book owns its own list of reviews. A given member can
 * have at most <em>one</em> review per book; subsequent submissions by the
 * same author overwrite the previous mark and comment via
 * {@link #editReview(Member, float, String)}.
 * </p>
 *
 * <p>
 * <b>Mean mark:</b> {@link #getMeanMark()} computes the arithmetic mean of
 * the marks. It is only meaningful once at least one review has been posted —
 * calling it on a book without reviews would divide by zero.
 * </p>
 *
 * <p>
 * <b>Attributes:</b>
 * <ul>
 *   <li>{@link #title} — unique identifier within the network;</li>
 *   <li>{@link #kind} — genre / category (novel, SF, thriller, …);</li>
 *   <li>{@link #author} — name of the book's author;</li>
 *   <li>{@link #nbPages} — number of pages, strictly positive;</li>
 *   <li>{@link #reviews} — reviews posted by members on this book.</li>
 * </ul>
 * </p>
 */
public class Book {

	/** Title of the book; serves as unique identifier within the network. */
	private String title;

	/** Genre of the book (novel, SF, thriller, …), never {@code null}. */
	private String kind;

	/** Name of the author, never {@code null}. */
	private String author;

	/** Number of pages, always strictly positive. */
	private int nbPages;

	/**
	 * Reviews posted on this book.
	 *
	 * <p>
	 * Implemented as a {@link LinkedList} because all use sites are
	 * sequential traversals (mean computation, lookup of an existing review by
	 * author).
	 * </p>
	 */
	private List<Review> reviews;

	/**
	 * Creates a new book with all its descriptive fields.
	 *
	 * <p>
	 * Parameters are <em>not</em> validated here: callers (typically
	 * {@link SocialNetwork#addItemBook}) must ensure they meet the network's
	 * format constraints. The reviews list is initialised empty.
	 * </p>
	 *
	 * @param title   the book title.
	 * @param kind    the book genre.
	 * @param author  the book author.
	 * @param nbPages the number of pages.
	 */
	Book(String title, String kind, String author, int nbPages) {
		// Trust the caller to have validated the inputs.
		this.title = title;
		this.kind = kind;
		this.author = author;
		this.nbPages = nbPages;

		// Start with no reviews; members will populate the list later.
		this.reviews = new LinkedList<Review>();
	}

	/** @return the book title. */
	public String getTitle() {
		return this.title;
	}

	/** @return the book genre. */
	public String getKind() {
		return this.kind;
	}

	/** @return the book author. */
	public String getAuthor() {
		return this.author;
	}

	/** @return the number of pages. */
	public int getNbPages() {
		return this.nbPages;
	}

	/**
	 * Tells whether this book shares the given title.
	 *
	 * <p>
	 * Comparison ignores case and surrounding whitespace, so {@code "Dune"},
	 * {@code "DUNE"} and {@code " dune "} are considered the same title.
	 * </p>
	 *
	 * @param title the title to compare against.
	 * @return {@code true} if the titles match.
	 */
	public boolean hasSameTitleAs(String title) {
		// Case-insensitive, whitespace-tolerant comparison.
		return this.title.trim().equalsIgnoreCase(title.trim());
	}

	/**
	 * Tells whether this book already has a review by the given author.
	 *
	 * <p>
	 * Identity is checked by reference (==), which is safe here because
	 * {@link SocialNetwork} only ever passes around the unique {@link Member}
	 * instance retrieved from its members list.
	 * </p>
	 *
	 * @param author the candidate review author.
	 * @return {@code true} if a review by this author already exists.
	 */
	public boolean hasSameReviewAuthor(Member author) {
		// Linear scan: identify a review whose author is the given member.
		for (Review r : this.reviews) {
			if (r.getAuthor() == author) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Appends a new review to this book's list.
	 *
	 * <p>
	 * No duplicate-author check is performed here: callers must ensure the
	 * author has no existing review (cf. {@link #hasSameReviewAuthor(Member)})
	 * or use {@link #editReview(Member, float, String)} instead.
	 * </p>
	 *
	 * @param review the review to append.
	 */
	public void addReview(Review review) {
		reviews.add(review);
	}

	/**
	 * Updates the mark and comment of the existing review by the given author.
	 *
	 * <p>
	 * If multiple reviews happened to be authored by the same member (which
	 * the higher-level invariant forbids), all of them would be updated.
	 * If no review by this author exists, the call is a no-op.
	 * </p>
	 *
	 * @param author  the author whose review must be updated.
	 * @param mark    the new mark to assign.
	 * @param comment the new comment to assign.
	 */
	public void editReview(Member author, float mark, String comment) {
		// Locate the matching review and overwrite its mark and comment.
		for (Review r : this.reviews) {
			if (r.getAuthor() == author) {
				r.setMark(mark);
				r.setComment(comment);
			}
		}
	}

	/**
	 * Computes the arithmetic mean of the marks of every review on this book.
	 *
	 * <p>
	 * <b>Precondition:</b> at least one review must have been posted —
	 * calling this method on a book with no review divides by zero (would
	 * yield {@code NaN}).
	 * </p>
	 *
	 * @return the mean of all review marks.
	 */
	public float getMeanMark() {
		// Sum every mark and count how many reviews contributed.
		float sum = 0;
		int count = 0;
		for (Review r : this.reviews) {
			sum += r.getMark();
			count++;
		}

		// Arithmetic mean; assumes count > 0 (precondition above).
		return (sum / count);
	}

}
