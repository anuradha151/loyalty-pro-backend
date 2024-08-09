package com.anuradha.loyaltyprobackend.dto;

public class CustomerDto {

    private String uuid;
    private String cardNumber;
    private String name;
    private String email;
    private String mobile;
    private String address;

    public CustomerDto() {
    }

    public CustomerDto(String uuid, String cardNumber, String name, String email, String mobile, String address) {
        this.uuid = uuid;
        this.cardNumber = cardNumber;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
