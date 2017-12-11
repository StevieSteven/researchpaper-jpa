package net.stremo.graphqljpa.graphql;


import graphql.schema.*;

import graphql.schema.GraphQLSchema;
import net.stremo.graphqljpa.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

import static graphql.Scalars.GraphQLID;
import static graphql.Scalars.GraphQLInt;
import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLArgument.newArgument;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;

@Service
public class Mutation {

	@Autowired
	AddressRepository addressRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	RatingReposistory ratingReposistory;

	@Autowired
	ShoppingcardRepository shoppingcardRepository;

	@Autowired
	ShoppingcardElementRepository shoppingcardElementRepository;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	OrderItemRepository orderItemRepository;

	@Autowired
	OrderStatusRepository orderStatusRepository;

	public void addMutation(GraphQLSchema.Builder schemaBuilder, EntityManager entityManager) {
		GraphQLObjectType mutation = newObject()
				.name("Mutation")
				.field(createPutProductInShoppingcardMutation())
				.field(createFinishOrderMutation())
				.field(createAddRatingMutation())
				.build();

		schemaBuilder.mutation(mutation);
	}

	private GraphQLFieldDefinition createPutProductInShoppingcardMutation() {
		return newFieldDefinition()
				.name("putProductIntoShoppingCard")
				.type(new GraphQLTypeReference("Shoppingcard"))
				.argument(GraphQLArgument.newArgument().name("productId").type(GraphQLID).build())
				.argument(GraphQLArgument.newArgument().name("count").type(GraphQLInt).build())
				.dataFetcher(environment -> {
					Long productId = Long.parseLong(environment.getArgument("productId"));
					int count = environment.getArgument("count");

					if (count < 1)
						return null;
					Product p = productRepository.findOne(productId);
					if (p == null)
						return null;
					Customer c = customerRepository.findOne(1L);
					Shoppingcard s = c.getShoppingcard();
					if (s == null) {
						s = new Shoppingcard();
						c.setShoppingcard(s);
					}
					ShoppingcardElement e = new ShoppingcardElement(count, s, p);
					shoppingcardElementRepository.save(e);
					s.addProduct(e);

					return s;
				})
				.build();
	}

	private GraphQLFieldDefinition createFinishOrderMutation() {

		return newFieldDefinition()
				.name("finishOrder")
				.type(new GraphQLTypeReference("Order"))
				.argument(GraphQLArgument.newArgument().name("addressId").type(GraphQLID).build())
				.dataFetcher(environment -> {
					Long addressId = Long.parseLong(environment.getArgument("addressId"));
					Address address = addressRepository.findOne(addressId);
					if (address == null)
						return null;
					Customer c = customerRepository.findOne(1L);
					Shoppingcard shoppingcard = c.getShoppingcard();
					if (shoppingcard == null)
						return null;

					OrderStatus orderStatus = orderStatusRepository.findOne(1L);
					Order order = new Order(address, orderStatus, c);
					orderRepository.save(order);
					shoppingcard.getProducts().forEach(shoppingcardElement -> {
						OrderItem item = new OrderItem(shoppingcardElement.getQuantity(), order, shoppingcardElement.getProduct());
						orderItemRepository.save(item);
						order.addOrderItem(item);
						shoppingcardElementRepository.delete(shoppingcardElement);

					});
					shoppingcardRepository.delete(shoppingcard);
					return order;
				})
				.build();
	}


	private GraphQLFieldDefinition createAddRatingMutation() {
		return newFieldDefinition()
				.name("addRating")
				.type(new GraphQLTypeReference("Rating"))
				.argument(GraphQLArgument.newArgument().name("productId").type(GraphQLID).build())
				.argument(GraphQLArgument.newArgument().name("stars").type(GraphQLInt).build())
				.argument(GraphQLArgument.newArgument().name("comment").type(GraphQLString).build())
				.dataFetcher(environment -> {
					Long productId = Long.parseLong(environment.getArgument("productId"));
					int stars = environment.getArgument("stars");
					String comment = environment.getArgument("comment");

					if (stars < 1)
						return null;

					Product p = productRepository.findOne(productId);
					if (p == null)
						return null;
					Customer c = customerRepository.findOne(1L);

					Rating rating = new Rating(stars, c, p);
					if(comment.length() > 0)
						rating.setComment(comment);
					ratingReposistory.save(rating);
					c.addRating(rating);
					p.addRating(rating);

					return rating;
				})
				.build();
	}


}
