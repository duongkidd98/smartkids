package com.smartosc.training.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by DuongBV on 17-04
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ServiceDTO {
    private Integer serviceId;
    private String code;
    private String name;

    //1 - chuyển mạch 0 - không phải chuyển mạch
    private Integer transStatus;

    //1 - active , 0 - inactive, default = 1
    private Integer status;

}
