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
	
	class Indices {
		
		@SerializedName(value = "count")
		private long count;
		
		@SerializedName(value = "shards")
		private Shards shards;
		
		@SerializedName(value = "docs")
		private Docs docs;
		
		@SerializedName(value = "segments")
		private Segments segments;
		
		class Shards {
			
			@SerializedName(value = "total")
			private long total;
			
			@SerializedName(value = "primaries")
			private long primaries;
			
			@SerializedName(value = "replication")
			private float replication;
			
			@SerializedName(value = "index")
			private Index index;
			
			class Index {
				
				@SerializedName(value = "shards")
				private Idx shards;
				
				@SerializedName(value = "primaries")
				private Idx primaries;
				
				@SerializedName(value = "replication")
				private Idx replication;
				
				class Idx {
					@SerializedName(value = "min")
					private float min;
					
					@SerializedName(value = "max")
					private float max;
					
					@SerializedName(value = "avg")
					private float avg;
				}
				
			}
			
		}
		
		class Docs {
			
			@SerializedName(value = "count")
			private long count;
			
			@SerializedName(value = "deleted")
			private long deleted;
			
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
			private String fileSizes;
			
		}
		
	}
	
	class Nodes {
		
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
			
		}
		
		class OS {
			
			@SerializedName(value = "available_processors")
			private int availableProcessors;
			
			@SerializedName(value = "allocated_processors")
			private int allocatedProcessors;
			
			@SerializedName(value = "names")
			private List<Names> names;
			
			@SerializedName(value = "mem")
			private Mem mem;
			
			class Names {
				
				@SerializedName(value = "name")
				private String name;
				
				@SerializedName(value = "count")
				private int count;
				
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
				
			}
			
			class Mem {
				
				@SerializedName(value = "heap_used_in_bytes")
				private long heapUsedInBytes;

				@SerializedName(value = "heap_max_in_bytes")
				private long heapMaxInBytes;
				
			}
			
		}
		
		class FS {
			
			@SerializedName(value = "total_in_bytes")
			protected long totalInBytes;

			@SerializedName(value = "free_in_bytes")
			protected long freeInBytes;

			@SerializedName(value = "used_in_bytes")
			protected long usedInBytes;
			
		}
		
	}
	
}
