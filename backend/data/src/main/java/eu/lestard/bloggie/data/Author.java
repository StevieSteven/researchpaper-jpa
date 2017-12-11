package eu.lestard.bloggie.data;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Entity
public class Author extends BaseEntity {

    private String name;

    @ManyToMany(mappedBy = "authors")
    private List<Article> articles = new ArrayList<>();

    @OneToMany(mappedBy = "author")
    private List<Comment> comments = new ArrayList<>();

    protected Author() {
    }

    public Author(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Article> getArticles() {
        return Collections.unmodifiableList(articles);
    }

    public void addArticles(Article... newArticles) {
        Collections.addAll(this.articles, newArticles);

        Arrays.stream(newArticles).forEach(article -> {
            if (!article.getAuthors().contains(this)) {
                article.addAuthors(this);
            }
        });
    }

    public List<Comment> getComments() {
        return Collections.unmodifiableList(comments);
    }

    void addComment(Comment comment) {
        this.comments.add(comment);
    }
}
