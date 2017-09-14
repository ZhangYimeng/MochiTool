package mochi.tool.module.iotplatform.foundation.dataanalyse;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import mochi.tool.data.interconversion.DataInterconversionTool;

public class MessageReceiverAndSender {

	private DataInputStream in;
	private DataOutputStream out;
	
	public MessageReceiverAndSender(Socket socket) {
		try {
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public MessageReader getMessageReader() {
		byte[] length = new byte[2];
		try {
			in.read(length);
		} catch (IOException e) {
			e.printStackTrace();
		}
		short totalLength = DataInterconversionTool.bytesToShort(length);
		System.out.println("收到的报文长度为" + totalLength);
		byte[] message = new byte[totalLength - 2];
		try {
			in.read(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
		MessageReader mr = new MessageReader(totalLength, message);
		return mr;
	}
	
	public void sendResponse(byte[] response) {
		try {
			out.write(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
