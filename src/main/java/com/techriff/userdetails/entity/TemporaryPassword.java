package com.techriff.userdetails.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class TemporaryPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String  tempPassword;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_USER_TEMP_PASSWORD",referencedColumnName = "userId")
    private Users user;
    private boolean flag=false;
    public TemporaryPassword(String tempPassword, Users user) {
        super();
        this.tempPassword = tempPassword;
        this.user = user;
    }
    
    

}
