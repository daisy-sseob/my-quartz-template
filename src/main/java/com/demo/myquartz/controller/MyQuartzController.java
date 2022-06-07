package com.demo.myquartz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyQuartzController {

	@GetMapping("/")
	public String main(){
		return "main";
	}
	
	@GetMapping("/exception")
	public String list(){
		return "exception";
	}
}