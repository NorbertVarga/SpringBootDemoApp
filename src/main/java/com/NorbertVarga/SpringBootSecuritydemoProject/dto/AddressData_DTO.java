package com.NorbertVarga.SpringBootSecuritydemoProject.dto;

import com.NorbertVarga.SpringBootSecuritydemoProject.entity.UserAddress;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class AddressData_DTO {

    private Long addressId;
    private String country;
    private String city;
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
        this.street = address.getStreet();
        this.houseNumber = address.getHouseNumber();
        this.additionalInfo = address.getAdditionalInfo();
        this.createdAt = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                .format(address.getCreatedAt());
        this.lastModified = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                .format(address.getLastModified());
    }
}
