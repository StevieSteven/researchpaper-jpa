package eu.lestard.bloggie.data;

import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDateTime;

@Projection(name= "short-article", types = {Article.class})
public interface ShortArticleProjection {

    String getId();

    String getTitle();

    String getPermalink();

    String getTeaser();

    LocalDateTime getReleaseDate();

}
