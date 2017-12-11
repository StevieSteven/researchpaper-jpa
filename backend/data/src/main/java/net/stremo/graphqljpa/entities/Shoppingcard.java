package net.stremo.graphqljpa.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "shoppingcards")
public class Shoppingcard extends BaseEntity {


    @OneToOne(fetch = FetchType.EAGER)
    private Customer customer;

    @OneToMany(mappedBy = "shoppingcard")
    private final List<ShoppingcardElement> products = new ArrayList<>();

    public Shoppingcard () {

    }

    public Shoppingcard(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<ShoppingcardElement> getProducts() {
        return products;
    }

    public void addProduct (ShoppingcardElement product) {
        products.add(product);
    }
}
