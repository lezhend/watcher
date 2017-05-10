package com.fortinet.fcasb.watcher.alert.controller;

import org.apache.http.client.ClientProtocolException;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.cluster.node.info.NodesInfoResponse;
import org.elasticsearch.action.admin.cluster.stats.ClusterStatsResponse;
import org.elasticsearch.client.ClusterAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Created by zliu on 17/3/3.
 */
@RestController
@RequestMapping("/esmonitor/cluster")
public class ESMonitorClusterController {
	
	@Autowired
    private TransportClient client;

	@RequestMapping(value = "/health", method = RequestMethod.GET, produces = "application/json")
	public String health() throws UnknownHostException {
    	ClusterAdminClient clusterAdminClient = client.admin().cluster();
    	ClusterHealthResponse clusterHealth = clusterAdminClient.prepareHealth().get();
		return clusterHealth.toString();
	}

	// get cluster cpu jvm memory
	@RequestMapping(value = "/stats", method = RequestMethod.GET, produces = "application/json")
	public String stats() throws ClientProtocolException, IOException {
    	ClusterAdminClient clusterAdminClient = client.admin().cluster();
    	ClusterStatsResponse clusterStats = clusterAdminClient.prepareClusterStats().get();
    	
    	return clusterStats.toString();
	}
	
	@RequestMapping(value = "/nodes", method = RequestMethod.GET, produces = "application/json")
	public String nodes() throws ClientProtocolException, IOException {
    	ClusterAdminClient clusterAdminClient = client.admin().cluster();
    	NodesInfoResponse nodesInfo = clusterAdminClient.prepareNodesInfo().get();
    	
    	return nodesInfo.toString();
	}

	@RequestMapping(value = "/nodes/{nodeName}/stats", method = RequestMethod.GET, produces = "application/json")
	public String nodeStats(@PathVariable("nodeName") String nodeName) throws ClientProtocolException, IOException {
    	ClusterAdminClient clusterAdminClient = client.admin().cluster();
    	NodesInfoResponse nodesInfo = clusterAdminClient.prepareNodesInfo(nodeName).get();
    	
    	return nodesInfo.toString();
	}

}
