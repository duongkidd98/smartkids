package com.smartosc.training.controller;

import java.util.HashMap;
import java.util.Map;

import com.smartosc.training.dto.AddressDTO;
import com.smartosc.training.dto.OrdersDTO;
import com.smartosc.training.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smartosc.training.dto.UserDTO;
import com.smartosc.training.entity.APIResponse;
import com.smartosc.training.entity.AppUserDetails;
import com.smartosc.training.service.RestService;
import com.smartosc.training.utils.JWTUtils;

@Controller
@RequestMapping("/")
public class UserController {
	
	@Autowired
    private RestService restService;
    @Autowired
    private AddressService addressService;

    @Autowired
    private JWTUtils jwtTokenUtil;

    @Value("${prefix.user}")
    private String prefixUrl;

    @Value("${api.url}")
    private String url;

    @Value("${prefix.address}")
    private String preUrl;
    @GetMapping("profile")
    public String profile(Model model) {
        AppUserDetails userLogin = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDTO user = null;
        AddressDTO addressDTO =null;
        Map values = new HashMap<String, Object>();
        values.put("username", userLogin.getUsername());
        APIResponse<UserDTO> responseData = restService.execute(
                new StringBuilder(url).append(prefixUrl).append("/username/{username}").toString(),
                HttpMethod.GET,
                null,
                null,
                new ParameterizedTypeReference<APIResponse<UserDTO>>() {},
                values);

        if(responseData.getStatus()==200) {
            user = responseData.getData();
        }

            APIResponse<AddressDTO> addressDTOAPIResponse = restService.execute(
                    new StringBuilder(url).append("/address/user/").append(user.getUserId()).toString(),
                    HttpMethod.GET,
                    null,
                    null,
                    new ParameterizedTypeReference<APIResponse<AddressDTO>>() {
                    },
                    values);

            if (addressDTOAPIResponse.getStatus() == 200) {
                addressDTO = addressDTOAPIResponse.getData();
            }

        model.addAttribute("user", user);
        model.addAttribute("addressDTO", addressDTO);
        return "profile";
    }

    @PostMapping("profile")
    public String profileUpdate(@ModelAttribute("user") UserDTO userDTO,@ModelAttribute("addressDTO") AddressDTO addressDTO){
        String authToken = jwtTokenUtil.getJwtTokenFromSecurityContext();
        HttpHeaders header = new HttpHeaders();
        header.setBearerAuth(authToken);
        header.setContentType(MediaType.APPLICATION_JSON);
        restService.execute(
        		url + prefixUrl+"/update",
                HttpMethod.PUT,
                header,
                userDTO,
                new ParameterizedTypeReference<APIResponse<UserDTO>>() {});

        return "redirect:/profile";
    }
	
}
