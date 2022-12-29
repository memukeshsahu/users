package com.techriff.userdetails.dto;

import java.util.Date;

public class SpUsersDto 
{
    public int user_id;
    public String first_name;
    public String last_name;
    public String email_adress;
    public Date dob;
    public int address_id;
    public String address;
    public String city;
    public String state;
    public int zip_code;
    // public SpUsersDto(int user_id, String first_name, String last_name, String email_adress) {
    //     this.user_id = user_id;
    //     this.first_name = first_name;
    //     this.last_name = last_name;
    //     this.email_adress = email_adress;
    // }
    public SpUsersDto(int user_id, String first_name, String last_name, String email_adress, Date dob, int address_id,
            String address, String city, String state, int zip_code) {
        this.user_id = user_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email_adress = email_adress;
        this.dob = dob;
        this.address_id = address_id;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip_code = zip_code;
    }
  

    
}
