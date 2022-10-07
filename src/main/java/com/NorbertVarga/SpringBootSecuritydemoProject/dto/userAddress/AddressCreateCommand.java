package com.NorbertVarga.SpringBootSecuritydemoProject.dto.userAddress;


public class AddressCreateCommand {

    private String country;
    private String city;
    private String zipcode;
    private String street;
    private Integer houseNumber;
    private String additionalInfo;

    public AddressCreateCommand() {
    }

    public AddressCreateCommand(String country, String city, String zipcode, String street, Integer houseNumber, String additionalInfo) {
        this.country = country;
        this.city = city;
        this.zipcode = zipcode;
        this.street = street;
        this.houseNumber = houseNumber;
        this.additionalInfo = additionalInfo;
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
}
