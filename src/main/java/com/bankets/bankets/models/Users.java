package com.bankets.bankets.models;

import jakarta.persistence.*;

@Entity
public class Users {
    @Id
    @SequenceGenerator(name = "pet_seq", sequenceName = "pet_sequence7", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "pet_seq")
    private Long customer_id;
    private Long person_id;
    private Long role_id;
    private String login, password;


    public Long getPerson_id() {
        return person_id;
    }

    public Long getRole_id() {
        return role_id;
    }

    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }

    public void setPerson_id(Long person_id) {
        this.person_id = person_id;
    }

    public void setRole_id(Long role_id) {
        this.role_id = role_id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Users() {
    }

    public Users(String login, String password, Long person_id) {
        this.person_id = person_id;
        this.login = login;
        this.password = password;
    }

    public Users(Long role_id, Long person_id, String login, String password) {
        this.role_id = role_id;
        this.person_id = person_id;
        this.login = login;
        this.password = password;
    }
}
