package eu.lestard.bloggie.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@BasePathAwareController
public class CustomArticleController {

    @Autowired
    private ProjectionFactory factory;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private PagedResourcesAssembler<ShortArticleProjection> assembler;

    @RequestMapping(value="/articles", method = RequestMethod.GET, produces = "application/hal+json")
    public ResponseEntity<?> getArticles(Pageable pageable) {
        Page<Article> articles = articleRepository.findAll(pageable);
        Page<ShortArticleProjection> projected = articles.map(article -> factory.createProjection(ShortArticleProjection.class, article));

        final PagedResources<Resource<ShortArticleProjection>> resources = assembler.toResource(projected);
        return ResponseEntity.ok(resources);
    }
}
