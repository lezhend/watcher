package com.fortinet.fcasb.watcher.alert.model.cluster;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ClusterStats {

	@SerializedName(value = "cluster_name")
	private String clusterName;

	@SerializedName(value = "timestamp")
	private long timestamp;
	
	@SerializedName(value = "status")
	private String status;
	
	@SerializedName(value = "indices")
	private Indices indices;
	
	@SerializedName(value = "nodes")
	private Nodes nodes;
	
	public String getClusterName() {
		return clusterName;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public String getStatus() {
		return status;
	}

	public Indices getIndices() {
		return indices;
	}

	public Nodes getNodes() {
		return nodes;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setIndices(Indices indices) {
		this.indices = indices;
	}

	public void setNodes(Nodes nodes) {
		this.nodes = nodes;
	}

	public class Indices {
		
		@SerializedName(value = "count")
		private long count;
		
		@SerializedName(value = "shards")
		private Shards shards;
		
		@SerializedName(value = "docs")
		private Docs docs;
		
		@SerializedName(value = "segments")
		private Segments segments;
		
		public long getCount() {
			return count;
		}

		public Shards getShards() {
			return shards;
		}

		public Docs getDocs() {
			return docs;
		}

		public Segments getSegments() {
			return segments;
		}

		public void setCount(long count) {
			this.count = count;
		}

		public void setShards(Shards shards) {
			this.shards = shards;
		}

		public void setDocs(Docs docs) {
			this.docs = docs;
		}

		public void setSegments(Segments segments) {
			this.segments = segments;
		}

		class Shards {
			
			@SerializedName(value = "total")
			private long total;
			
			@SerializedName(value = "primaries")
			private long primaries;
			
			@SerializedName(value = "replication")
			private float replication;
			
			@SerializedName(value = "index")
			private Index index;
			
			public long getTotal() {
				return total;
			}

			public long getPrimaries() {
				return primaries;
			}

			public float getReplication() {
				return replication;
			}

			public Index getIndex() {
				return index;
			}

			public void setTotal(long total) {
				this.total = total;
			}

			public void setPrimaries(long primaries) {
				this.primaries = primaries;
			}

			public void setReplication(float replication) {
				this.replication = replication;
			}

			public void setIndex(Index index) {
				this.index = index;
			}

			class Index {
				
				@SerializedName(value = "shards")
				private Idx shards;
				
				@SerializedName(value = "primaries")
				private Idx primaries;
				
				@SerializedName(value = "replication")
				private Idx replication;
				
				public Idx getShards() {
					return shards;
				}

				public Idx getPrimaries() {
					return primaries;
				}

				public Idx getReplication() {
					return replication;
				}

				public void setShards(Idx shards) {
					this.shards = shards;
				}

				public void setPrimaries(Idx primaries) {
					this.primaries = primaries;
				}

				public void setReplication(Idx replication) {
					this.replication = replication;
				}

				class Idx {
					@SerializedName(value = "min")
					private float min;
					
					@SerializedName(value = "max")
					private float max;
					
					@SerializedName(value = "avg")
					private float avg;

					public float getMin() {
						return min;
					}

					public float getMax() {
						return max;
					}

					public float getAvg() {
						return avg;
					}

					public void setMin(float min) {
						this.min = min;
					}

					public void setMax(float max) {
						this.max = max;
					}

					public void setAvg(float avg) {
						this.avg = avg;
					}
				}
				
			}
			
		}
		
		class Docs {
			
			@SerializedName(value = "count")
			private long count;
			
			@SerializedName(value = "deleted")
			private long deleted;

			public long getCount() {
				return count;
			}

			public long getDeleted() {
				return deleted;
			}

			public void setCount(long count) {
				this.count = count;
			}

			public void setDeleted(long deleted) {
				this.deleted = deleted;
			}
			
		}
		
		class Segments {
			
			@SerializedName(value = "count")
			private long count;
			
			@SerializedName(value = "memory_in_bytes")
			private long memoryInBytes;
			
			@SerializedName(value = "terms_memory_in_bytes")
			private long termsMemoryInBytes;
			
			@SerializedName(value = "stored_fields_memory_in_bytes")
			private long storedFieldsMemoryInBytes;
			
			@SerializedName(value = "term_vectors_memory_in_bytes")
			private long term_vectorsMemoryInBytes;
			
			@SerializedName(value = "norms_memory_in_bytes")
			private long normsMemoryInBytes;
			
			@SerializedName(value = "points_memory_in_bytes")
			private long pointsMemoryInBytes;
			
			@SerializedName(value = "doc_values_memory_in_bytes")
			private long docValuesMemoryInBytes;
			
			@SerializedName(value = "index_writer_memory_in_bytes")
			private long indexWriterMemoryInBytes;
			
			@SerializedName(value = "version_map_memory_in_bytes")
			private long versionMapMemoryInBytes;
			
			@SerializedName(value = "fixed_bit_set_memory_in_bytes")
			private long fixedBitSetMemoryInBytes;
			
			@SerializedName(value = "max_unsafe_auto_id_timestamp")
			private long maxUnsafeAutoIdTimestamp;
			
			@SerializedName(value = "file_sizes")
			private Object fileSizes;

			public long getCount() {
				return count;
			}

			public long getMemoryInBytes() {
				return memoryInBytes;
			}

			public long getTermsMemoryInBytes() {
				return termsMemoryInBytes;
			}

			public long getStoredFieldsMemoryInBytes() {
				return storedFieldsMemoryInBytes;
			}

			public long getTerm_vectorsMemoryInBytes() {
				return term_vectorsMemoryInBytes;
			}

			public long getNormsMemoryInBytes() {
				return normsMemoryInBytes;
			}

			public long getPointsMemoryInBytes() {
				return pointsMemoryInBytes;
			}

			public long getDocValuesMemoryInBytes() {
				return docValuesMemoryInBytes;
			}

			public long getIndexWriterMemoryInBytes() {
				return indexWriterMemoryInBytes;
			}

			public long getVersionMapMemoryInBytes() {
				return versionMapMemoryInBytes;
			}

			public long getFixedBitSetMemoryInBytes() {
				return fixedBitSetMemoryInBytes;
			}

			public long getMaxUnsafeAutoIdTimestamp() {
				return maxUnsafeAutoIdTimestamp;
			}

			public Object getFileSizes() {
				return fileSizes;
			}

			public void setCount(long count) {
				this.count = count;
			}

			public void setMemoryInBytes(long memoryInBytes) {
				this.memoryInBytes = memoryInBytes;
			}

			public void setTermsMemoryInBytes(long termsMemoryInBytes) {
				this.termsMemoryInBytes = termsMemoryInBytes;
			}

			public void setStoredFieldsMemoryInBytes(long storedFieldsMemoryInBytes) {
				this.storedFieldsMemoryInBytes = storedFieldsMemoryInBytes;
			}

			public void setTerm_vectorsMemoryInBytes(long term_vectorsMemoryInBytes) {
				this.term_vectorsMemoryInBytes = term_vectorsMemoryInBytes;
			}

			public void setNormsMemoryInBytes(long normsMemoryInBytes) {
				this.normsMemoryInBytes = normsMemoryInBytes;
			}

			public void setPointsMemoryInBytes(long pointsMemoryInBytes) {
				this.pointsMemoryInBytes = pointsMemoryInBytes;
			}

			public void setDocValuesMemoryInBytes(long docValuesMemoryInBytes) {
				this.docValuesMemoryInBytes = docValuesMemoryInBytes;
			}

			public void setIndexWriterMemoryInBytes(long indexWriterMemoryInBytes) {
				this.indexWriterMemoryInBytes = indexWriterMemoryInBytes;
			}

			public void setVersionMapMemoryInBytes(long versionMapMemoryInBytes) {
				this.versionMapMemoryInBytes = versionMapMemoryInBytes;
			}

			public void setFixedBitSetMemoryInBytes(long fixedBitSetMemoryInBytes) {
				this.fixedBitSetMemoryInBytes = fixedBitSetMemoryInBytes;
			}

			public void setMaxUnsafeAutoIdTimestamp(long maxUnsafeAutoIdTimestamp) {
				this.maxUnsafeAutoIdTimestamp = maxUnsafeAutoIdTimestamp;
			}

			public void setFileSizes(Object fileSizes) {
				this.fileSizes = fileSizes;
			}
			
		}
		
	}
	
	public class Nodes {
		
		@SerializedName(value = "count")
		private Count count;
		
		@SerializedName(value = "verisons")
		private List<String> versions;
		
		@SerializedName(value = "os")
		private OS os;
		
		@SerializedName(value = "jvm")
		private JVM jvm;
		
		@SerializedName(value = "fs")
		private FS fs;
		
		public Count getCount() {
			return count;
		}

		public List<String> getVersions() {
			return versions;
		}

		public OS getOs() {
			return os;
		}

		public JVM getJvm() {
			return jvm;
		}

		public FS getFs() {
			return fs;
		}

		public void setCount(Count count) {
			this.count = count;
		}

		public void setVersions(List<String> versions) {
			this.versions = versions;
		}

		public void setOs(OS os) {
			this.os = os;
		}

		public void setJvm(JVM jvm) {
			this.jvm = jvm;
		}

		public void setFs(FS fs) {
			this.fs = fs;
		}

		class Count {
			
			@SerializedName(value = "total")
			private int total;
			
			@SerializedName(value = "data")
			private int data;
			
			@SerializedName(value = "coordinating_only")
			private int coordinatingOnly;
			
			@SerializedName(value = "master")
			private int master;
			
			@SerializedName(value = "ingest")
			private int ingest;

			public int getTotal() {
				return total;
			}

			public int getData() {
				return data;
			}

			public int getCoordinatingOnly() {
				return coordinatingOnly;
			}

			public int getMaster() {
				return master;
			}

			public int getIngest() {
				return ingest;
			}

			public void setTotal(int total) {
				this.total = total;
			}

			public void setData(int data) {
				this.data = data;
			}

			public void setCoordinatingOnly(int coordinatingOnly) {
				this.coordinatingOnly = coordinatingOnly;
			}

			public void setMaster(int master) {
				this.master = master;
			}

			public void setIngest(int ingest) {
				this.ingest = ingest;
			}
			
		}
		
		public class OS {
			
			@SerializedName(value = "available_processors")
			private int availableProcessors;
			
			@SerializedName(value = "allocated_processors")
			private int allocatedProcessors;
			
			@SerializedName(value = "names")
			private List<Names> names;
			
			@SerializedName(value = "mem")
			private Mem mem;
			
			public int getAvailableProcessors() {
				return availableProcessors;
			}

			public int getAllocatedProcessors() {
				return allocatedProcessors;
			}

			public List<Names> getNames() {
				return names;
			}

			public Mem getMem() {
				return mem;
			}

			public void setAvailableProcessors(int availableProcessors) {
				this.availableProcessors = availableProcessors;
			}

			public void setAllocatedProcessors(int allocatedProcessors) {
				this.allocatedProcessors = allocatedProcessors;
			}

			public void setNames(List<Names> names) {
				this.names = names;
			}

			public void setMem(Mem mem) {
				this.mem = mem;
			}

			class Names {
				
				@SerializedName(value = "name")
				private String name;
				
				@SerializedName(value = "count")
				private int count;

				public String getName() {
					return name;
				}

				public int getCount() {
					return count;
				}

				public void setName(String name) {
					this.name = name;
				}

				public void setCount(int count) {
					this.count = count;
				}
				
			}
			
			class Mem {
				
				@SerializedName(value = "total_in_bytes")
				protected long totalInBytes;

				@SerializedName(value = "free_in_bytes")
				protected long freeInBytes;

				@SerializedName(value = "used_in_bytes")
				protected long usedInBytes;
				
				@SerializedName(value = "free_percent")
				private int freePercent;

				@SerializedName(value = "used_percent")
				private int userPercent;

				public long getTotalInBytes() {
					return totalInBytes;
				}

				public long getFreeInBytes() {
					return freeInBytes;
				}

				public long getUsedInBytes() {
					return usedInBytes;
				}

				public int getFreePercent() {
					return freePercent;
				}

				public int getUserPercent() {
					return userPercent;
				}

				public void setTotalInBytes(long totalInBytes) {
					this.totalInBytes = totalInBytes;
				}

				public void setFreeInBytes(long freeInBytes) {
					this.freeInBytes = freeInBytes;
				}

				public void setUsedInBytes(long usedInBytes) {
					this.usedInBytes = usedInBytes;
				}

				public void setFreePercent(int freePercent) {
					this.freePercent = freePercent;
				}

				public void setUserPercent(int userPercent) {
					this.userPercent = userPercent;
				}	
				
			}
			
		}
		
		class JVM {
			
			@SerializedName(value = "max_uptime_in_millis")
			private long maxUptimeInMillis;
			
			@SerializedName(value = "versions")
			private List<Version> versions;
			
			@SerializedName(value = "mem")
			private Mem mem;
			
			@SerializedName(value = "threads")
			private int threads;
			
			public long getMaxUptimeInMillis() {
				return maxUptimeInMillis;
			}

			public List<Version> getVersions() {
				return versions;
			}

			public Mem getMem() {
				return mem;
			}

			public int getThreads() {
				return threads;
			}

			public void setMaxUptimeInMillis(long maxUptimeInMillis) {
				this.maxUptimeInMillis = maxUptimeInMillis;
			}

			public void setVersions(List<Version> versions) {
				this.versions = versions;
			}

			public void setMem(Mem mem) {
				this.mem = mem;
			}

			public void setThreads(int threads) {
				this.threads = threads;
			}

			class Version {
				
				@SerializedName(value = "version")
				private String version;
				
				@SerializedName(value = "vm_name")
				private String vmName;
				
				@SerializedName(value = "vm_version")
				private String vmVersion;
				
				@SerializedName(value = "vm_vendor")
				private String vmVendor;
				
				@SerializedName(value = "count")
				private int count;

				public String getVersion() {
					return version;
				}

				public String getVmName() {
					return vmName;
				}

				public String getVmVersion() {
					return vmVersion;
				}

				public String getVmVendor() {
					return vmVendor;
				}

				public int getCount() {
					return count;
				}

				public void setVersion(String version) {
					this.version = version;
				}

				public void setVmName(String vmName) {
					this.vmName = vmName;
				}

				public void setVmVersion(String vmVersion) {
					this.vmVersion = vmVersion;
				}

				public void setVmVendor(String vmVendor) {
					this.vmVendor = vmVendor;
				}

				public void setCount(int count) {
					this.count = count;
				}
				
			}
			
			class Mem {
				
				@SerializedName(value = "heap_used_in_bytes")
				private long heapUsedInBytes;

				@SerializedName(value = "heap_max_in_bytes")
				private long heapMaxInBytes;

				public long getHeapUsedInBytes() {
					return heapUsedInBytes;
				}

				public long getHeapMaxInBytes() {
					return heapMaxInBytes;
				}

				public void setHeapUsedInBytes(long heapUsedInBytes) {
					this.heapUsedInBytes = heapUsedInBytes;
				}

				public void setHeapMaxInBytes(long heapMaxInBytes) {
					this.heapMaxInBytes = heapMaxInBytes;
				}
				
			}
			
		}
		
		class FS {
			
			@SerializedName(value = "total_in_bytes")
			protected long totalInBytes;

			@SerializedName(value = "free_in_bytes")
			protected long freeInBytes;

			@SerializedName(value = "used_in_bytes")
			protected long usedInBytes;

			public long getTotalInBytes() {
				return totalInBytes;
			}

			public long getFreeInBytes() {
				return freeInBytes;
			}

			public long getUsedInBytes() {
				return usedInBytes;
			}

			public void setTotalInBytes(long totalInBytes) {
				this.totalInBytes = totalInBytes;
			}

			public void setFreeInBytes(long freeInBytes) {
				this.freeInBytes = freeInBytes;
			}

			public void setUsedInBytes(long usedInBytes) {
				this.usedInBytes = usedInBytes;
			}
			
		}
		
	}
	
}
