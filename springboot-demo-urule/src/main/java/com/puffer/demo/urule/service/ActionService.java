package com.puffer.demo.urule.service;

import org.springframework.stereotype.Service;

import com.bstek.urule.model.ExposeAction;

@Service
public class ActionService {

	
	@ExposeAction("问候")
	public String hello(String name){
		return "Hello ".concat(name);
	}
}
