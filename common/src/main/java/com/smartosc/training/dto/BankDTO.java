package com.smartosc.training.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Created by DuongBV on 17-04
 */
@Getter
@Setter
public class BankDTO extends BaseAuditDTO {

    private Integer bankId;

    @NotBlank(message = "Không được bỏ trống")
    @Pattern(regexp = "^[A-Z0-9]+$", message = "Mã phải gồm chữ cái viết hoa và số")
    private String code;

    private String shortName;

    @NotBlank(message = "Không được bỏ trống")
    private String legalName;

    @NotBlank(message = "Không được bỏ trống")
    @Pattern(regexp = "^[0-9]+$", message = "Đầu số thẻ chỉ được nhập số")
    private String prefixCard;

    //1 - active , 0 - inactive,default = 1
    private Integer status;

    @Override
    public String toString() {
        return "BankDTO{" +
                "bankId=" + bankId +
                ", code='" + code + '\'' +
                ", shortName='" + shortName + '\'' +
                ", legalName='" + legalName + '\'' +
                ", prefixCard='" + prefixCard + '\'' +
                ", status=" + status +
                '}';
    }
}
