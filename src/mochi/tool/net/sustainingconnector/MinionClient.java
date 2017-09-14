package mochi.tool.net.sustainingconnector;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import mochi.tool.data.interconversion.DataInterconversionTool;
import mochi.tool.net.sustainingconnector.foundation.ConnterProperty;
import mochi.tool.net.sustainingconnector.foundation.DataFrame;
import mochi.tool.util.task.ScheduledTask;

public class MinionClient {

	private Socket s;
	private DataInputStream din;
	private DataOutputStream dout;
	private byte[] feedbackLength = new byte[2];
	private byte[] feedback;
	private HeartBeatThread hbt;
	
	public MinionClient(String ip, int port) throws UnknownHostException, IOException {
		s = new Socket(ip, port);
		din = new DataInputStream(s.getInputStream());
		dout = new DataOutputStream(s.getOutputStream());
	}
	
	public synchronized int connect() {
		try {
			dout.writeInt(ConnterProperty.TRY_CONNECT);
			return din.readInt();
		} catch (IOException e) {
			e.printStackTrace();
			return ConnterProperty.CONNECT_FAIL;
		}
	}
	
	public synchronized byte[] sendData(DataFrame data) {
		try {
			dout.writeInt(ConnterProperty.KEEP_LISTEN);
			dout.write(data.getDataBytes());
			din.read(feedbackLength);
			feedback = new byte[DataInterconversionTool.bytesToShort(feedbackLength)];
			din.read(feedback);
			return feedback;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public synchronized int disconnect() {
		try {
			dout.writeInt(ConnterProperty.DISCONNECT);
			hbt.cancel();
			int feedback = din.readInt();
			din.close();
			dout.close();
			s.close();
			return feedback;
		} catch (IOException e) {
			e.printStackTrace();
			return ConnterProperty.DISCONNECT_FAIL;
		}
	}
	
	public void openHeartBeat() {
		hbt = new HeartBeatThread();
		hbt.schedule(hbt, 0, 500);
	}
	
	private class HeartBeatThread extends ScheduledTask {

		public HeartBeatThread() {
			
		}
		
		@Override
		public void run() {
			try {
				synchronized(this) {
					dout.writeInt(ConnterProperty.HEART_BEAT);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
