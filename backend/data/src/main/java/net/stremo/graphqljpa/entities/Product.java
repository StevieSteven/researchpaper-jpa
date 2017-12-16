package net.stremo.graphqljpa.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="products")
public class Product extends BaseEntity{

    private String name;
    private Float price;
    private  int deliveryTime;

    @Lob
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

    @OneToMany(mappedBy = "product")
    private final List<Rating> ratings = new ArrayList<>();


    @OneToMany(mappedBy = "product")
    private final List<ShoppingcardElement> shoppingcardElements = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private final List<OrderItem> orderElements = new ArrayList<>();

    public Product () {

    }

    public Product(String name, Float price, int deliveryTime, String description) {
        this.name = name;
        this.price = price;
        this.deliveryTime = deliveryTime;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<OrderItem> getOrderElements() {
        return orderElements;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void addRating (Rating rating) {
        ratings.add(rating);
    }

    public List<ShoppingcardElement> getShoppingcardElements() {
        return shoppingcardElements;
    }

    public void addShoppingcardElement (ShoppingcardElement shoppingcardElement) {
        shoppingcardElements.add(shoppingcardElement);
    }

}
