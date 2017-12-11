package eu.lestard.bloggie.data;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class Comment extends BaseEntity {

    @Lob
    private String text;

    @ManyToOne
    private Article article;

    /**
     * Optional.
     * A comment can have a known author (a registered user of the blog instance).
     * In this case the reference to this author is stored here.
     */
    @ManyToOne
    private Author author;

    /**
     * Optional.
     * A comment can be from a guest (someone not registered in the blog instance).
     * Guests have to enter their name as string.
     */
    private String guestAuthor;


    Comment() {
    }

    private Comment(Article article, String text) {
        this.article = article;
        this.text = text;

        if(!article.getComments().contains(this)) {
            article.addComment(this);
        }
    }

    public Comment(Article article, String text, Author author) {
        this(article, text);
        this.author = author;

        if(!author.getComments().contains(this)) {
            author.addComment(this);
        }
    }

    public Comment(Article article, String text, String guestAuthor) {
        this(article, text);
        this.guestAuthor = guestAuthor;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Author getAuthor() {
        return author;
    }

    public String getGuestAuthor() {
        return guestAuthor;
    }

    public Article getArticle() {
        return article;
    }
}
