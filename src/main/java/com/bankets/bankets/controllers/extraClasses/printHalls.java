package com.bankets.bankets.controllers.extraClasses;

import jakarta.persistence.Lob;

import java.awt.*;
import java.util.Date;

public class printHalls {
    private Long banquet_halls_id;

    private Long category_id;
    private String category_name, category_description, banquet_halls_name, descript, adress, price;
    private String image_path;
    private String date;
    private Long purchase_id;

    public String getCategory_description() {
        return category_description;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public void setCategory_description(String category_description) {
        this.category_description = category_description;
    }

    public printHalls() {
    }

    public printHalls(Long banquet_halls_id, Long category_id, String category_name, String category_description, String banquet_halls_name, String descript, String adress, String price) {
        this.banquet_halls_id = banquet_halls_id;
        this.category_id = category_id;
        this.category_name = category_name;
        this.category_description = category_description;
        this.banquet_halls_name = banquet_halls_name;
        this.descript = descript;
        this.adress = adress;
        this.price = price;
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

    public Long getBanquet_halls_id() {
        return banquet_halls_id;
    }

    public void setBanquet_halls_id(Long banquet_halls_id) {
        this.banquet_halls_id = banquet_halls_id;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getBanquet_halls_name() {
        return banquet_halls_name;
    }

    public void setBanquet_halls_name(String banquet_halls_name) {
        this.banquet_halls_name = banquet_halls_name;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
