package net.stremo.graphqljpa.entities;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {


    private String prename;
    private String surname;
    private String email;

    //    @OneToMany(fetch = FetchType.EAGER)
    @OneToMany(mappedBy = "customer")
    private final List<Address> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "customer")
    private final List<Rating> ratings = new ArrayList<>();

    @OneToOne(mappedBy = "customer")
    private Shoppingcard shoppingcard;

    @OneToMany(mappedBy = "customer")
    private final List<Order> orders = new ArrayList<>();
//    private Order order;


    public Customer() {

    }

    public Customer(String prename, String surname, String email) {
        this.prename = prename;
        this.surname = surname;
        this.email = email;
    }


    public String getPrename() {
        return prename;
    }

    public void setPrename(String prename) {
        this.prename = prename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void addAddress(Address address) {
        addresses.add(address);
        address.setCustomer(this);
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void addRating(Rating rating) {
        ratings.add(rating);
        rating.setCustomer(this);
    }


    public Shoppingcard getShoppingcard() {
        return shoppingcard;
    }

    public void setShoppingcard(Shoppingcard shoppingcard) {
        this.shoppingcard = shoppingcard;
    }


    public List<Order> getOrders() {
        System.out.println(orders.size() + " elemente in getter");
        return orders;
    }

    public void addOrder(Order order) {
//        System.out.println("in addOrder: " + order.getId());
        orders.add(order);
        order.setCustomer(this);
    }

//
//    public Order getOrder() {
//        return order;
//    }
//
//    public void setOrder(Order order) {
//        this.order = order;
//    }
}
