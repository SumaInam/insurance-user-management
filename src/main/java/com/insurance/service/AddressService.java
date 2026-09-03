package com.insurance.service;

import com.insurance.entity.Address;

import java.util.List;

public interface AddressService {

    Address addAddress(Long userId, Address address);

    List<Address> getAddresses(Long userId);

    Address updateAddress(Long addressId, Address address);

    void deleteAddress(Long addressId);
}
