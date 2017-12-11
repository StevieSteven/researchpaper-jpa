package eu.lestard.bloggie.graphql;

import eu.lestard.bloggie.data.Article;
import eu.lestard.bloggie.data.ArticleRepository;
import eu.lestard.bloggie.data.Author;
import eu.lestard.bloggie.data.AuthorRepository;
import eu.lestard.bloggie.data.Comment;
import eu.lestard.bloggie.data.CommentRepository;
import graphql.schema.*;

import graphql.schema.GraphQLSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

import static graphql.Scalars.GraphQLID;
import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLArgument.newArgument;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;

@Service
public class Mutation {

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private CommentRepository commentRepository;

	public void addMutation(GraphQLSchema.Builder schemaBuilder, EntityManager entityManager) {
		GraphQLObjectType mutation = newObject()
				.name("Mutation")
				.field(createAddAuthorMutation())
				.field(createAddCommentForAuthorMutation())
				.build();

		schemaBuilder.mutation(mutation);
	}

	private GraphQLFieldDefinition createAddAuthorMutation() {
		return newFieldDefinition()
				.name("addAuthor")
				.type(new GraphQLTypeReference("Author"))
				.argument(newArgument()
						.name("username")
						.type(new GraphQLNonNull(GraphQLString))
						.build())
				.dataFetcher(env -> {
					String username = env.getArgument("username");
					Author author = new Author(username);

					return authorRepository.save(author);
				}).build();
	}


	private GraphQLFieldDefinition createAddCommentForAuthorMutation() {
		return newFieldDefinition()
				.name("addCommentForAuthor")
				.type(new GraphQLTypeReference("Comment"))
				.argument(newArgument()
					.name("articleId")
						.type(new GraphQLNonNull(GraphQLID))
				)
				.argument(newArgument()
						.name("authorId")
						.type(new GraphQLNonNull(GraphQLID))
				)
				.argument(newArgument()
						.name("text")
						.type(new GraphQLNonNull(GraphQLString))
				)
				.dataFetcher(env -> {
					final String articleId = env.getArgument("articleId");
					final String authorId = env.getArgument("authorId");
					final String text = env.getArgument("text");

					final Article article = articleRepository.findOne(articleId);

					if(article == null) {
						throw new IllegalArgumentException("Article with id=[" + articleId + "] not found");
					}

					final Author author = authorRepository.findOne(authorId);

					if(author == null) {
						throw new IllegalArgumentException("Author with id=[" + authorId + "] not found");
					}


					final Comment comment = new Comment(article, text, author);

					return commentRepository.save(comment);
				}).build();
	}


}
