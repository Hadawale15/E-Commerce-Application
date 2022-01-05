package com.example.shoping_app;

public class productModel {
    String Date,Time,Category_Name,Image_Uri,Product_Name,Product_Description,Product_Price,Product_RandomKey,Product_Status,Seller_Address,Seller_Email,Seller_Name,Seller_Phone_No,Seller_UID;

    public productModel() {
    }

    public productModel(String date, String time, String category_Name, String image_Uri, String product_Name, String product_Description, String product_Price, String product_RandomKey, String product_Status, String seller_Address, String seller_Email, String seller_Name, String seller_Phone_No, String seller_UID) {
        Date = date;
        Time = time;
        Category_Name = category_Name;
        Image_Uri = image_Uri;
        Product_Name = product_Name;
        Product_Description = product_Description;
        Product_Price = product_Price;
        Product_RandomKey = product_RandomKey;
        Product_Status = product_Status;
        Seller_Address = seller_Address;
        Seller_Email = seller_Email;
        Seller_Name = seller_Name;
        Seller_Phone_No = seller_Phone_No;
        Seller_UID = seller_UID;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getCategory_Name() {
        return Category_Name;
    }

    public void setCategory_Name(String category_Name) {
        Category_Name = category_Name;
    }

    public String getImage_Uri() {
        return Image_Uri;
    }

    public void setImage_Uri(String image_Uri) {
        Image_Uri = image_Uri;
    }

    public String getProduct_Name() {
        return Product_Name;
    }

    public void setProduct_Name(String product_Name) {
        Product_Name = product_Name;
    }

    public String getProduct_Description() {
        return Product_Description;
    }

    public void setProduct_Description(String product_Description) {
        Product_Description = product_Description;
    }

    public String getProduct_Price() {
        return Product_Price;
    }

    public void setProduct_Price(String product_Price) {
        Product_Price = product_Price;
    }

    public String getProduct_RandomKey() {
        return Product_RandomKey;
    }

    public void setProduct_RandomKey(String product_RandomKey) {
        Product_RandomKey = product_RandomKey;
    }

    public String getProduct_Status() {
        return Product_Status;
    }

    public void setProduct_Status(String product_Status) {
        Product_Status = product_Status;
    }

    public String getSeller_Address() {
        return Seller_Address;
    }

    public void setSeller_Address(String seller_Address) {
        Seller_Address = seller_Address;
    }

    public String getSeller_Email() {
        return Seller_Email;
    }

    public void setSeller_Email(String seller_Email) {
        Seller_Email = seller_Email;
    }

    public String getSeller_Name() {
        return Seller_Name;
    }

    public void setSeller_Name(String seller_Name) {
        Seller_Name = seller_Name;
    }

    public String getSeller_Phone_No() {
        return Seller_Phone_No;
    }

    public void setSeller_Phone_No(String seller_Phone_No) {
        Seller_Phone_No = seller_Phone_No;
    }

    public String getSeller_UID() {
        return Seller_UID;
    }

    public void setSeller_UID(String seller_UID) {
        Seller_UID = seller_UID;
    }}
