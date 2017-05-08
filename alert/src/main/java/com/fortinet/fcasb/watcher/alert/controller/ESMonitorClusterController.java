package com.fortinet.fcasb.watcher.alert.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.http.client.ClientProtocolException;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.cluster.node.info.NodesInfoResponse;
import org.elasticsearch.action.admin.cluster.stats.ClusterStatsResponse;
import org.elasticsearch.client.ClusterAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zliu on 17/3/3.
 */
@RestController
@RequestMapping("/esmonitor/cluster")
public class ESMonitorClusterController {

	@Value("${es.transport.client.host}")
	public String esServerHost;

	@Value("${es.transport.client.port}")
	public int esServerPort;
	
	@Value("${es.cluster.name}")
	public String esClusterName;
	
	@SuppressWarnings("resource")
	@RequestMapping(value = "/health", method = RequestMethod.GET, produces = "application/json")
	public String health() throws UnknownHostException {
		byte[] ipInBytes = InetAddress.getByName(esServerHost).getAddress();
    	Settings settings = Settings.builder()
    	        .put("cluster.name", esClusterName).build();
    	TransportClient client = new PreBuiltTransportClient(settings)
    	        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByAddress(ipInBytes), esServerPort));
    	ClusterAdminClient clusterAdminClient = client.admin().cluster();
    	ClusterHealthResponse clusterHealth = clusterAdminClient.prepareHealth().get();
    	
		return clusterHealth.toString();
	}

	// get cluster cpu jvm memory
	@SuppressWarnings("resource")
	@RequestMapping(value = "/stats", method = RequestMethod.GET, produces = "application/json")
	public String stats() throws ClientProtocolException, IOException {
		byte[] ipInBytes = InetAddress.getByName(esServerHost).getAddress();
    	Settings settings = Settings.builder()
    	        .put("cluster.name", esClusterName).build();
    	TransportClient client = new PreBuiltTransportClient(settings)
    	        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByAddress(ipInBytes), esServerPort));
    	ClusterAdminClient clusterAdminClient = client.admin().cluster();
    	ClusterStatsResponse clusterStats = clusterAdminClient.prepareClusterStats().get();
    	
    	return clusterStats.toString();
	}
	
	@SuppressWarnings("resource")
	@RequestMapping(value = "/nodes", method = RequestMethod.GET, produces = "application/json")
	public String nodes() throws ClientProtocolException, IOException {
		byte[] ipInBytes = InetAddress.getByName(esServerHost).getAddress();
    	Settings settings = Settings.builder()
    	        .put("cluster.name", esClusterName).build();
    	TransportClient client = new PreBuiltTransportClient(settings)
    	        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByAddress(ipInBytes), esServerPort));
    	ClusterAdminClient clusterAdminClient = client.admin().cluster();
    	NodesInfoResponse nodesInfo = clusterAdminClient.prepareNodesInfo().get();
    	
    	return nodesInfo.toString();
	}

	@SuppressWarnings("resource")
	@RequestMapping(value = "/nodes/{nodeName}/stats", method = RequestMethod.GET, produces = "application/json")
	public String nodeStats(@PathVariable("nodeName") String nodeName) throws ClientProtocolException, IOException {
		byte[] ipInBytes = InetAddress.getByName(esServerHost).getAddress();
    	Settings settings = Settings.builder()
    	        .put("cluster.name", esClusterName).build();
    	TransportClient client = new PreBuiltTransportClient(settings)
    	        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByAddress(ipInBytes), esServerPort));
    	ClusterAdminClient clusterAdminClient = client.admin().cluster();
    	NodesInfoResponse nodesInfo = clusterAdminClient.prepareNodesInfo(nodeName).get();
    	
    	return nodesInfo.toString();
	}

}
