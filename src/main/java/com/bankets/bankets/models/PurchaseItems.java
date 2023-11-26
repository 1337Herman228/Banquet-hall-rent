package com.bankets.bankets.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class PurchaseItems {

    @Id
    @SequenceGenerator(name = "pet_seq", sequenceName = "pet_sequence4", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "pet_seq")
    private Long purchase_id;

    private Long banquet_halls_id;
    private Date purchase_date;

    public Long getPurchase_id() {
        return purchase_id;
    }

    public void setPurchase_id(Long purchase_id) {
        this.purchase_id = purchase_id;
    }

    public Date getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(Date purchase_date) {
        this.purchase_date = purchase_date;
    }

    public Long getBanquet_halls_id() {
        return banquet_halls_id;
    }

    public void setBanquet_halls_id(Long banquet_halls_id) {
        this.banquet_halls_id = banquet_halls_id;
    }

    public PurchaseItems() {
    }

    public PurchaseItems( Long banquet_halls_id, Date purchase_date) {
        this.banquet_halls_id = banquet_halls_id;
        this.purchase_date = purchase_date;
    }

    public PurchaseItems(Long purchase_id, Long banquet_halls_id, Date purchase_date) {
        this.purchase_id = purchase_id;
        this.banquet_halls_id = banquet_halls_id;
        this.purchase_date = purchase_date;
    }
}
