package eu.lestard.bloggie.data;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends PagingAndSortingRepository<Article, String> {

    Article findByPermalink(@Param("permalink") String permalink);

}
