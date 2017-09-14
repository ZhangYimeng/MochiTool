package mochi.tool.module.iotplatform.open.api.datatool;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import mochi.tool.data.interconversion.DataInterconversionTool;
import mochi.tool.module.iotplatform.open.api.connector.MochiGatewayConnector;
import mochi.tool.module.iotplatform.open.api.exception.BodyLengthZeroException;
import mochi.tool.util.task.ScheduledTask;

public class MochiGatewayHeartBeatScheduledTask extends ScheduledTask implements Runnable{
	
	private static String username;
	private static String password;
	private static String token;
	private static String deviceID;
	private static boolean loginStatus = false;
	private static MessageHeadGenerator mh;
	private static MessageBodyGenerator mb;
	private static MessageGenerator mg;
	private static MessageResponse mr;
	
	private static MochiGatewayConnector conn;
	private static Socket socket;
	private static DataInputStream din;
	private static DataOutputStream dout;
	private static byte[] feedbackLengthBytes = new byte[2];
	private static short feedbackLength;
	private static byte[] feedback;
	
	private static long interval;
	
	public MochiGatewayHeartBeatScheduledTask(MochiGatewayConnector conn) {
		MochiGatewayHeartBeatScheduledTask.conn = conn;
	}
	
	public MochiGatewayHeartBeatScheduledTask(String username, String password, String token, String deviceID) {
		MochiGatewayHeartBeatScheduledTask.username = username;
		MochiGatewayHeartBeatScheduledTask.password = password;
		MochiGatewayHeartBeatScheduledTask.token = token;
		MochiGatewayHeartBeatScheduledTask.deviceID = deviceID;
	}

	public static String getDeviceID() {
		return deviceID;
	}

	public static void setDeviceID(String deviceID) {
		MochiGatewayHeartBeatScheduledTask.deviceID = deviceID;
	}

	public static String getToken() {
		return token;
	}

	public static void setToken(String token) {
		MochiGatewayHeartBeatScheduledTask.token = token;
	}

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		MochiGatewayHeartBeatScheduledTask.username = username;
	}
	
	public static void setPassword(String password) {
		MochiGatewayHeartBeatScheduledTask.password = password;
	}

	private int checkReady() {
		if((username != null) && (token != null) && (password != null) && (deviceID != null)) {
			this.reschedule(DataConfig.HEART_BEAT_INTERVAL);
			System.out.println("Ready check is 1");
			return 1;
		} else if((username != null) && (token != null) && (password != null) && (deviceID == null)) {
			this.reschedule(DataConfig.HEART_BEAT_INTERVAL);
			System.out.println("Ready check is 2");
			return 2;
		} else {
			this.reschedule(0);
			System.out.println("Ready check is 0");
			return 0;
		}
	}
	
	public void tryReactiveTaskLoop() {
		this.reschedule(DataConfig.HEART_BEAT_INTERVAL);
	}
	
	@Override
	public void run() {
		try {
			switch(checkReady()) {
			case 1:
				if(loginStatus) {
					mh = new MessageHeadGenerator();
					mb = new MessageBodyGenerator();
					mh.generate(MessageProtocolConfig.COMMAND_ID_DEVICE_HEART_BEAT, System.currentTimeMillis());
					mb.append(MessageProtocolConfig.X0001, DataInterconversionTool.stringToBytes(username));
					mb.append(MessageProtocolConfig.X0004, DataInterconversionTool.stringToBytes(token));
					mb.append(MessageProtocolConfig.X000D, DataInterconversionTool.stringToBytes(deviceID));
					mg = new MessageGenerator(mh, mb);
					socket = new Socket(conn.getIP(), conn.getPort());
					din = new DataInputStream(socket.getInputStream());
					dout = new DataOutputStream(socket.getOutputStream());
					dout.write(mg.getMessageBytes());
					din.read(feedbackLengthBytes);
					feedbackLength = DataInterconversionTool.bytesToShort(feedbackLengthBytes);
					feedback = new byte[feedbackLength];
					din.read(feedback);
					mr = new MessageResponse(feedbackLength, feedback);
					din.close();
					dout.close();
					socket.close();
					ResponseBody rb = mr.getResponseBody();
					while(rb.next()) {
						switch (rb.getCurrentTag()) {
						case MessageProtocolConfig.X1001:
							System.out.println(DataInterconversionTool.bytesToString(rb.getCurrentValue()));
							break;
						case MessageProtocolConfig.X1002:
							System.out.println(DataInterconversionTool.bytesToString(rb.getCurrentValue()));
							break;
						case MessageProtocolConfig.X0014:
							interval = DataInterconversionTool.bytesToLong(rb.getCurrentValue());
							MochiGatewayObserverBetweenHeartBeatAndUploadDataTask.notifyUploadDataTaskGetInterval();
						default:
							break;
						}
					}
				} else {
					mh = new MessageHeadGenerator();
					mb = new MessageBodyGenerator();
					mh.generate(MessageProtocolConfig.COMMAND_ID_USER_LOGIN, System.currentTimeMillis());
					mb.append(MessageProtocolConfig.X0001, DataInterconversionTool.stringToBytes(username));
					mb.append(MessageProtocolConfig.X0002, DataInterconversionTool.stringToBytes(password));
					mg = new MessageGenerator(mh, mb);
					System.out.println("用户登录信息准备完毕");
					socket = new Socket(conn.getIP(), conn.getPort());
					din = new DataInputStream(socket.getInputStream());
					dout = new DataOutputStream(socket.getOutputStream());
					System.out.println("发送的报文长度为" + mg.getTotalLength());
					dout.write(mg.getMessageBytes());
					din.read(feedbackLengthBytes);
					feedbackLength = DataInterconversionTool.bytesToShort(feedbackLengthBytes);
					feedback = new byte[feedbackLength];
					din.read(feedback);
					mr = new MessageResponse(feedbackLength, feedback);
					din.close();
					dout.close();
					socket.close();
					ResponseBody rb2 = mr.getResponseBody();
					while(rb2.next()) {
						switch (rb2.getCurrentTag()) {
						case MessageProtocolConfig.X1001:
							String responseCode = DataInterconversionTool.bytesToString(rb2.getCurrentValue());
							if(responseCode.equals(MessageProtocolConfig.SUCCEED)) {
								loginStatus = true;
								MochiGatewayConnector.setUserLoginStatus(true);
								System.out.println("登陆成功");
							} else {
								loginStatus = false;
								MochiGatewayConnector.setUserLoginStatus(false);
								System.out.println("登陆失败，响应码为：" + responseCode);
							}
							break;
						case MessageProtocolConfig.X1002:
							System.out.println(DataInterconversionTool.bytesToString(rb2.getCurrentValue()));
							break;
						default:
							break;
						}
					}
				}
				break;
			case 2:
				if(loginStatus) {
					mh = new MessageHeadGenerator();
					mb = new MessageBodyGenerator();
					mh.generate(MessageProtocolConfig.COMMAND_ID_USER_HEART_BEAT, System.currentTimeMillis());
					mb.append(MessageProtocolConfig.X0001, DataInterconversionTool.stringToBytes(username));
					mb.append(MessageProtocolConfig.X0004, DataInterconversionTool.stringToBytes(token));
					mg = new MessageGenerator(mh, mb);
					System.out.println("用户心跳信息准备完毕");
					socket = new Socket(conn.getIP(), conn.getPort());
					din = new DataInputStream(socket.getInputStream());
					dout = new DataOutputStream(socket.getOutputStream());
					System.out.println("发送的报文长度为" + mg.getTotalLength());
					dout.write(mg.getMessageBytes());
					din.read(feedbackLengthBytes);
					feedbackLength = DataInterconversionTool.bytesToShort(feedbackLengthBytes);
					feedback = new byte[feedbackLength];
					din.read(feedback);
					mr = new MessageResponse(feedbackLength, feedback);
					din.close();
					dout.close();
					socket.close();
					ResponseBody rb2 = mr.getResponseBody();
					while(rb2.next()) {
						switch (rb2.getCurrentTag()) {
						case MessageProtocolConfig.X1001:
							String responseCode = DataInterconversionTool.bytesToString(rb2.getCurrentValue());
							if(responseCode.equals(MessageProtocolConfig.SUCCEED)) {
								System.out.println("状态码返回值：" + responseCode + "\n" + "用户认证成功！");
							} else {
								System.out.println("状态码返回值：" + responseCode);
								loginStatus = false;
							}
							System.out.println(DataInterconversionTool.bytesToString(rb2.getCurrentValue()));
							break;
						case MessageProtocolConfig.X1002:
							System.out.println(DataInterconversionTool.bytesToString(rb2.getCurrentValue()));
							break;
						default:
							break;
						}
					}
				} else {
					mh = new MessageHeadGenerator();
					mb = new MessageBodyGenerator();
					mh.generate(MessageProtocolConfig.COMMAND_ID_USER_LOGIN, System.currentTimeMillis());
					mb.append(MessageProtocolConfig.X0001, DataInterconversionTool.stringToBytes(username));
					mb.append(MessageProtocolConfig.X0002, DataInterconversionTool.stringToBytes(password));
					mg = new MessageGenerator(mh, mb);
					System.out.println("用户登录信息准备完毕");
					socket = new Socket(conn.getIP(), conn.getPort());
					din = new DataInputStream(socket.getInputStream());
					dout = new DataOutputStream(socket.getOutputStream());
					System.out.println("发送的报文长度为" + mg.getTotalLength());
					dout.write(mg.getMessageBytes());
					din.read(feedbackLengthBytes);
					feedbackLength = DataInterconversionTool.bytesToShort(feedbackLengthBytes);
					feedback = new byte[feedbackLength];
					din.read(feedback);
					mr = new MessageResponse(feedbackLength, feedback);
					din.close();
					dout.close();
					socket.close();
					ResponseBody rb2 = mr.getResponseBody();
					while(rb2.next()) {
						switch (rb2.getCurrentTag()) {
						case MessageProtocolConfig.X1001:
							String responseCode = DataInterconversionTool.bytesToString(rb2.getCurrentValue());
							if(responseCode.equals(MessageProtocolConfig.SUCCEED)) {
								loginStatus = true;
								MochiGatewayConnector.setUserLoginStatus(true);
								System.out.println("登陆成功");
							} else {
								loginStatus = false;
								MochiGatewayConnector.setUserLoginStatus(false);
								System.out.println("登陆失败，响应码为：" + responseCode);
							}
							break;
						case MessageProtocolConfig.X1002:
							System.out.println(DataInterconversionTool.bytesToString(rb2.getCurrentValue()));
							break;
						default:
							break;
						}
					}
				}
				break;
			case 0:
				System.out.println("发送用户心跳信息缺少必要信息");
				this.reschedule(0);
				break;
			}
		} catch(IOException e) {
			e.printStackTrace();
		} catch (BodyLengthZeroException e) {
			e.printStackTrace();
		}
	}

	public long getInterval() {
		return interval;
	}
	
}
