package com.bankets.bankets.models;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CompositeKey implements Serializable {
    private Long customer_id;
    private Long banquet_halls_id;

    public CompositeKey(Long customer_id, Long banquet_halls_id) {
        this.customer_id = customer_id;
        this.banquet_halls_id = banquet_halls_id;
    }

    public CompositeKey() {
    }

    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }

    public Long getBanquet_halls_id() {
        return banquet_halls_id;
    }

    public void setBanquet_halls_id(Long banquet_halls_id) {
        this.banquet_halls_id = banquet_halls_id;
    }

}