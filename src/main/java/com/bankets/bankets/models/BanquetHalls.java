package com.bankets.bankets.models;

import jakarta.persistence.*;

import java.awt.*;

@Entity
public class BanquetHalls {

    @Id
    @SequenceGenerator(name = "pet_seq", sequenceName = "pet_sequence1", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "pet_seq")
    private Long banquet_halls_id;

    private Long category_id;
    private String banquet_halls_name, descript, adress, price;
    private String image_path;

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
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

    public Long getBanquet_halls_id() {
        return banquet_halls_id;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }

    public void setBanquet_halls_id(Long banquet_halls_id) {
        this.banquet_halls_id = banquet_halls_id;
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

    public BanquetHalls() {
    }

    public BanquetHalls( Long category_id, String banquet_halls_name, String descript, String adress, String price) {
        this.category_id = category_id;
        this.banquet_halls_name = banquet_halls_name;
        this.descript = descript;
        this.adress = adress;
        this.price = price;
    }

    public BanquetHalls(Long banquet_halls_id, Long category_id, String banquet_halls_name, String descript, String image_path, String adress, String price) {
        this.banquet_halls_id = banquet_halls_id;
        this.category_id = category_id;
        this.banquet_halls_name = banquet_halls_name;
        this.descript = descript;
        this.image_path = image_path;
        this.adress = adress;
        this.price = price;
    }
}
