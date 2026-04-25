package opinion;

public class Review {
    
    private float mark;

    private String comment;

    private Member author;

    Review (float mark, String comment, Member author) {
        this.mark = mark;
        this.comment = comment;
        this.author = author;
    }

    public float getMark() {
        return this.mark;
    }

    public String getComment() {
        return this.comment;
    }

    public Member getAuthor() {
        return this.author;
    }

    public void setMark(float mark) {
        this.mark = mark;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
