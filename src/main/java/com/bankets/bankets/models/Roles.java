package com.bankets.bankets.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Roles {
    @Id
    @SequenceGenerator(name = "pet_seq", sequenceName = "pet_sequence6", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "pet_seq")
    private Long role_id;
    private String position;

    public Long getRole_id() {
        return role_id;
    }

    public void setRole_id(Long role_id) {
        this.role_id = role_id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Roles() {
    }

    public Roles(String position) {
        this.position = position;
    }

    public Roles(Long role_id, String position) {
        this.role_id = role_id;
        this.position = position;
    }
}
