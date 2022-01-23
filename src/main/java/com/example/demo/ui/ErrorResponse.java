package com.example.demo.ui;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
	
	private String message;
	private Integer responseCode;
	private Long errorTime;

}
