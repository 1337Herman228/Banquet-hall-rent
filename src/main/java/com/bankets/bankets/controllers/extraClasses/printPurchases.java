package com.bankets.bankets.controllers.extraClasses;

import java.util.Date;

public class printPurchases {
    private printHalls hall;
    private printUsers user;
    private String date;
    private Long purchase_id;

    public printPurchases(printHalls hall, printUsers user, String date, Long purchase_id) {
        this.hall = hall;
        this.user = user;
        this.date = date;
        this.purchase_id = purchase_id;
    }

    public Long getPurchase_id() {
        return purchase_id;
    }

    public void setPurchase_id(Long purchase_id) {
        this.purchase_id = purchase_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public printPurchases() {
    }

    public printHalls getHall() {
        return hall;
    }

    public void setHall(printHalls hall) {
        this.hall = hall;
    }

    public printUsers getUser() {
        return user;
    }

    public void setUser(printUsers user) {
        this.user = user;
    }
}
