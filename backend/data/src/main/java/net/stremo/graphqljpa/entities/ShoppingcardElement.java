package net.stremo.graphqljpa.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "shoppingcard_elements")
public class ShoppingcardElement extends BaseEntity {


    private int quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    private Shoppingcard shoppingcard;

    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;

    public ShoppingcardElement() {

    }


    public ShoppingcardElement(int quantity, Shoppingcard shoppingcard, Product product) {
        this.quantity = quantity;
        this.shoppingcard = shoppingcard;
        this.product = product;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Shoppingcard getShoppingcard() {
        return shoppingcard;
    }

    public void setShoppingcard(Shoppingcard shoppingcard) {
        this.shoppingcard = shoppingcard;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
