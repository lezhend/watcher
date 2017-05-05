package com.fortinet.fcasb.watcher.alert.model.node;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class NodesStats {
	
	@SerializedName(value = "timestamp")
	private long timestamp;
	
	@SerializedName(value = "name")
	private String name;
	
	@SerializedName(value = "transport_address")
	private String transportAddress;
	
	@SerializedName(value = "host")
	private String host;
	
	@SerializedName(value = "ip")
	private String ip;
	
	@SerializedName(value = "roles")
	private List<String> roles;
	
	@SerializedName(value = "os")
	private OS os;
	
	@SerializedName(value = "jvm")
	private JVM jvm;
	
	class OS {

		@SerializedName(value = "timestamp")
		private long timestamp;

		@SerializedName(value = "cpu")
		private CPU cpu;

		@SerializedName(value = "mem")
		private Mem mem;

		@SerializedName(value = "swap")
		private Swap swap;

		public long getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(long timestamp) {
			this.timestamp = timestamp;
		}

		public CPU getCpu() {
			return cpu;
		}

		public void setCpu(CPU cpu) {
			this.cpu = cpu;
		}

		public Mem getMem() {
			return mem;
		}

		public void setMem(Mem mem) {
			this.mem = mem;
		}

		public Swap getSwap() {
			return swap;
		}

		public void setSwap(Swap swap) {
			this.swap = swap;
		}

		class CPU {

			@SerializedName(value = "percent")
			private int percent;

			@SerializedName(value = "load_average")
			private LoadAverage loadAverage;

			public int getPercent() {
				return percent;
			}

			public void setPercent(int percent) {
				this.percent = percent;
			}

			public LoadAverage getLoadAverage() {
				return loadAverage;
			}

			public void setLoadAverage(LoadAverage loadAverage) {
				this.loadAverage = loadAverage;
			}
			
			class LoadAverage {

				@SerializedName(value = "1m")
				private float _1m;

				@SerializedName(value = "5m")
				private float _5m;

				@SerializedName(value = "15m")
				private float _15m;

				public float get_1m() {
					return _1m;
				}

				public void set_1m(float _1m) {
					this._1m = _1m;
				}

				public float get_5m() {
					return _5m;
				}

				public void set_5m(float _5m) {
					this._5m = _5m;
				}

				public float get_15m() {
					return _15m;
				}

				public void set_15m(float _15m) {
					this._15m = _15m;
				}

			}

		}

		class Mem extends Swap {

			@SerializedName(value = "free_percent")
			private int freePercent;

			@SerializedName(value = "used_percent")
			private int userPercent;

			public int getFreePercent() {
				return freePercent;
			}

			public void setFreePercent(int freePercent) {
				this.freePercent = freePercent;
			}

			public int getUserPercent() {
				return userPercent;
			}

			public void setUserPercent(int userPercent) {
				this.userPercent = userPercent;
			}

		}

		class Swap {

			@SerializedName(value = "total_in_bytes")
			protected long totalInBytes;

			@SerializedName(value = "free_in_bytes")
			protected long freeInBytes;

			@SerializedName(value = "used_in_bytes")
			protected long usedInBytes;

			public long getTotalInBytes() {
				return totalInBytes;
			}

			public void setTotalInBytes(long totalInBytes) {
				this.totalInBytes = totalInBytes;
			}

			public long getFreeInBytes() {
				return freeInBytes;
			}

			public void setFreeInBytes(long freeInBytes) {
				this.freeInBytes = freeInBytes;
			}

			public long getUsedInBytes() {
				return usedInBytes;
			}

			public void setUsedInBytes(long usedInBytes) {
				this.usedInBytes = usedInBytes;
			}

		}

	}
	
	class JVM {

		@SerializedName(value = "timestamp")
		private long timestamp;

		@SerializedName(value = "uptime_in_millis")
		private long uptimeInMillis;

		@SerializedName(value = "mem")
		private Mem mem;

		@SerializedName(value = "threads")
		private Threads threads;

		@SerializedName(value = "gc")
		private GC gc;
		
		@SerializedName(value = "buffer_pools")
		private BufferPools bufferPools;
		
		@SerializedName(value = "classes")
		private Classes classes;


		public long getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(long timestamp) {
			this.timestamp = timestamp;
		}

		public long getUptimeInMillis() {
			return uptimeInMillis;
		}

		public void setUptimeInMillis(long uptimeInMillis) {
			this.uptimeInMillis = uptimeInMillis;
		}

		public Mem getMem() {
			return mem;
		}

		public void setMem(Mem mem) {
			this.mem = mem;
		}

		public Threads getThreads() {
			return threads;
		}

		public void setThreads(Threads threads) {
			this.threads = threads;
		}

		public GC getGc() {
			return gc;
		}

		public void setGc(GC gc) {
			this.gc = gc;
		}

		public BufferPools getBufferPools() {
			return bufferPools;
		}

		public void setBufferPools(BufferPools bufferPools) {
			this.bufferPools = bufferPools;
		}

		public Classes getClasses() {
			return classes;
		}

		public void setClasses(Classes classes) {
			this.classes = classes;
		}

		class Mem {

			@SerializedName(value = "heap_used_in_bytes")
			private long heapUsedInBytes;

			@SerializedName(value = "heap_used_percent")
			private int heapUsedPercent;

			@SerializedName(value = "heap_committed_in_bytes")
			private long heapCommittedInBytes;

			@SerializedName(value = "heap_max_in_bytes")
			private long heapMaxInBytes;

			@SerializedName(value = "non_heap_used_in_bytes")
			private long nonHeapUsedInBytes;

			@SerializedName(value = "non_heap_committed_in_bytes")
			private long nonHeapCommittedInBytes;

			@SerializedName(value = "pools")
			private Pools pools;

			public long getHeapUsedInBytes() {
				return heapUsedInBytes;
			}

			public void setHeapUsedInBytes(long heapUsedInBytes) {
				this.heapUsedInBytes = heapUsedInBytes;
			}

			public int getHeapUsedPercent() {
				return heapUsedPercent;
			}

			public void setHeapUsedPercent(int heapUsedPercent) {
				this.heapUsedPercent = heapUsedPercent;
			}

			public long getHeapCommittedInBytes() {
				return heapCommittedInBytes;
			}

			public void setHeapCommittedInBytes(long heapCommittedInBytes) {
				this.heapCommittedInBytes = heapCommittedInBytes;
			}

			public long getHeapMaxInBytes() {
				return heapMaxInBytes;
			}

			public void setHeapMaxInBytes(long heapMaxInBytes) {
				this.heapMaxInBytes = heapMaxInBytes;
			}

			public long getNonHeapUsedInBytes() {
				return nonHeapUsedInBytes;
			}

			public void setNonHeapUsedInBytes(long nonHeapUsedInBytes) {
				this.nonHeapUsedInBytes = nonHeapUsedInBytes;
			}

			public long getNonHeapCommittedInBytes() {
				return nonHeapCommittedInBytes;
			}

			public void setNonHeapCommittedInBytes(long nonHeapCommittedInBytes) {
				this.nonHeapCommittedInBytes = nonHeapCommittedInBytes;
			}

			public Pools getPools() {
				return pools;
			}

			public void setPools(Pools pools) {
				this.pools = pools;
			}
			
			class Pools {

				@SerializedName(value = "young")
				Heap young;

				@SerializedName(value = "survivor")
				Heap survivor;

				@SerializedName(value = "old")
				Heap old;

				public Heap getYoung() {
					return young;
				}

				public void setYoung(Heap young) {
					this.young = young;
				}

				public Heap getSurvivor() {
					return survivor;
				}

				public void setSurvivor(Heap survivor) {
					this.survivor = survivor;
				}

				public Heap getOld() {
					return old;
				}

				public void setOld(Heap old) {
					this.old = old;
				}
				
				class Heap {

					@SerializedName(value = "used_in_bytes")
					private long usedInBytes;

					@SerializedName(value = "max_in_bytes")
					private long maxInBytes;

					@SerializedName(value = "peak_used_in_bytes")
					private long peakUsedInBytes;

					@SerializedName(value = "peak_max_in_bytes")
					private long peakMaxInBytes;

					public long getUsedInBytes() {
						return usedInBytes;
					}

					public void setUsedInBytes(long usedInBytes) {
						this.usedInBytes = usedInBytes;
					}

					public long getMaxInBytes() {
						return maxInBytes;
					}

					public void setMaxInBytes(long maxInBytes) {
						this.maxInBytes = maxInBytes;
					}

					public long getPeakUsedInBytes() {
						return peakUsedInBytes;
					}

					public void setPeakUsedInBytes(long peakUsedInBytes) {
						this.peakUsedInBytes = peakUsedInBytes;
					}

					public long getPeakMaxInBytes() {
						return peakMaxInBytes;
					}

					public void setPeakMaxInBytes(long peakMaxInBytes) {
						this.peakMaxInBytes = peakMaxInBytes;
					}

				}

			}
			
		}

		class Threads {

			@SerializedName(value = "count")
			private long count;

			@SerializedName(value = "peak_count")
			private long peakCount;

			public long getCount() {
				return count;
			}

			public void setCount(long count) {
				this.count = count;
			}

			public long getPeakCount() {
				return peakCount;
			}

			public void setPeakCount(long peakCount) {
				this.peakCount = peakCount;
			}

		}

		class GC {

			@SerializedName(value = "young")
			private Collectors collectors;

			public Collectors getCollectors() {
				return collectors;
			}

			public void setCollectors(Collectors collectors) {
				this.collectors = collectors;
			}
			
			class Collectors {

				@SerializedName(value = "young")
				private Collector young;

				@SerializedName(value = "old")
				private Collector old;

				public Collector getYoung() {
					return young;
				}

				public void setYoung(Collector young) {
					this.young = young;
				}

				public Collector getOld() {
					return old;
				}

				public void setOld(Collector old) {
					this.old = old;
				}
				
				class Collector {

					@SerializedName(value = "collection_count")
					private long collectionCount;

					@SerializedName(value = "collection_time_in_millis")
					private long collectionTimeInMillis;

					public long getCollectionCount() {
						return collectionCount;
					}

					public void setCollectionCount(long collectionCount) {
						this.collectionCount = collectionCount;
					}

					public long getCollectionTimeInMillis() {
						return collectionTimeInMillis;
					}

					public void setCollectionTimeInMillis(long collectionTimeInMillis) {
						this.collectionTimeInMillis = collectionTimeInMillis;
					}

				}

			}

		}
		
		class BufferPools {
			
			@SerializedName(value = "direct")
			private BufferPool direct;
			
			@SerializedName(value = "mapped")
			private BufferPool mapped;

			public BufferPool getDirect() {
				return direct;
			}

			public void setDirect(BufferPool direct) {
				this.direct = direct;
			}

			public BufferPool getMapped() {
				return mapped;
			}

			public void setMapped(BufferPool mapped) {
				this.mapped = mapped;
			}
			
			class BufferPool {
				
				@SerializedName(value = "count")
				private long count;
				
				@SerializedName(value = "used_in_bytes")
				private long usedInBytes;
				

				@SerializedName(value = "total_capacity_in_bytes")
				private long totalCapacityInBytes;


				public long getCount() {
					return count;
				}


				public void setCount(long count) {
					this.count = count;
				}


				public long getUsedInBytes() {
					return usedInBytes;
				}


				public void setUsedInBytes(long usedInBytes) {
					this.usedInBytes = usedInBytes;
				}


				public long getTotalCapacityInBytes() {
					return totalCapacityInBytes;
				}


				public void setTotalCapacityInBytes(long totalCapacityInBytes) {
					this.totalCapacityInBytes = totalCapacityInBytes;
				}
				
			}
			
		}
		
		class Classes {
			
			@SerializedName(value = "current_loaded_count")
			private long currentLoadedCount;
			
			@SerializedName(value = "total_loaded_count")
			private long totalLoadedCount;
			
			@SerializedName(value = "total_unloaded_count")
			private long totalUnloadedCount;

			public long getCurrentLoadedCount() {
				return currentLoadedCount;
			}

			public void setCurrentLoadedCount(long currentLoadedCount) {
				this.currentLoadedCount = currentLoadedCount;
			}

			public long getTotalLoadedCount() {
				return totalLoadedCount;
			}

			public void setTotalLoadedCount(long totalLoadedCount) {
				this.totalLoadedCount = totalLoadedCount;
			}

			public long getTotalUnloadedCount() {
				return totalUnloadedCount;
			}

			public void setTotalUnloadedCount(long totalUnloadedCount) {
				this.totalUnloadedCount = totalUnloadedCount;
			}
			
		}

	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
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

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public OS getOs() {
		return os;
	}

	public void setOs(OS os) {
		this.os = os;
	}

	public JVM getJvm() {
		return jvm;
	}

	public void setJvm(JVM jvm) {
		this.jvm = jvm;
	}

}
