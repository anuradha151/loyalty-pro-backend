package com.anuradha.loyaltyprobackend.dto;

public class CustomerDto {

    private String uuid;
    private String name;
    private String email;
    private String mobile;
    private String address;

    public CustomerDto() {
    }

    public CustomerDto(String uuid, String name, String email, String mobile, String address) {
        this.uuid = uuid;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
