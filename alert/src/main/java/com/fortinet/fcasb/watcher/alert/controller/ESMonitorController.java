package com.fortinet.fcasb.watcher.alert.controller;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Created by zliu on 17/3/3.
 */
@Controller
@RequestMapping("/monitor")
public class ESMonitorController {
	


	@Value("${es.server.hosts.monitor}")
	private String[] esMonitors;

	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping(value = "/index.html", method = RequestMethod.GET)
	public String health(Model model) throws UnknownHostException {
		model.addAttribute("esMonitors",esMonitors);
		return "monitor/index";
	}
	
	@RequestMapping(value = "/nodes.html", method = RequestMethod.GET)
	public String stats(Model model) throws ClientProtocolException, IOException {
    	return "monitor/nodes";
	}

}
