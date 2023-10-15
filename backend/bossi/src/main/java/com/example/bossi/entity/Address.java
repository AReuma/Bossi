package com.example.bossi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADDRESS_ID")
    private Long id;

    private String city;
    private String street;
    private String zipcode;

    private String addrName;    // 배송지명
    private String recipient;   // 수령인
    private String phoneNum;
    private boolean basic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    public static Address createAddress(String city, String street, String zipcode, String addrName, String recipient, String phoneNum, boolean isBasic, User user){
        Address address = Address.builder()
                .city(city)
                .street(street)
                .zipcode(zipcode)
                .addrName(addrName)
                .recipient(recipient)
                .phoneNum(phoneNum)
                .basic(isBasic)
                .user(user)
                .build();

        user.addAddress(address);
        return address;
    }

    public void changeBasicAddress(boolean isBasic){
        this.basic = isBasic;
    }
}
