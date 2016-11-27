package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.local.AES;

@Service
public class SecuringService {
	
	@Autowired
	private AES aesService;
	
	
}
