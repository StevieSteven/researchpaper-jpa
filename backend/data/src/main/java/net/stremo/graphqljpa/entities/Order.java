package net.stremo.graphqljpa.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity{

    private String date ;

    @ManyToOne(fetch = FetchType.EAGER)
    private Address address;

    @OneToOne(fetch = FetchType.EAGER)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    private Customer customer;


    @OneToMany(mappedBy = "order")
    private final List<OrderItem> items = new ArrayList<>();

    public Order() {

    }

    public Order(Address address, OrderStatus status, Customer customer) {
        this.date = "1970-01-01 00:00";
        this.address = address;
        this.status = status;
        this.customer = customer;
    }

    public Order(String date, Address address, OrderStatus status, Customer customer) {
        this.date = date;
        this.address = address;
        this.status = status;
        this.customer = customer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void addProduct(Product productInOrder, int i) {
        OrderItem o = new OrderItem(i, this, productInOrder);
        items.add(o);
    }

    public void addOrderItem(OrderItem orderItem) {
        items.add(orderItem);
    }
}
