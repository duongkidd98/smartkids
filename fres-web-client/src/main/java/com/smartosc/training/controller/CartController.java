package com.smartosc.training.controller;

import javax.servlet.http.HttpServletRequest;

import com.smartosc.training.dto.AddressDTO;
import com.smartosc.training.dto.UserDTO;
import com.smartosc.training.entity.APIResponse;
import com.smartosc.training.entity.AppUserDetails;
import com.smartosc.training.entity.CartLineInfo;
import com.smartosc.training.service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.smartosc.training.dto.ProductDTO;
import com.smartosc.training.entity.CartInfo;
import com.smartosc.training.service.ProductService;
import com.smartosc.training.utils.CartSupportUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
    private ProductService productService;


    @Value("${prefix.user}")
    private String prefixUser;
    @Value("${prefix.address}")
    private String prefixAddress;

    @Value("${api.url}")
    private String url;

    @Autowired
    private RestService restService;



    @GetMapping
    public String view(HttpServletRequest request, Model model) {
        CartInfo cartInfo = CartSupportUtils.getCartInSession(request);
        model.addAttribute("myCart", cartInfo);
        model.addAttribute("myCartProduct", cartInfo.getCartLines());
        model.addAttribute("sizeCart", cartInfo.getCartLines().size());

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username= "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        UserDTO user = null;
        AddressDTO addressDTO= null;
        Map values = new HashMap<String, Object>();
        values.put("username", username);
            APIResponse<UserDTO> responseData = restService.execute(
                    new StringBuilder(url).append(prefixUser).append("/username/{username}").toString(),
                    HttpMethod.GET,
                    null,
                    null,
                    new ParameterizedTypeReference<APIResponse<UserDTO>>() {
                    },
                    values);

            if (responseData.getStatus() == 200) {
                user = responseData.getData();
            }
            if (principal instanceof UserDetails){


            int userId = user.getUserId();
            APIResponse<AddressDTO> addressDTOAPIResponse = restService.execute(
                    new StringBuilder(url).append("/address/user/").append(userId).toString(),
                    HttpMethod.GET,
                    null,
                    null,
                    new ParameterizedTypeReference<APIResponse<AddressDTO>>() {
                    },
                    values);
            if (addressDTOAPIResponse.getStatus() == 200) {
                addressDTO = addressDTOAPIResponse.getData();
                model.addAttribute("address", addressDTO);}
            }
            else
                model.addAttribute("address", new AddressDTO());
            model.addAttribute("user", user);

        return "cart";
    }


    @GetMapping("/buycart")
    @ResponseBody
    public Integer buyProduct(HttpServletRequest request,
                          @RequestParam(value = "id", defaultValue = "") Integer id,
                          @RequestParam(value = "quantity", defaultValue = "1") Integer quantity) {
        ProductDTO productDTO = null;
        if (id != null && id > 0) {
            productDTO = productService.findById(id);
        }

        if (productDTO != null) {
            CartInfo cartInfo = CartSupportUtils.getCartInSession(request);
            ProductDTO product = new ProductDTO(productDTO);
            cartInfo.addProduct(product, quantity);
            return cartInfo.getCartLines().size();
        }
        else {
            return 0;
        }
    }

    @GetMapping("/removecart" )
    @ResponseBody
    public Integer removeProduct(HttpServletRequest request,
                                 @RequestParam(value = "id", defaultValue = "") Integer id) {
        ProductDTO productDTO = null;
        if (id != null && id > 0) {
            productDTO = productService.findById(id);
        }
        if (productDTO != null) {

            CartInfo cartInfo = CartSupportUtils.getCartInSession(request);
            ProductDTO product = new ProductDTO(productDTO);
            cartInfo.removeProduct(product);
            return cartInfo.getCartLines().size();
        }
        else {
            return 0;
        }
    }
    @GetMapping("/updatecart" )
    @ResponseBody
    public Double updateProduct(HttpServletRequest request,
                                @RequestParam(value = "id", defaultValue = "") Integer id,
                                @RequestParam(value = "quantity", defaultValue = "1") Integer quantity) {
        ProductDTO productDTO = null;
        if (id != null && id > 0) {
            productDTO = productService.findById(id);
        }
        if (productDTO != null) {
            CartInfo cartInfo = CartSupportUtils.getCartInSession(request);
            ProductDTO product = new ProductDTO(productDTO);
            cartInfo.addProduct2(product, quantity);
            CartLineInfo line = cartInfo.findLineByCode(productDTO.getProductId());
            if(line != null) {
                return line.getAmount();
            }
        }
        return 0.0;
    }
	
}
