package com.insurance.controller;

import com.insurance.entity.Address;
import com.insurance.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/{userId}")
    public Address addAddress(@PathVariable Long userId, @RequestBody Address address){

        return addressService.addAddress(userId, address);
    }

    @GetMapping("/{userId}")
    public List<Address> getAddresses(@PathVariable Long userId){

        return addressService.getAddresses(userId);
    }

    @PutMapping("/{addressId}")
    public Address updateAddress(@PathVariable Long addressId, @RequestBody Address address){

        return addressService.updateAddress(addressId, address);
    }

    @DeleteMapping("/{addressId}")
    public String deleteAddress(@PathVariable Long addressId){

        addressService.deleteAddress(addressId);

        return "Address Deleted Successfully";
    }
}