package com.smartosc.training.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Created by DuongBV on 17-04
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BaseAuditDTO {
    private String createdBy;
    private LocalDateTime createdDatetime;
    private String modifiedBy;
    private LocalDateTime modifiedDatetime;
}
