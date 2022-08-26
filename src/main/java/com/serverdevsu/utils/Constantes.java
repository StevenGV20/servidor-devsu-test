package com.serverdevsu.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;

public class Constantes {
	
	public static final String MOVIMIENTO_TIPO_RETIRO="RETIRO";
	public static final String MOVIMIENTO_TIPO_DEPOSITO="DEPOSITO";
	
	public static final String MOVIMIENTO_VALOR_DEBITO="DEBITO";
	public static final String MOVIMIENTO_VALOR_CREDITO="CREDITO";
	
	public static ResponseEntity<?> mensaje(HttpStatus status,String title,String detail){
		return ResponseEntity
				.status(status)
				.header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
				.body(Problem.create()
						.withTitle(title)
						.withDetail(detail));
	}
}
