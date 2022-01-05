package com.example.shoping_app;

public class Model {
    String name,mobile,password,image,address;

    public Model(String name, String mobile, String password, String image, String address) {
        this.name = name;
        this.mobile = mobile;
        this.password = password;
        this.image = image;
        this.address = address;
    }

    public Model() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
