package com.fortinet.fcasb.watcher.alert.controller;

import com.fortinet.fcasb.watcher.alert.model.cluster.ClusterStats;
import com.fortinet.fcasb.watcher.alert.model.cluster.ClusterStats.Indices;
import com.fortinet.fcasb.watcher.alert.model.cluster.ClusterStats.Nodes.OS;
import com.fortinet.fcasb.watcher.alert.model.node.Node;
import com.fortinet.fcasb.watcher.alert.model.node.NodeStats;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zliu on 17/3/3.
 */
@Controller
@RequestMapping("/esmonitor/cluster")
public class ESMonitorClusterController {
	

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
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String health(Model model) throws UnknownHostException {
//    	ClusterAdminClient clusterAdminClient = client.admin().cluster();
//    	ClusterHealthResponse clusterHealth = clusterAdminClient.prepareHealth().get();
//    	model.addAttribute("clusterHealth", clusterHealth);
    	
		return "home";
	}
	
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String stats(Model model) throws ClientProtocolException, IOException {
//		ClusterAdminClient clusterAdminClient = client.admin().cluster();
//    	ClusterStatsResponse clusterStats = clusterAdminClient.prepareClusterStats().get();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(restClusterStats);
		CloseableHttpResponse response = httpclient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		String n = EntityUtils.toString(entity);
		httpclient.close();
		
		Gson gson = new Gson();
		ClusterStats clusterStats = gson.fromJson(n, ClusterStats.class);
    	
		com.fortinet.fcasb.watcher.alert.model.cluster.ClusterStats.Nodes nodes = clusterStats.getNodes();
		OS os = nodes.getOs();
    	model.addAttribute("clusterStats", clusterStats);
    	Indices indices = clusterStats.getIndices();
    	model.addAttribute("shards", gson.toJson(indices.getShards()));
    	model.addAttribute("docs", gson.toJson(indices.getDocs()));
    	model.addAttribute("segments", gson.toJson(indices.getSegments()));
    	model.addAttribute("nodes", clusterStats.getNodes());
    	model.addAttribute("os", gson.toJson(os));
    	model.addAttribute("fs", gson.toJson(nodes.getFs()));
    	model.addAttribute("jvm",gson.toJson(nodes.getJvm()));
    	model.addAttribute("mem", gson.toJson(os.getMem()));
    	
    	return "detail";
	}
	
	@RequestMapping(value = "/nodelist", method = RequestMethod.GET)
	public String nodes(Model model) throws ClientProtocolException, IOException {
//		ClusterAdminClient clusterAdminClient = client.admin().cluster();
//    	NodesInfoResponse nodesInfo = clusterAdminClient.prepareNodesInfo().get();
//    	List<NodeInfo> nodeInfos = nodesInfo.getNodes();
    	List<Node> nodes = new ArrayList<>();
//    	for (NodeInfo nodeInfo : nodeInfos) {
//    		Node node = new Node();
//    		DiscoveryNode discoveryNode = nodeInfo.getNode();
//    		node.setId(discoveryNode.getId());
//    		node.setName(discoveryNode.getName());
//    		node.setTransportAddress(discoveryNode.getAddress().toString());
//    		node.setHost(discoveryNode.getHostAddress());
//    		node.setIp(node.getHost());
//    		node.setVersion(discoveryNode.getVersion().toString());
//    		node.setIndexBuffer(nodeInfo.getTotalIndexingBuffer().toString());
//    		nodes.add(node);
//    	}

		model.addAttribute("nodes", nodes);
		
		return "nodelist";
	}
	
	@RequestMapping(value = "/nodedetail", method = RequestMethod.GET)
	public String nodeStats(/*@PathVariable("nodeName") String nodeName, */Model model) throws ClientProtocolException, IOException {
		String nodeName = "node-5";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(MessageFormat.format(restNodeStats, nodeName));
		CloseableHttpResponse response = httpclient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		String nodes = EntityUtils.toString(entity);
		JSONObject jsonObject = new JSONObject(nodes);
		JSONObject jsonNode = getJsonNode(jsonObject);
		httpclient.close();
		
		Gson gson = new Gson();
		NodeStats nodeStats = gson.fromJson(jsonNode.toString(), NodeStats.class);
    	model.addAttribute("nodeName", nodeStats.getName());
    	model.addAttribute("transportAddress", nodeStats.getTransportAddress());
    	model.addAttribute("host", nodeStats.getHost());
    	model.addAttribute("roles", nodeStats.getRoles());
    	model.addAttribute("jvm", gson.toJson(nodeStats.getJvm()));
    	model.addAttribute("os", gson.toJson(nodeStats.getOs()));
    	
//    	ClusterAdminClient clusterAdminClient = client.admin().cluster();
//    	NodesInfoResponse nodesInfo = clusterAdminClient.prepareNodesInfo(nodeName).get();
//    	NodeInfo nodeInfo = nodesInfo.getNodes().get(0);
//    	String indexBuffer = nodeInfo.getTotalIndexingBuffer().toString();
//    	Settings settings = nodeInfo.getSettings();
//    	DiscoveryNode node = nodeInfo.getNode();
//    	Version version = node.getVersion();
//    	model.addAttribute("indexBuffer", indexBuffer);
//    	model.addAttribute("settings", gson.toJson(settings));
//    	model.addAttribute("version", (int)version.major + "." + (int)version.minor + "." + (int)version.revision);
//    	
    	return "nodedetail";
	}
	
	private JSONObject getJsonNode(JSONObject jsonObject) {
		Iterator<String> keys = jsonObject.keys();
		while (keys.hasNext()) {
    		String key = keys.next();
    		if (key.equals("nodes")) {
				jsonObject = (JSONObject) jsonObject.get(key);
				keys = jsonObject.keys();
				key = keys.next();
				jsonObject = (JSONObject) jsonObject.get(key);
			} 
    	}
    	
		return jsonObject;
	}
	
	private JSONObject getJsonNode(JSONObject jsonObject, String target) {
		Iterator<String> keys = jsonObject.keys();
    	while (keys.hasNext()) {
    		String key = keys.next();
    		if (!key.equals(target)) {
				if (keys.hasNext()) {
					key = keys.next();
				} else {
					jsonObject = (JSONObject) jsonObject.get(key);
					keys = jsonObject.keys();
				}
			} else {
				jsonObject = (JSONObject) jsonObject.get(key);
				break;
			}
    	}
    	
		return jsonObject;
	}

}
