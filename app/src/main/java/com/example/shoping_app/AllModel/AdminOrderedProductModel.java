package com.example.shoping_app.AllModel;

public class AdminOrderedProductModel {
    String CurrentState,Address,City,Mobile,PinCode,Date,Time,TotalAmount,Name,RegisterMobileNo;

    public AdminOrderedProductModel(String currentState, String address, String city, String mobile, String pinCode, String date, String time, String totalAmount, String name,String registerMobileNo) {
        CurrentState = currentState;
        Address = address;
        City = city;
        Mobile = mobile;
        PinCode = pinCode;
        Date = date;
        Time = time;
        TotalAmount = totalAmount;
        Name = name;
        RegisterMobileNo=registerMobileNo;
    }

    public AdminOrderedProductModel() {
    }

    public String getRegisterMobileNo() {
        return RegisterMobileNo;
    }

    public void setRegisterMobileNo(String registerMobileNo) {
        RegisterMobileNo = registerMobileNo;
    }

    public String getCurrentState() {
        return CurrentState;
    }

    public void setCurrentState(String currentState) {
        CurrentState = currentState;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getPinCode() {
        return PinCode;
    }

    public void setPinCode(String pinCode) {
        PinCode = pinCode;
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

    public void setTime(String dime) {
        Time = dime;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
