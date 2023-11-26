package com.bankets.bankets.models;

import jakarta.persistence.*;

@Entity
public class Purchases {
    @Id
    @SequenceGenerator(name = "pet_seq", sequenceName = "pet_sequence5", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "pet_seq")
    private Long purchase_id;
    private Long customer_id;

    public Long getPurchase_id() {
        return purchase_id;
    }

    public void setPurchase_id(Long purchase_id) {
        this.purchase_id = purchase_id;
    }

    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }

    public Purchases() {
    }

    public Purchases(Long purchase_id, Long customer_id) {
        this.purchase_id = purchase_id;
        this.customer_id = customer_id;
    }
}
