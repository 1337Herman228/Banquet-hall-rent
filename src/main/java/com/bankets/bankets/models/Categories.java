package com.bankets.bankets.models;

import jakarta.persistence.*;

@Entity
public class Categories {

    @Id
    @SequenceGenerator(name = "pet_seq", sequenceName = "pet_sequence2", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "pet_seq")
    private Long category_id;

    private String category_name, category_description;

    public String getCategory_description() {
        return category_description;
    }

    public void setCategory_description(String category_description) {
        this.category_description = category_description;
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

    public Categories() {
    }

    public Categories( String category_name, String category_description) {
        this.category_name = category_name;
        this.category_description = category_description;
    }

    public Categories(Long category_id, String category_name, String category_description) {
        this.category_id = category_id;
        this.category_name = category_name;
        this.category_description = category_description;
    }
}
