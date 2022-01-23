package com.example.demo.service;

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
	private static final long serialVersionUID = -2136829180451325578L;
	private String message;

}
