package atest;

import mochi.tool.hardware.supervision.CPUSupervisor;

public class CPUCoresTest {

	public static void main(String[] args) {
		CPUSupervisor cpu = new CPUSupervisor();
		System.out.println(cpu.getCPUCores());
	}

}
