package opinion;

/**
 * Represents a book in the social network.
 *
 * This class only stores the informations of a book.
 *
 * Attributes :
 *   - title   : the book title (unique identifier in the network)
 *   - kind    : the book genre (novel, SF, thriller, etc)
 *   - author  : the book author
 *   - nbPages : the number of pages (strictly positive)
 */
public class Book {

	/** Title of the book, serves as unique identifier in the network */
	private String title;

	/** Genre of the book (novel, SF, thriller...), never null */
	private String kind;

	/** Name of the author, never null */
	private String author;

	/** Number of pages, always strictly positive */
	private int nbPages;

	/**
	 * Creates a new book with all its informations.
	 * Parameters must have been validated before calling this constructor.
	 *
	 * @param title   the book title
	 * @param kind    the book genre
	 * @param author  the book author
	 * @param nbPages the number of pages
	 */
	Book(String title, String kind, String author, int nbPages) {
		this.title = title;
		this.kind = kind;
		this.author = author;
		this.nbPages = nbPages;
	}

	/** @return the book title */
	public String getTitle() {
		return this.title;
	}

	/** @return the book genre */
	public String getKind() {
		return this.kind;
	}

	/** @return the book author */
	public String getAuthor() {
		return this.author;
	}

	/** @return the number of pages */
	public int getNbPages() {
		return this.nbPages;
	}

	/**
	 * Tells if this book has the same title as the one given.
	 * Comparison ignores case and leading/trailing spaces, so that
	 * "Dune", "DUNE" and " dune " are considered the same title.
	 *
	 * @param otherTitle the title to compare to
	 * @return true if the titles match
	 */
	public boolean hasSameTitleAs(String otherTitle) {
		return this.title.trim().equalsIgnoreCase(otherTitle.trim());
	}

}
