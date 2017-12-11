package eu.lestard.bloggie.data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Entity
public class Article extends BaseEntity implements ShortArticleProjection {

    private String title;

    /**
     * A user-defined identifier that is used in the URL to route to the article.
     */
    private String permalink;

    @Lob
    private String teaser;

    @Lob
    private String text;

    private LocalDateTime releaseDate;

    @ManyToMany(fetch = FetchType.EAGER)
    private final List<Author> authors = new ArrayList<>();

    @OneToMany(mappedBy = "article")
    private final List<Comment> comments = new ArrayList<>();

    Article() {
        this.releaseDate = LocalDateTime.now();
    }

    Article(String title, String teaser, String text) {
        this();
        this.title = title;
        this.permalink = title.toLowerCase().replace(" ", "-").replace(".", "").replace(",","");
        this.teaser = teaser;
        this.text = text;
    }

    public Article(String title, String teaser, String text, Author author) {
        this(title, teaser, text);

        addAuthors(author);
    }

    public Article(String title, String teaser, String text, List<Author> authors) {
        this(title, teaser, text);

        authors.forEach(this::addAuthors);
    }

    public void addAuthors(Author... newAuthors) {
        Collections.addAll(this.authors, newAuthors);

        Arrays.stream(newAuthors).forEach(author -> {
            if (!author.getArticles().contains(this)) {
                author.addArticles(this);
            }
        });
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTeaser() {
        return teaser;
    }

    public void setTeaser(String teaser) {
        this.teaser = teaser;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDateTime releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<Author> getAuthors() {
        return Collections.unmodifiableList(authors);
    }

    public List<Comment> getComments() {
        return Collections.unmodifiableList(comments);
    }

    void addComment(Comment comment) {
        this.comments.add(comment);
    }

    @Override
    public String toString() {
        return "Article{" +
                "id='" + getId() + "'," +
                "title='" + getTitle() + "'}";
    }
}
