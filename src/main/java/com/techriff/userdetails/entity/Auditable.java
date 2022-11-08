package com.techriff.userdetails.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable {
    
    @CreatedDate
		@Column(name = "created_date", updatable = false)
		@Temporal(TemporalType.TIMESTAMP)
		private Date creationDate;
		@CreatedBy
		@Column(name = "createdBy",updatable =  false)
		private String createdBy;

        @Column(name="isActive")
        private boolean isActive=true;
        @LastModifiedDate
		@Column(name = "last_modified_date")
		@Temporal(TemporalType.TIMESTAMP)
		private Date lastModifiedDate;

		@LastModifiedBy
		@Column(name = "modifiedBy")
		private String modifyBy;

}
