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
public class BankDetailDTO {
    private Integer id;
    private Integer bankId;
    private String type;
    private Integer value;

    //1 - active, 0 - inactive , default = 1
    private Integer status;

}
