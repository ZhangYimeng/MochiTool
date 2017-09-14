package mochi.tool.net.sustainingconnector.foundation;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import mochi.tool.data.interconversion.DataInterconversionTool;

public class DataComingHandlerThread extends Thread {
	
	private Socket s;
	private DataInputStream din;
	private DataOutputStream dout;
	private int statusFlag;
	private byte[] dataLength = new byte[2];
	private byte[] data;
	private Class<?> c;
	
	public DataComingHandlerThread(Socket s, String nameOfDataHandler) throws IOException {
		this.s = s;
		din = new DataInputStream(s.getInputStream());
		dout = new DataOutputStream(s.getOutputStream());
		try {
			c = Class.forName(nameOfDataHandler);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			while((statusFlag = din.readInt()) != ConnterProperty.DISCONNECT) {
				switch(statusFlag) {
				case ConnterProperty.HEART_BEAT:
					System.out.println("收到ID为：" + this.getName() + "会话的心跳信息。");
					break;
				case ConnterProperty.KEEP_LISTEN:
					System.out.println("收到ID为：" + this.getName() + "会话的发送数据。");
					din.read(dataLength);
					data = new byte[DataInterconversionTool.bytesToShort(dataLength)];
					din.read(data);
					DataHandlerInterface dhi = (DataHandlerInterface) c.newInstance();
					dhi.setBytesData(data);
					dhi.setReplier(dout);
					Thread it = new Thread(dhi);
					it.start();
					break;
				case ConnterProperty.TRY_CONNECT:
					System.out.println("收到ID为：" + this.getName() + "会话的连接请求。");
					dout.writeInt(ConnterProperty.CONNECT_SUCCESS);
					break;
				}
			}
			System.out.println("收到ID为：" + this.getName() + "会话的断开连接请求。");
			dout.writeInt(ConnterProperty.DISCONNECT_SUCCESS);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			din.close();
			dout.close();
			s.close();
		} catch (IOException e) {
			System.out.println("该链接断开，所有相关实例已被抛弃。");
			try {
				din.close();
				dout.close();
				s.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
