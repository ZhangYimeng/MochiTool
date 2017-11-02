package mochi.tool.net.server.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import mochi.tool.data.bytescombinetool.BytesCombineTool;
import mochi.tool.data.interconversion.DataInterconversionTool;

public class SocketHandler {

	private Socket socket;
	private SocketHandlerInterface handler;

	public SocketHandler(Socket socket, SocketHandlerInterface handler) {
		this.socket = socket;
		this.handler = handler;
	}

	public void doBusiness() throws IOException {
		Thread supervisorIn = new Thread(new SupervisorIn(this));
		supervisorIn.start();
	}

	private class SupervisorIn implements Runnable {

		private DataInputStream din;
		private SupervisorOut supervisorOut;

		public SupervisorIn(SocketHandler self) throws IOException {
			din = new DataInputStream(socket.getInputStream());
			supervisorOut = new SupervisorOut();
		}

		@Override
		public void run() {
			while (true) {
				try {
					byte[] lengthBytes = new byte[2];
					din.read(lengthBytes);
					short length = DataInterconversionTool.bytesToShort(lengthBytes);
					if (length != 0) {
						byte[] data = new byte[length - 2];
						din.read(data);
						byte[] feedback;
						if(handler != null) {
							feedback = handler.handle(data);
						} else {
							feedback = new byte[0];
						}
						supervisorOut.feedback(BytesCombineTool
								.append(DataInterconversionTool.shortToBytes((short) (feedback.length + 2)), feedback));
					} else {
						throw new IOException();
					}
				} catch (IOException e) {
					try {
						if(handler != null) {
							handler.whenConnectionClose();
						}
						din.close();
						supervisorOut.close();
						socket.close();
						System.out.println("A Client close connection!");
						break;
					} catch (IOException e1) {
						System.out.println("A Client close connection!");
						e1.printStackTrace();
						break;
					}
				}
			}
		}
	}

	private class SupervisorOut {

		private DataOutputStream dout;

		public SupervisorOut() throws IOException {
			dout = new DataOutputStream(socket.getOutputStream());
		}

		public void feedback(byte[] data) throws IOException {
			dout.write(data);
		}

		public void close() throws IOException {
			dout.close();
		}

	}

}
