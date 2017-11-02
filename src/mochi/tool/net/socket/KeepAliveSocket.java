package mochi.tool.net.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import mochi.tool.data.bytescombinetool.BytesCombineTool;
import mochi.tool.data.interconversion.DataInterconversionTool;

public class KeepAliveSocket {

	private Socket socket;
	private DataInputStream din;
	private DataOutputStream dout;
	
	public KeepAliveSocket(String ip, int port) throws UnknownHostException, IOException {
		socket = new Socket(ip, port);
		din = new DataInputStream(socket.getInputStream());
		dout = new DataOutputStream(socket.getOutputStream());
	}

	public byte[] fire(MessageBullet bullet) throws IOException {
		byte[] message = bullet.toBytes();
		dout.write(BytesCombineTool.append(DataInterconversionTool.shortToBytes((short) (message.length + 2)), message));
		byte[] feedbackLengthBytes = new byte[2];
		din.read(feedbackLengthBytes);
		short feedbackLength = DataInterconversionTool.bytesToShort(feedbackLengthBytes);
		byte[] feedbackBytes = new byte[feedbackLength - 2];
		din.read(feedbackBytes);
		return feedbackBytes;
	}
	
	public void close() throws IOException {
		din.close();
		dout.close();
		socket.close();
	}
	
}
