package com.smartosc.training.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AbstractDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Date createdAt;
    private Date updatedAt;
    private int status;
}
