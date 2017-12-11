package net.stremo.graphqljpa.entities;

import javax.persistence.*;

@Entity
@Table(name = "ratings")
public class Rating extends BaseEntity {

    private int stars;

    @Lob
    private String comment;

    @ManyToOne(fetch = FetchType.EAGER)
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;


    public Rating () {

    }
    public Rating(int stars, Customer customer, Product product) {
        this.stars = stars;
        this.customer = customer;
        this.product = product;
    }

    public Rating(int stars, String comment, Customer customer, Product product) {
        this(stars, customer, product);
        this.comment = comment;

    }


    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
