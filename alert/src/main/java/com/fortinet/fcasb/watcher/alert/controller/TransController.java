package com.fortinet.fcasb.watcher.alert.controller;

import com.alibaba.fastjson.TypeReference;
import com.fortinet.fcasb.watcher.alert.init.RestWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownHostException;

/**
 * Created by zliu on 17/3/3.
 */
@RestController
@RequestMapping("/request")
public class TransController {

	@Autowired
	private RestWrapper restWrapper;

	@RequestMapping(value = "/es", method = RequestMethod.GET)
	public Object health(Model model, @RequestParam String url) throws UnknownHostException {
		ResponseEntity<Object> responseEntity = restWrapper.get(url,new TypeReference<Object>(){});
		return responseEntity.getBody();
	}
	


}
