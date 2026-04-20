package opinion;

/**
 * Represents a book of the SocialNetwork.
 */

public class Book {

    private String title;
    private String kind;
    private String author;
    private int nbPages;

    /**
     * @param title the book's title
     * @param kind the book's kind
     * @param author the book's author
     * @param nbPages the book's pages number
     */

    Book(String title, String kind, String author, int nbPages) {
        this.title = title;
        this.kind = kind;
        this.author = author;
        this.nbPages = nbPages;
    }

    public String getTitle() {
        return this.title;
    }

    public String getKind() {
        return this.kind;
    }

    public String getAuthor() {
        return this.author;
    }

    public int getNbPages() {
        return this.nbPages;
    }
    
}
