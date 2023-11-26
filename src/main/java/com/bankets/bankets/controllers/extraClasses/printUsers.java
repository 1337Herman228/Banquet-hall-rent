package com.bankets.bankets.controllers.extraClasses;

public class printUsers {
    private Long customer_id;
    private Long person_id;
    private Long role_id;
    private String login, password;
    private String role,fio,phone,email;

    public printUsers() {
    }

    public printUsers(Long customer_id, Long person_id, Long role_id, String login, String password, String role, String fio, String phone, String email) {
        this.customer_id = customer_id;
        this.person_id = person_id;
        this.role_id = role_id;
        this.login = login;
        this.password = password;
        this.role = role;
        this.fio = fio;
        this.phone = phone;
        this.email = email;
    }

    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }

    public Long getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Long person_id) {
        this.person_id = person_id;
    }

    public Long getRole_id() {
        return role_id;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
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
}
