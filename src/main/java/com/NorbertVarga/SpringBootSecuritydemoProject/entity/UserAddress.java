package com.NorbertVarga.SpringBootSecuritydemoProject.entity;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table (name = "user_adress")
// Entity Listener needed for auditing and automatically manage the creating and modified dates
@EntityListeners(AuditingEntityListener.class)
public class UserAddress {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "address_id")
    private Long addressId;

    @Column (name = "address_country")
    private String country;

    @Column (name = "address_city")
    private String city;

    @Column (name = "address_zipcode")
    private String zipcode;

    @Column (name = "address_street")
    private String street;

    @Column (name = "address_house_number")
    private Integer houseNumber;

    @Column (name = "address_additional_info")
    private String additionalInfo;

    @Column(name = "address_created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "address_last_modified")
    @LastModifiedDate
    private LocalDateTime lastModified;

    public UserAddress() {
    }

    public UserAddress(String country, String city, String zipcode, String street, Integer houseNumber, String additionalInfo) {
        this.country = country;
        this.city = city;
        this.zipcode = zipcode;
        this.street = street;
        this.houseNumber = houseNumber;
        this.additionalInfo = additionalInfo;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }
}
