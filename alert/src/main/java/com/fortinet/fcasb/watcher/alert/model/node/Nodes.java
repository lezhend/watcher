package com.fortinet.fcasb.watcher.alert.model.node;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Nodes {

	@SerializedName(value = "nodes")
	private List<Node> nodes;
	
	class Node {
		
		@SerializedName(value = "name")
		private String name;
		
		@SerializedName(value = "transportAddress")
		private String transportAddress;
		
		@SerializedName(value = "host")
		private String host;
		
		@SerializedName(value = "ip")
		private String ip;
		
		@SerializedName(value = "version")
		private String version;
		
	}
	
}
