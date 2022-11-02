package com.techriff.userdetails.dto;

import lombok.Data;

@Data
public class AddressTypeDto {

    int id;
    String addressType;
    public String getAddressType() {
        return addressType;
    }
    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }
    public int getId() {
        return id;
    }
    
    
}
