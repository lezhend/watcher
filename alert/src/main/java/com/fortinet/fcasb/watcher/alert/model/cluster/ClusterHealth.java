package com.fortinet.fcasb.watcher.alert.model.cluster;

import com.google.gson.annotations.SerializedName;

public class ClusterHealth {

	@SerializedName(value = "cluster_name")
	private String clusterName;
	
	@SerializedName(value = "status")
	private String status;
	
	@SerializedName(value = "timed_out")
	private boolean timedOut;
	
	@SerializedName(value = "number_of_nodes")
	private int numberOfNodes;
	
	@SerializedName(value = "number_of_data_nodes")
	private int numberOfDataNodes;
	
	@SerializedName(value = "active_primary_shards")
	private int activePrimaryShards;
	
	@SerializedName(value = "active_shards")
	private int activeShards;
	
	@SerializedName(value = "relocating_shards")
	private int relocatingShards;
	
	@SerializedName(value = "initializing_shards")
	private int initializingShards;
	
	@SerializedName(value = "unassigned_shards")
	private int unassignedShards;
	
	@SerializedName(value = "delayed_unassigned_shards")
	private int delayedUnassignedShards;
	
	@SerializedName(value = "number_of_pending_tasks")
	private int numberOfPendingTasks;
	
	@SerializedName(value = "number_of_in_flight_fetch")
	private int numberOfInFlightFetch;
	
	@SerializedName(value = "task_max_waiting_in_queue_millis")
	private int taskMaxWaitingInQueueMillis;
	
	@SerializedName(value = "active_shards_percent_as_number")
	private int activeShardsPercentAsNumber;

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isTimedOut() {
		return timedOut;
	}

	public void setTimedOut(boolean timedOut) {
		this.timedOut = timedOut;
	}

	public int getNumberOfNodes() {
		return numberOfNodes;
	}

	public void setNumberOfNodes(int numberOfNodes) {
		this.numberOfNodes = numberOfNodes;
	}

	public int getNumberOfDataNodes() {
		return numberOfDataNodes;
	}

	public void setNumberOfDataNodes(int numberOfDataNodes) {
		this.numberOfDataNodes = numberOfDataNodes;
	}

	public int getActivePrimaryShards() {
		return activePrimaryShards;
	}

	public void setActivePrimaryShards(int activePrimaryShards) {
		this.activePrimaryShards = activePrimaryShards;
	}

	public int getActiveShards() {
		return activeShards;
	}

	public void setActiveShards(int activeShards) {
		this.activeShards = activeShards;
	}

	public int getRelocatingShards() {
		return relocatingShards;
	}

	public void setRelocatingShards(int relocatingShards) {
		this.relocatingShards = relocatingShards;
	}

	public int getInitializingShards() {
		return initializingShards;
	}

	public void setInitializingShards(int initializingShards) {
		this.initializingShards = initializingShards;
	}

	public int getUnassignedShards() {
		return unassignedShards;
	}

	public void setUnassignedShards(int unassignedShards) {
		this.unassignedShards = unassignedShards;
	}

	public int getDelayedUnassignedShards() {
		return delayedUnassignedShards;
	}

	public void setDelayedUnassignedShards(int delayedUnassignedShards) {
		this.delayedUnassignedShards = delayedUnassignedShards;
	}

	public int getNumberOfPendingTasks() {
		return numberOfPendingTasks;
	}

	public void setNumberOfPendingTasks(int numberOfPendingTasks) {
		this.numberOfPendingTasks = numberOfPendingTasks;
	}

	public int getNumberOfInFlightFetch() {
		return numberOfInFlightFetch;
	}

	public void setNumberOfInFlightFetch(int numberOfInFlightFetch) {
		this.numberOfInFlightFetch = numberOfInFlightFetch;
	}

	public int getTaskMaxWaitingInQueueMillis() {
		return taskMaxWaitingInQueueMillis;
	}

	public void setTaskMaxWaitingInQueueMillis(int taskMaxWaitingInQueueMillis) {
		this.taskMaxWaitingInQueueMillis = taskMaxWaitingInQueueMillis;
	}

	public int getActiveShardsPercentAsNumber() {
		return activeShardsPercentAsNumber;
	}

	public void setActiveShardsPercentAsNumber(int activeShardsPercentAsNumber) {
		this.activeShardsPercentAsNumber = activeShardsPercentAsNumber;
	}
	
}
