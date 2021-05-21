package com.smartosc.training.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Table(name = "address")
@Data
public class Address implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int addressId;
    @Column(name = "address", length = 45)
    private String address;
    @Column(name = "userName", length = 145)
    private String userName;
    @Column(name = "note")
    private String note;
    @Column(name = "phone", length = 15)
    private String phone;
    @CreationTimestamp
    @Column(name = "createdAt", updatable = false)
    private Date createdAt;
    @UpdateTimestamp
    @Column(name = "updatedAt")
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
}
