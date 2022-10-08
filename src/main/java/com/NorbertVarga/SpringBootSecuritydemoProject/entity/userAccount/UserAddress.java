package com.NorbertVarga.SpringBootSecuritydemoProject.entity.userAccount;


import com.NorbertVarga.SpringBootSecuritydemoProject.dto.userAddress.AddressCreateCommand;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_adress")
// Entity Listener needed for auditing and automatically manage the creating and modified dates
@EntityListeners(AuditingEntityListener.class)
public class UserAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", nullable = false)
    private Long addressId;

    @Column(name = "address_country")
    @Size(min = 3, message
            = "Country must be minimum 3 character")
    @NotBlank
    private String country;

    @Column(name = "address_city")
    @Size(min = 3, message
            = "City must be minimum 3 character")
    @NotBlank
    private String city;

    @Column(name = "address_zipcode")
    @NotBlank
    private String zipcode;

    @Column(name = "address_street")
    @NotBlank
    private String street;

    @Column(name = "address_house_number")
    @NotNull
    private Integer houseNumber;

    @Column(
            name = "address_additional_info",
            columnDefinition = "TEXT(500)")
    private String additionalInfo;

    @Column(name = "address_created_at")
    @CreatedDate
    @NotNull
    private LocalDateTime createdAt;

    @Column(name = "address_last_modified")
    @LastModifiedDate
    @NotNull
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

    public UserAddress(AddressCreateCommand command) {
        this.country = command.getCountry();
        this.city = command.getCity();
        this.zipcode = command.getZipcode();
        this.street = command.getStreet();
        this.houseNumber = command.getHouseNumber();
        this.additionalInfo = command.getAdditionalInfo();
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
