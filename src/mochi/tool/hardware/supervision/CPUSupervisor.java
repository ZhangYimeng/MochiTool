package mochi.tool.hardware.supervision;

import java.lang.management.ManagementFactory;

import com.sun.management.OperatingSystemMXBean;

public class CPUSupervisor {
	
	private OperatingSystemMXBean osmb;
	
	public CPUSupervisor() {
		this.osmb = (OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
	}
	
	public double getSystemCpuLoadInfo() {
		return this.osmb.getSystemCpuLoad();
	}
	
	public double getProcessCpuLoadInfo() {
		return this.osmb.getProcessCpuLoad();
	}
	
	public int getCPUCores() {
		return Runtime.getRuntime().availableProcessors();
	}
	
}
