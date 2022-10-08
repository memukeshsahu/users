package com.techriff.userdetails.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor

public class AddressType {
    @Column(name = "addressTypeId")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String AddressType;

}
