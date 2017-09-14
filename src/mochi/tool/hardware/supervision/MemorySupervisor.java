package mochi.tool.hardware.supervision;

public class MemorySupervisor {

	private Runtime runtime;
	
	public MemorySupervisor() {
		this.runtime = Runtime.getRuntime();
	}
	
	public long getTotalMemory() {
		return this.runtime.totalMemory();
	}
	
	public long getFreeMemory() {
		return this.runtime.freeMemory();
	}
	
	public long getMemoryUsage() {
		return this.runtime.totalMemory() - this.runtime.freeMemory();
	}
	
}
