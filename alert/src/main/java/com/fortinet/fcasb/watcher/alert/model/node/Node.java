package com.fortinet.fcasb.watcher.alert.model.node;

import com.google.gson.annotations.SerializedName;

public class Node {

	@SerializedName(value = "id")
	private String id;
	
	@SerializedName(value = "name")
	private String name;

	@SerializedName(value = "transport_address")
	private String transportAddress;

	@SerializedName(value = "host")
	private String host;

	@SerializedName(value = "ip")
	private String ip;

	@SerializedName(value = "version")
	private String version;

	@SerializedName(value = "index_buffer")
	private String indexBuffer;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTransportAddress() {
		return transportAddress;
	}

	public void setTransportAddress(String transportAddress) {
		this.transportAddress = transportAddress;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getIndexBuffer() {
		return indexBuffer;
	}

	public void setIndexBuffer(String indexBuffer) {
		this.indexBuffer = indexBuffer;
	}

}
