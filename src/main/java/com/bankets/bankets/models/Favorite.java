package com.bankets.bankets.models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Favorite {

    @EmbeddedId
    private CompositeKey id;

    public Favorite() {
    }

    public Favorite(CompositeKey id) {
        this.id = id;
    }

    public CompositeKey getId() {
        return id;
    }

    public void setId(CompositeKey id) {
        this.id = id;
    }
}
