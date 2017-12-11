package net.stremo.graphqljpa.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

    private String name;


    @OneToOne(fetch= FetchType.EAGER)
    private Category parent;

    @ManyToMany
    @JoinTable(
            name="products_categories",
            joinColumns=@JoinColumn(name="categories_id", referencedColumnName="ID"),
            inverseJoinColumns=@JoinColumn(name="product_id", referencedColumnName="ID"))
    private final List<Product> products = new ArrayList<>();

    public Category () {

    }


    public Category(String name) {
        this.name = name;
    }

    public Category(String name, Category parent) {
        this.name = name;
        this.parent= parent;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }


    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

}
