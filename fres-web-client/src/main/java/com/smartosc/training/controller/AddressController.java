package com.smartosc.training.controller;

import com.smartosc.training.service.AddressService;
import com.smartosc.training.service.ProductService;
import com.smartosc.training.service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private ProductService productService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private RestService restService;

    @Value("${prefix.user}")
    private String prefixUser;
    @Value("${prefix.address}")
    private String prefixAddress;

    @Value("${api.url}")
    private String url;

}
