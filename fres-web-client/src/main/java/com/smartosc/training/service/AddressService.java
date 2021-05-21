package com.smartosc.training.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smartosc.training.dto.AddressDTO;
import com.smartosc.training.dto.OrdersDTO;
import com.smartosc.training.dto.UserDTO;
import com.smartosc.training.service.impl.RestServiceImpl;
import com.smartosc.training.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.smartosc.training.dto.CategoryDTO;
import com.smartosc.training.entity.APIResponse;

@Service
public class AddressService {

    @Autowired
    private RestServiceImpl restService;
    @Autowired
    private JWTUtils jwtTokenUtil;
    @Value("${api.url}")
    private String url;

    @Value("${prefix.address}")
    private String preUrl;

    public AddressDTO addAddress(AddressDTO addressDTO, String username) {
        String authToken = jwtTokenUtil.getJwtTokenFromSecurityContext();
        UserDTO user = null;
        Map values = new HashMap<String, Object>();
        APIResponse<UserDTO> responseData = restService.execute(
                new StringBuilder(url).append("user/username/").append(username).toString(),
                HttpMethod.GET,
                null,
                null,
                new ParameterizedTypeReference<APIResponse<UserDTO>>() {
                },
                values);

        if (responseData.getStatus() == 200) {
            user = responseData.getData();

            HttpHeaders header = new HttpHeaders();
            header.setBearerAuth(authToken);
            restService.execute(
                    new StringBuilder(url).append(preUrl).append("/").append(user.getUserId()).toString(),
                    HttpMethod.POST,
                    header,
                    addressDTO,
                    new ParameterizedTypeReference<APIResponse<OrdersDTO>>() {
                    }).getData();
        }
        return addressDTO;

    }
}