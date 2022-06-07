package com.demo.myquartz.common.controller;

import com.demo.myquartz.common.dto.QuartzMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping(value = "/dangerZone")
public class DangerZoneController {

	private final PasswordEncoder passwordEncoder;
	
	@Value("${danger.zone.password}")
	private String dangerzonePassword;

	@Autowired
	public DangerZoneController(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	@PostMapping(value = "/match")
	public @ResponseBody ResponseEntity<QuartzMsg> match(@RequestParam String password) {
		return passwordEncoder.matches(password, dangerzonePassword)
				? ResponseEntity.ok(new QuartzMsg.Builder().msg("Danger zone password match : success").build())
				: ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new QuartzMsg.Builder().msg("Danger zone password match : fail. danger zone password 비교에 실패 했으므로 당신은 아무것도 할 수 없습니다.").build());
	}
}
