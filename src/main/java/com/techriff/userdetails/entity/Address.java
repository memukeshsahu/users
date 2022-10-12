package com.techriff.userdetails.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.techriff.userdetails.validationAnnotation.ValidateAdressType;
import com.techriff.userdetails.validationAnnotation.ValidateAdressType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address
 {
    @Column(name = "addressId")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String address;
    // This can br primary or secondary
    // Use own custom annotation
    //@ValidateAdressType
    private int addressTypeId;
    private String city;
    private String state;
    @Column(length = 6)
    private int zipCode;

}
