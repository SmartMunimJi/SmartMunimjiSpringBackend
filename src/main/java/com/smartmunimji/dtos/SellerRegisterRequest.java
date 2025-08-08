package com.smartmunimji.dtos;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SellerRegisterRequest {
    private String sellername;
    private String sellercontact;
    private String shopname;
    private String sellersemail;
    private String shopaddress;
    private String city;
    private String pincode;
    private String category;
    private String contractStatus;
    private String password;

    public String getSellername() {
        return sellername;
    }

    public void setSellername(String sellername) {
        this.sellername = sellername;
    }

    public String getSellercontact() {
        return sellercontact;
    }

    public void setSellercontact(String sellercontact) {
        this.sellercontact = sellercontact;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getSellersemail() {
        return sellersemail;
    }

    public void setSellersemail(String sellersemail) {
        this.sellersemail = sellersemail;
    }

    public String getShopaddress() {
        return shopaddress;
    }

    public void setShopaddress(String shopaddress) {
        this.shopaddress = shopaddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
