package atest;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

public class 检测硬件使用率情况 {

	public static void main(String[] args) {
		OperatingSystemMXBean osmb = (OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
		System.out.println(osmb.getArch());
		System.out.println(osmb.getVersion());
		System.out.println(osmb.getName());
		System.out.println(osmb.getAvailableProcessors());
		System.out.println(osmb.getSystemLoadAverage());
		System.out.println(osmb.getObjectName());
		/*while(true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(osmb.getSystemLoadAverage());
		}*/
		com.sun.management.OperatingSystemMXBean osmb1 = (com.sun.management.OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
		System.out.println(osmb1.getArch());
		System.out.println(osmb1.getCommittedVirtualMemorySize());
		System.out.println(osmb1.getFreePhysicalMemorySize());
		System.out.println(osmb1.getProcessCpuLoad());
		System.out.println(osmb1.getSystemCpuLoad());
		while(true) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(osmb1.getSystemCpuLoad());
		}
	}

}
