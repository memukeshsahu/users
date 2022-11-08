package com.techriff.userdetails.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
public class Product  extends Auditable{
    @Id
		@Column(name = "productId")
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int id;
        
        private String name;
        private String description;
        private long price;
        private String productType;
    
}
