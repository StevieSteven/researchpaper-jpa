package net.stremo.graphqljpa.entities;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "order_status")
public class OrderStatus extends BaseEntity {

    private String message;

    @OneToOne(mappedBy = "status")
    private Order order;

    public OrderStatus () {

    }

    public OrderStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
