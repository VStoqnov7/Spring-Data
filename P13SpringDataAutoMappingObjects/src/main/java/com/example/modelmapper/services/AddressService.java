package com.example.modelmapper.services;

import com.example.modelmapper.entities.Address;
import com.example.modelmapper.entities.dtos.AddressDTO;

public interface AddressService {
    Address create(AddressDTO data);
}
