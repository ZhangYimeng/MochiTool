package mochi.tool.net.environment;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.LinkedList;

public class HostIPViwer {
	
	private LinkedList<String> ipList;
	
	public HostIPViwer() {
		this.ipList = new LinkedList<String>();
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {  
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress()) {
						this.ipList.add(inetAddress.getHostAddress().toString());
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public LinkedList<String> getHostIP() {
		return this.ipList;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(String s: this.ipList) {
			sb.append(s + "\n");
		}
		return sb.toString();
	}
	
}
