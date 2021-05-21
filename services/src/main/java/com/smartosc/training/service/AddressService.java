package com.smartosc.training.service;

import com.smartosc.training.dto.AddressDTO;
import com.smartosc.training.dto.CategoryDTO;
import com.smartosc.training.entity.Address;
import com.smartosc.training.entity.Category;
import com.smartosc.training.entity.Promotion;
import com.smartosc.training.entity.Users;
import com.smartosc.training.repositories.AddressRepository;
import com.smartosc.training.repositories.CategoryRepository;
import com.smartosc.training.repositories.UserRepository;
import com.smartosc.training.utils.ConvertUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;

    public List<AddressDTO> getAllAddress() {
        List<Address> addresses = addressRepository.findAll();
        List<AddressDTO> addressDTOS = addresses.stream()
                .map(ConvertUtils::convertAddressToAddressDTO)
                .collect(Collectors.toList());
        return addressDTOS;
    }

    public AddressDTO getAddressByUserId(Integer userId) {
        List<Address> addresses = addressRepository.findByUsers_UserId(userId);
        if (addresses.isEmpty()) {
            return new AddressDTO();
        } else {
            Address address = addresses.stream().max(Comparator.comparing(Address::getAddressId)).get();
            return ConvertUtils.convertAddressToAddressDTO(address);
        }
    }

    public Address addAddress(AddressDTO addressDTO, Integer userId) {
        Users users = userRepository.findByUserId(userId);

        Address oldAddress = ConvertUtils.convertAddressDTOToAddress(getAddressByUserId(userId));

        Address address = ConvertUtils.convertAddressDTOToAddress(addressDTO);
        address.setUsers(users);

        if (oldAddress.getAddress().equals(address.getAddress()) &&
                oldAddress.getPhone().equals(address.getPhone())&&
                oldAddress.getUserName().equals(address.getUserName())) {
            return address;
        } else {
            return addressRepository.save(address);
        }
    }
}
