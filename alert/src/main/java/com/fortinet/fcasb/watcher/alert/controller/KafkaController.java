package com.fortinet.fcasb.watcher.alert.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.UnknownHostException;

/**
 * Created by zliu on 17/3/3.
 */
@Controller
@RequestMapping("/kafka")
public class KafkaController {
	

	@RequestMapping(value = "/manage.html", method = RequestMethod.GET)
	public String manage(Model model) throws UnknownHostException {
		return "kafka/manage";
	}

}
