package com.example.demo.Exception;

import com.example.demo.model.Flight;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeatNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -537666382325236789L;
	
	private String message;

}
