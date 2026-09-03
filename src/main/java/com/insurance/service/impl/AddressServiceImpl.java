package com.insurance.service.impl;

import com.insurance.entity.Address;
import com.insurance.entity.User;
import com.insurance.exception.ResourceNotFoundException;
import com.insurance.repository.AddressRepository;
import com.insurance.repository.UserRepository;
import com.insurance.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Override
    public Address addAddress(Long userId, Address address) {

        User user =
                userRepository.findById(userId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("User not found"));

        address.setUser(user);

        return addressRepository.save(address);
    }

    @Override
    public List<Address> getAddresses(Long userId) {

        User user =
                userRepository.findById(userId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("User not found"));

        return user.getAddresses();
    }

    @Override
    public Address updateAddress(
            Long addressId,
            Address address) {

        Address existingAddress =
                addressRepository.findById(addressId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Address not found"));

        existingAddress.setCity(address.getCity());
        existingAddress.setState(address.getState());
        existingAddress.setPinCode(address.getPinCode());

        return addressRepository.save(existingAddress);
    }

    @Override
    public void deleteAddress(Long addressId) {

        Address address =
                addressRepository.findById(addressId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Address not found"));

        addressRepository.delete(address);
    }
}