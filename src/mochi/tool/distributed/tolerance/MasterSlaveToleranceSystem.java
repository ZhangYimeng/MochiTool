package mochi.tool.distributed.tolerance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class MasterSlaveToleranceSystem { 
	
	private static Properties properties;
	private static final String MASTER_IP = "master.ip";
	private static final String MASTER_PORT = "master.port";
	private static final String TOLERANCE_SYSTEM_MODE = "tolerance.system.mode";
	@SuppressWarnings("unused")
	private static String master_ip;
	@SuppressWarnings("unused")
	private static int master_port;
	private static String tolerance_system_mode;
	
	public static void start() {
		try {
			properties = new Properties();
			properties.load(new FileInputStream(new File(new File("").getAbsolutePath() + "/conf/MasterSlaveToleranceSystem.properties")));
			master_ip = (String) properties.get(MASTER_IP);
			master_port = Integer.parseInt((String) properties.get(MASTER_PORT));
			tolerance_system_mode = (String) properties.get(TOLERANCE_SYSTEM_MODE);
			if(tolerance_system_mode.equals("master")) {
				
			} else {
				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		start();
	}
	
}
