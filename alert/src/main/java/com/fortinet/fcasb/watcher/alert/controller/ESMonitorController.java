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
	

	@Value("${es.server.rest.cluster.health}")
	public String restClusterHealth;
	
	@Value("${es.server.rest.cluster.stats}")
	public String restClusterStats;
	
	@Value("${es.server.rest.cluster.nodes}")
	public String restNodes;
	
	@Value("${es.server.rest.cluster.node.stats}")
	public String restNodeStats;
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping(value = "/index.html", method = RequestMethod.GET)
	public String health(Model model) throws UnknownHostException {
		model.addAttribute("restClusterHealth", restClusterHealth);
		model.addAttribute("restClusterStats", restClusterStats);
		model.addAttribute("restNodes", restNodes);
		model.addAttribute("restNodeStats", restNodeStats);

		return "monitor/index";
	}
	
	@RequestMapping(value = "/nodes.html", method = RequestMethod.GET)
	public String stats(Model model) throws ClientProtocolException, IOException {
		model.addAttribute("restClusterHealth", restClusterHealth);
		model.addAttribute("restClusterStats", restClusterStats);
		model.addAttribute("restNodes", restNodes);
		model.addAttribute("restNodeStats", restNodeStats);
    	return "monitor/nodes";
	}

}
