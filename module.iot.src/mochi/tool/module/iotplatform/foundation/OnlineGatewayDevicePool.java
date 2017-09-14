package mochi.tool.module.iotplatform.foundation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import mochi.tool.module.iotplatform.foundation.exception.DeviceNotExistsException;
import mochi.tool.module.iotplatform.foundation.exception.DeviceNotOnlineException;
import mochi.tool.module.iotplatform.foundation.exception.UsernameWrongException;

public class OnlineGatewayDevicePool {
	
	private static HashMap<String, OnlineDeviceInfo> onlineDeviceCollection = new HashMap<String, OnlineDeviceInfo>();
	private static class OnlineCheck implements Runnable {

		@Override
		public void run() {
			Timer timer = new Timer();
			TimerTask tt = new TimerTask(){
				@Override
				public void run() {
					System.out.println("GatewayDeviceOnlineCheckThread is running.");
					Iterator<OnlineDeviceInfo> it = onlineDeviceCollection.values().iterator();
					while(it.hasNext()) {
						OnlineDeviceInfo flag = it.next();
						if(flag.getFlag()) {
							flag.setFlag(false);
							System.out.println(flag.getDeviceID() + "标记false");
						} else {
							onlineDeviceCollection.remove(flag.getDeviceID());
							System.out.println(flag.getDeviceID() + "被移出了设备Cache！");
						}
					}
				}
			};
			timer.schedule(tt, 100, 120000);
		}
		
	}
	
	private static class OnlineDeviceInfo {
		
		private String username;
		private String deviceID;
		private boolean flag;
		private Long interval;
		
		public OnlineDeviceInfo(String username, String deviceID) throws DeviceNotExistsException, UsernameWrongException {
			this.username = username;
			this.deviceID = deviceID;
			flag = true;
			interval = GatewayDevice.queryDeviceInterval(username, deviceID);
		}
		
		@SuppressWarnings("unused")
		public String getUsername() {
			return username;
		}
		
		public void setFlag(boolean value) {
			flag = value;
		}
		
		public boolean getFlag() {
			return flag;
		}
		
		public String getDeviceID() {
			return deviceID;
		}
		
		public Long getInterval() {
			Long intervalCopy = interval;
			interval = null;
			return intervalCopy;
		}
		
		public void setInterval(long interval) {
			this.interval = interval;
		}
		
	}
	
	static {
		new Thread(new OnlineCheck()).start();
	}

	public static void setDeviceInterval(String deviceID, long interval) throws DeviceNotOnlineException {
		OnlineDeviceInfo flag = onlineDeviceCollection.get(deviceID);
		if(flag != null) {
			flag.setInterval(interval);
		} else {
			throw new DeviceNotOnlineException();
		}
	}
	
	public static Long tryToGetDeviceInterval(String deviceID) throws DeviceNotOnlineException {
		OnlineDeviceInfo flag = onlineDeviceCollection.get(deviceID);
		if(flag != null) {
			return flag.getInterval();
		} else {
			throw new DeviceNotOnlineException();
		}
	}
	
	public static boolean onlineCheck(String deviceID) {
		return onlineDeviceCollection.containsKey(deviceID)? true: false;
	}
	
	/**
	 * 将设备存入在线设备缓存，如果设备已存在则重新设置设备标记。
	 * @param deviceID
	 * @throws UsernameWrongException 
	 * @throws DeviceNotExistsException 
	 */
	public static void putDeviceIntoDevicePool(String username, String deviceID) throws DeviceNotExistsException, UsernameWrongException {
		OnlineDeviceInfo flag = onlineDeviceCollection.get(deviceID);
		if(flag == null) {
			onlineDeviceCollection.put(deviceID, new OnlineDeviceInfo(username, deviceID));
		} else {
			flag.setFlag(true);
		}
	}
	
}
