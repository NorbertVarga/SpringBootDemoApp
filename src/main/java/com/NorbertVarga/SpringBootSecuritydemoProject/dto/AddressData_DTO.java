package com.NorbertVarga.SpringBootSecuritydemoProject.dto;

import com.NorbertVarga.SpringBootSecuritydemoProject.entity.UserAddress;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class AddressData_DTO {

    private Long addressId;
    private String country;
    private String city;
    private String zipcode;
    private String street;
    private Integer houseNumber;
    private String additionalInfo;
    private String createdAt;
    private String lastModified;

    public AddressData_DTO() {
    }

    public AddressData_DTO(UserAddress address) {
        this.addressId = address.getAddressId();
        this.country = address.getCountry();
        this.city = address.getCity();
        this.zipcode = address.getZipcode();
        this.street = address.getStreet();
        this.houseNumber = address.getHouseNumber();
        this.additionalInfo = address.getAdditionalInfo();
        this.createdAt = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                .format(address.getCreatedAt());
        this.lastModified = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                .format(address.getLastModified());
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }
}
