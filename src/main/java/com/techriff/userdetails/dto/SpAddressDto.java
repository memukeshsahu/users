package com.techriff.userdetails.dto;

public class SpAddressDto 
{
     public int address_id;
    public String address;
    public String city;
    public String state;
    public int zip_code;
    public SpAddressDto(int address_id, String address, String city, String state, int zip_code) {
        this.address_id = address_id;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip_code = zip_code;
    }




}
