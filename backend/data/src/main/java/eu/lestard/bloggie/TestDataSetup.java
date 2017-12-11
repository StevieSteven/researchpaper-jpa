package eu.lestard.bloggie;

import com.github.javafaker.Faker;
import eu.lestard.bloggie.data.Article;
import eu.lestard.bloggie.data.ArticleRepository;
import eu.lestard.bloggie.data.Author;
import eu.lestard.bloggie.data.AuthorRepository;
import eu.lestard.bloggie.data.Comment;
import eu.lestard.bloggie.data.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class TestDataSetup {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Transactional
    public void run() {
        Faker faker = new Faker();

        BiFunction<String, Integer, String> substring = (source, maxLength) ->
                source.substring(0, Math.min(source.length(), maxLength));

        Runnable printAuthors = () -> {
            System.out.println("Authors:");
            authorRepository.findAll().forEach(author -> {
                System.out.println("\tname: " + author.getName());

                System.out.println("\tarticles: ");
                author.getArticles()
                        .forEach(article -> System.out.println("\t\t" + article.getTitle()));

                System.out.println("\tcomments:");
                author.getComments().forEach(comment -> {
                    System.out.println("\t\tarticle: " + comment.getArticle().getTitle());
                    System.out.println("\t\ttext: " + substring.apply(comment.getText(), 15) + "," + comment.getArticle().getTitle());
                    System.out.println("\n");
                });
                System.out.println("\n");
            });
        };

        Runnable printArticles = () -> {
            System.out.println("\n");
            System.out.println("Articles:");

            articleRepository.findAll().forEach(article -> {
                System.out.println("\tid: " + article.getId());
                System.out.println("\ttitle: " + article.getTitle());
                System.out.println("\tauthors: " + article.getAuthors()
                        .stream()
                        .map(Author::getName)
                        .collect(Collectors.joining(",")));
                System.out.println("\tcomments:\n" + article.getComments()
                        .stream()
                        .map(comment -> {
                            StringBuilder result = new StringBuilder();
                            if(comment.getAuthor() == null) {
                                result.append("\t\tguest:").append(comment.getGuestAuthor());
                            } else {
                                result.append("\t\tauthor:").append(comment.getAuthor().getName());
                            }

                            result.append("\n\t\ttext:").append(substring.apply(comment.getText(), 15));
                            result.append("\n\n");
                            return result.toString();
                        }).collect(Collectors.joining()));
                System.out.println("\n");
            });
        };


        final Author hugo = authorRepository.save(new Author("Hugo"));
        final Author luise = authorRepository.save(new Author("Luise"));
        final Author marie = authorRepository.save(new Author("Marie"));


        Supplier<String> createText = () -> faker.lorem().paragraphs(20).stream().collect(Collectors.joining("\n\n"));


        final Article article1 = articleRepository.save(new Article(faker.book().title(), faker.lorem().paragraph(), createText.get(), hugo));
        final Article article2 = articleRepository.save(new Article(faker.book().title(), faker.lorem().paragraph(), createText.get(), Arrays.asList(marie, luise)));
        final Article article3 = articleRepository.save(new Article(faker.book().title(), faker.lorem().paragraph(), createText.get(), Arrays.asList(marie, luise, hugo)));
        final Article article4 = articleRepository.save(new Article(faker.book().title(), faker.lorem().paragraph(), createText.get(), Arrays.asList(luise, hugo)));
        final Article article5 = articleRepository.save(new Article(faker.book().title(), faker.lorem().paragraph(), createText.get(), luise));
        final Article article6 = articleRepository.save(new Article(faker.book().title(), faker.lorem().paragraph(), createText.get(), marie));




        article6.addAuthors(luise);
        articleRepository.save(article6);


        hugo.addArticles(article5);
        authorRepository.save(hugo);
        articleRepository.save(article5);



        commentRepository.save(new Comment(article3, faker.lorem().paragraph(1), "Some guest"));
        commentRepository.save(new Comment(article3, faker.lorem().paragraph(1), "Some guest"));
        commentRepository.save(new Comment(article1, faker.lorem().paragraph(1), luise));
        commentRepository.save(new Comment(article2, faker.lorem().paragraph(1), hugo));


        printAuthors.run();
        printArticles.run();
    }

}
