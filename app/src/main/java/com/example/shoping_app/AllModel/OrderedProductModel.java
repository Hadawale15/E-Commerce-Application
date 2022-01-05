package com.example.shoping_app.AllModel;

public class OrderedProductModel {
    String Date,Time, Discount, Image,Pdesc,Pname, Pprize,Quantity,pid,Status,Seller_UID,Seller_Phone_No,Confirmation;


    public OrderedProductModel(String date, String time, String discount, String image, String pdesc, String pname, String pprize, String quantity, String pid, String status, String seller_UID, String seller_Phone_No, String confirmation) {
        Date = date;
        Time = time;
        Discount = discount;
        Image = image;
        Pdesc = pdesc;
        Pname = pname;
        Pprize = pprize;
        Quantity = quantity;
        this.pid = pid;
        Status = status;
        Seller_UID = seller_UID;
        Seller_Phone_No = seller_Phone_No;
        Confirmation = confirmation;
    }

    public OrderedProductModel() {
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

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getPdesc() {
        return Pdesc;
    }

    public void setPdesc(String pdesc) {
        Pdesc = pdesc;
    }

    public String getPname() {
        return Pname;
    }

    public void setPname(String pname) {
        Pname = pname;
    }

    public String getPprize() {
        return Pprize;
    }

    public void setPprize(String pprize) {
        Pprize = pprize;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getSeller_UID() {
        return Seller_UID;
    }

    public void setSeller_UID(String seller_UID) {
        Seller_UID = seller_UID;
    }

    public String getSeller_Phone_No() {
        return Seller_Phone_No;
    }

    public void setSeller_Phone_No(String seller_Phone_No) {
        Seller_Phone_No = seller_Phone_No;
    }

    public String getConfirmation() {
        return Confirmation;
    }

    public void setConfirmation(String confirmation) {
        Confirmation = confirmation;
    }
}
