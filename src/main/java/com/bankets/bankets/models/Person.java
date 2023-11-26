package com.bankets.bankets.models;

import jakarta.persistence.*;

@Entity
public class Person {
    @Id
    @SequenceGenerator(name = "pet_seq", sequenceName = "pet_sequence3", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "pet_seq")
    private Long person_id;

    private String phone, email, name;

    public Long getCustomer_id() {
        return person_id;
    }

    public Long getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Long person_id) {
        this.person_id = person_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.person_id = customer_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person() {
    }

    public Person(String phone, String email, String name) {
        this.phone = phone;
        this.email = email;
        this.name = name;
    }

    public Person(Long person_id, String phone, String email, String name) {
        this.person_id = person_id;
        this.phone = phone;
        this.email = email;
        this.name = name;
    }
}
