/**
 * @author Yimeng Zhang
 * @filename Connector.java
 * @package mingmeng.openapi
 * @description 连接器。
 * @date 2015年1月21日
 */
package mochi.tool.module.iotplatform.open.api.connector;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

import mochi.tool.data.interconversion.DataInterconversionTool;
import mochi.tool.module.iotplatform.foundation.dataanalyse.MessageProtocolConfig;
import mochi.tool.module.iotplatform.open.api.datatool.DataConfig;
import mochi.tool.module.iotplatform.open.api.datatool.HeartBeatScheduledTask;
import mochi.tool.module.iotplatform.open.api.datatool.MessageResponse;
import mochi.tool.module.iotplatform.open.api.datatool.ObserverBetweenHeartBeatAndUploadDataTask;
import mochi.tool.module.iotplatform.open.api.datatool.ResponseBody;
import mochi.tool.module.iotplatform.open.api.datatool.UploadDataScheduledTask;
import mochi.tool.module.iotplatform.open.api.exception.BodyLengthZeroException;
import mochi.tool.module.iotplatform.open.api.exception.ConnectorDeviceIDNotSetException;
import mochi.tool.module.iotplatform.open.api.exception.ConnectorDeviceIDSetException;
import mochi.tool.module.iotplatform.open.api.exception.ConnectorPasswordNotSetException;
import mochi.tool.module.iotplatform.open.api.exception.ConnectorTokenNotSetException;
import mochi.tool.module.iotplatform.open.api.exception.ConnectorUsernameNotSetException;
import mochi.tool.module.iotplatform.open.api.exception.ConnectorUsernameSetException;
import mochi.tool.module.iotplatform.open.api.exception.RegisterNewDeviceCommandIDWrongException;
import mochi.tool.module.iotplatform.open.api.exception.RegisterNewUserCommandIDWrongException;

@Deprecated
public class Connector {
	
	private Socket socket;
	private DataInputStream din;
	private DataOutputStream dout;
	//private String ip;
	//private int port;
	
	private static boolean usernameHasBeenSetFlag = false;
	private static boolean userPasswordSetFlag = false;
	private static boolean tokenHasBeenSetFlag = false;
	private static boolean deviceIDHasBeenSetFlag = false;
	
	private static boolean userLoginStatus = false;
	
	private byte[] uploadDataMessage;
	
	private static UploadDataScheduledTask uploadDataTask = new UploadDataScheduledTask();
	private static HeartBeatScheduledTask heartBeatTask = new HeartBeatScheduledTask();
	
	static {
		heartBeatTask.schedule(heartBeatTask, 0l, DataConfig.HEART_BEAT_INTERVAL);
		uploadDataTask.schedule(uploadDataTask, 0l, DataConfig.HEART_BEAT_INTERVAL);
		new ObserverBetweenHeartBeatAndUploadDataTask(heartBeatTask, uploadDataTask);
	}
	
	public Connector() {
		
	}
	
	public static void setUserPassword(String password) {
		userPasswordSetFlag = true;
		HeartBeatScheduledTask.setPassword(password);
		heartBeatTask.tryReactiveTaskLoop();
	}
	
	public static void setUsername(String username) throws ConnectorUsernameSetException {
		if(usernameHasBeenSetFlag == true) {
			throw new ConnectorUsernameSetException();
		} else {
			usernameHasBeenSetFlag = true;
			HeartBeatScheduledTask.setUsername(username);
			heartBeatTask.tryReactiveTaskLoop();
		}
	}
	
	public static void setToken(String token) {
		tokenHasBeenSetFlag = true;
		HeartBeatScheduledTask.setToken(token);
		heartBeatTask.tryReactiveTaskLoop();
	}
	
	public static void setDeviceID(String deviceID) throws ConnectorDeviceIDSetException {
		if(deviceIDHasBeenSetFlag == true) {
			throw new ConnectorDeviceIDSetException();
		} else {
			deviceIDHasBeenSetFlag = true;
			HeartBeatScheduledTask.setDeviceID(deviceID);
			heartBeatTask.tryReactiveTaskLoop();
		}
	}
	
	public MessageResponse registerNewUser(byte[] message) throws UnknownHostException, IOException, RegisterNewUserCommandIDWrongException {
		short commandid = DataInterconversionTool.bytesToShort(
				Arrays.copyOfRange(message, DataConfig.TOTALLENGTH_LENGTH, 
						DataConfig.TOTALLENGTH_LENGTH + DataConfig.COMMANDID_LENGTH));
		if(commandid == MessageProtocolConfig.COMMAND_ID_USER_REGISTER) {
			socket = new Socket(OpenAPIConfig.IP, OpenAPIConfig.PORT);
			din = new DataInputStream(socket.getInputStream());
			dout = new DataOutputStream(socket.getOutputStream());
			dout.write(message);
			byte[] feedbackLengthBytes = new byte[2];
			din.read(feedbackLengthBytes);
			short feedbackLength = DataInterconversionTool.bytesToShort(feedbackLengthBytes);
			byte[] feedback = new byte[feedbackLength];
			din.read(feedback);
			MessageResponse mr = new MessageResponse(feedbackLength, feedback);
			din.close();
			dout.close();
			socket.close();
			return mr;
		} else {
			throw new RegisterNewUserCommandIDWrongException();
		}
	}
	
	public MessageResponse registerNewDevice(byte[] message) throws UnknownHostException, IOException, RegisterNewDeviceCommandIDWrongException {
		short commandid = DataInterconversionTool.bytesToShort(
				Arrays.copyOfRange(message, DataConfig.TOTALLENGTH_LENGTH, 
						DataConfig.TOTALLENGTH_LENGTH + DataConfig.COMMANDID_LENGTH));
		if(commandid == MessageProtocolConfig.COMMAND_ID_DEVICE_REGISTER) {
			socket = new Socket(OpenAPIConfig.IP, OpenAPIConfig.PORT);
			din = new DataInputStream(socket.getInputStream());
			dout = new DataOutputStream(socket.getOutputStream());
			dout.write(message);
			byte[] feedbackLengthBytes = new byte[2];
			din.read(feedbackLengthBytes);
			short feedbackLength = DataInterconversionTool.bytesToShort(feedbackLengthBytes);
			byte[] feedback = new byte[feedbackLength];
			din.read(feedback);
			MessageResponse mr = new MessageResponse(feedbackLength, feedback);
			din.close();
			dout.close();
			socket.close();
			return mr;
		} else {
			throw new RegisterNewDeviceCommandIDWrongException();
		}
	}
	
	public MessageResponse send(byte[] message) throws ConnectorUsernameNotSetException, ConnectorTokenNotSetException, ConnectorDeviceIDSetException, ConnectorDeviceIDNotSetException, ConnectorPasswordNotSetException {
		if(checkReady()) {
			try {
				short commandid = DataInterconversionTool.bytesToShort(
						Arrays.copyOfRange(message, DataConfig.TOTALLENGTH_LENGTH, 
								DataConfig.TOTALLENGTH_LENGTH + DataConfig.COMMANDID_LENGTH));
				byte[] feedbackLengthBytes;
				short feedbackLength;
				byte[] feedback;
				MessageResponse mr;
				switch(commandid) {
				case MessageProtocolConfig.COMMAND_ID_DATA_UPLOAD:
					uploadDataMessage = message;
					if(UploadDataScheduledTask.getInterval() == 0) {
						socket = new Socket(OpenAPIConfig.IP, OpenAPIConfig.PORT);
						din = new DataInputStream(socket.getInputStream());
						dout = new DataOutputStream(socket.getOutputStream());
						dout.write(message);
						feedbackLengthBytes = new byte[2];
						din.read(feedbackLengthBytes);
						feedbackLength = DataInterconversionTool.bytesToShort(feedbackLengthBytes);
						feedback = new byte[feedbackLength];
						din.read(feedback);
						mr = new MessageResponse(feedbackLength, feedback);
						din.close();
						dout.close();
						socket.close();
						return mr;
					} else {
						UploadDataScheduledTask.setUploadDataMessage(uploadDataMessage);
					}
					break;
				case MessageProtocolConfig.COMMAND_ID_REGENERATE_TOKEN:
					socket = new Socket(OpenAPIConfig.IP, OpenAPIConfig.PORT);
					din = new DataInputStream(socket.getInputStream());
					dout = new DataOutputStream(socket.getOutputStream());
					dout.write(message);
					feedbackLengthBytes = new byte[2];
					din.read(feedbackLengthBytes);
					feedbackLength = DataInterconversionTool.bytesToShort(feedbackLengthBytes);
					feedback = new byte[feedbackLength];
					din.read(feedback);
					mr = new MessageResponse(feedbackLength, feedback);
					ResponseBody rb = mr.getResponseBody();
					while(rb.next()) {
						if(rb.getCurrentTag() == MessageProtocolConfig.X0004) {
							HeartBeatScheduledTask.setToken(DataInterconversionTool.bytesToString(rb.getCurrentValue()));
						}
					}
					din.close();
					dout.close();
					socket.close();
					return mr;
				default:
					socket = new Socket(OpenAPIConfig.IP, OpenAPIConfig.PORT);
					din = new DataInputStream(socket.getInputStream());
					dout = new DataOutputStream(socket.getOutputStream());
					dout.write(message);
					feedbackLengthBytes = new byte[2];
					din.read(feedbackLengthBytes);
					feedbackLength = DataInterconversionTool.bytesToShort(feedbackLengthBytes);
					feedback = new byte[feedbackLength];
					din.read(feedback);
					mr = new MessageResponse(feedbackLength, feedback);
					din.close();
					dout.close();
					socket.close();
					return mr;
				}
				return null;
			} catch (UnknownHostException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			} catch (BodyLengthZeroException e) {
				e.printStackTrace();
				return null;
			} 
		} else {
			if(!usernameHasBeenSetFlag) {
				System.out.println("Connectors缺少用户名，如果没有账号，请先注册！");
				throw new ConnectorUsernameNotSetException();
			} if(!userPasswordSetFlag) {
				System.out.println("Connectors缺少用户密码，如果没有账号，请先注册！");
				throw new ConnectorPasswordNotSetException();
			}else if(!tokenHasBeenSetFlag) {
				System.out.println("Connectors缺少用户Token，如果没有账号，请先注册！");
				throw new ConnectorTokenNotSetException();
			} else if(!deviceIDHasBeenSetFlag) {
				System.out.println("Connectors缺少设备ID，如果没有设备ID，请先注册设备！");
				throw new ConnectorDeviceIDNotSetException();
			}
			return null;
		}
	}
	
	public static void setUserLoginStatus(boolean status) {
		userLoginStatus = status;
		UploadDataScheduledTask.setUploadDataTaskReadyFlag(status);
		if(userLoginStatus) {
			uploadDataTask.tryReactiveTask();
		}
	}
	
	private boolean checkReady() {
		if(userLoginStatus) {
			UploadDataScheduledTask.setUploadDataTaskReadyFlag(userLoginStatus);
			uploadDataTask.tryReactiveTask();
			return true;
		} else {
			UploadDataScheduledTask.setUploadDataTaskReadyFlag(userLoginStatus);
			uploadDataTask.reschedule(0);
			return false;
		}
	}
	
}
