package com.smartosc.training.controller;

import com.smartosc.training.dto.AddressDTO;
import com.smartosc.training.dto.CategoryDTO;
import com.smartosc.training.entity.APIResponse;
import com.smartosc.training.service.AddressService;
import com.smartosc.training.service.CategoryService;
import com.smartosc.training.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class AddressController {
    @Autowired
    private AddressService addressService;
    @Autowired
    private UserService userService;

    @GetMapping("address/all")
    public ResponseEntity<Object> getAllAddress() {
        HttpHeaders responseHeaders = new HttpHeaders();
        APIResponse<List<AddressDTO>> responseData = new APIResponse<>();
        responseData.setMessage("Get All Address successful");
        responseData.setData(addressService.getAllAddress());
        responseData.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(responseData, responseHeaders, HttpStatus.OK);
    }
    @GetMapping("address/user/{id}")
    public ResponseEntity<Object> getAddressByUserId(@PathVariable("id") Integer userID) {
        HttpHeaders responseHeaders = new HttpHeaders();
        APIResponse<AddressDTO> responseData = new APIResponse<>();
        responseData.setMessage("Get All Address By UserId successful");
        responseData.setData(addressService.getAddressByUserId(userID));
        responseData.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(responseData, responseHeaders, HttpStatus.OK);
    }

    @PostMapping("address/{userId}")
    public ResponseEntity<Object> addCategory(@RequestBody AddressDTO addressDTO,
                                              @PathVariable(value = "userId") Integer userId) {
        addressService.addAddress(addressDTO,userId);
        HttpHeaders responseHeaders = new HttpHeaders();
        APIResponse<AddressDTO> responseData = new APIResponse<>();
        responseData.setStatus(HttpStatus.OK.value());
        responseData.setMessage("Add successful");
        responseData.setData(addressDTO);
        return new ResponseEntity<>(responseData, responseHeaders, HttpStatus.OK);
    }
}
