/**
 * @author Yimeng Zhang
 * @filename MochiGatewayConnector.java
 * @package mochi.tool.module.iotplatform.open.api.connector
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
import mochi.tool.module.iotplatform.open.api.datatool.MessageResponse;
import mochi.tool.module.iotplatform.open.api.datatool.MochiGatewayHeartBeatScheduledTask;
import mochi.tool.module.iotplatform.open.api.datatool.MochiGatewayObserverBetweenHeartBeatAndUploadDataTask;
import mochi.tool.module.iotplatform.open.api.datatool.MochiGatewayUploadDataScheduledTask;
import mochi.tool.module.iotplatform.open.api.datatool.ResponseBody;
import mochi.tool.module.iotplatform.open.api.exception.BodyLengthZeroException;
import mochi.tool.module.iotplatform.open.api.exception.ConnectorDeviceIDNotSetException;
import mochi.tool.module.iotplatform.open.api.exception.ConnectorDeviceIDSetException;
import mochi.tool.module.iotplatform.open.api.exception.ConnectorPasswordNotSetException;
import mochi.tool.module.iotplatform.open.api.exception.ConnectorTokenNotSetException;
import mochi.tool.module.iotplatform.open.api.exception.ConnectorUsernameNotSetException;
import mochi.tool.module.iotplatform.open.api.exception.ConnectorUsernameSetException;
import mochi.tool.module.iotplatform.open.api.exception.RegisterNewDataSourceCommandIDWrongException;
import mochi.tool.module.iotplatform.open.api.exception.RegisterNewDeviceCommandIDWrongException;
import mochi.tool.module.iotplatform.open.api.exception.RegisterNewUserCommandIDWrongException;

public class MochiGatewayConnector {
	
	private Socket socket;
	private DataInputStream din;
	private DataOutputStream dout;
	private static String ip;
	private static int port;
	
	private static boolean usernameHasBeenSetFlag = false;
	private static boolean userPasswordSetFlag = false;
	private static boolean tokenHasBeenSetFlag = false;
	private static boolean deviceIDHasBeenSetFlag = false;
	
	private static boolean userLoginStatus = false;
	
	private byte[] uploadDataMessage;
	
	private static MochiGatewayUploadDataScheduledTask uploadDataTask;
	private static MochiGatewayHeartBeatScheduledTask heartBeatTask;
	
	public MochiGatewayConnector(String ip, int port) {
		MochiGatewayConnector.ip = ip;
		MochiGatewayConnector.port = port;
		heartBeatTask = new MochiGatewayHeartBeatScheduledTask(this);
		uploadDataTask = new MochiGatewayUploadDataScheduledTask(this);
		heartBeatTask.schedule(heartBeatTask, 0l, DataConfig.HEART_BEAT_INTERVAL);
		uploadDataTask.schedule(uploadDataTask, 0l, DataConfig.HEART_BEAT_INTERVAL);
		new MochiGatewayObserverBetweenHeartBeatAndUploadDataTask(heartBeatTask, uploadDataTask);
	}
	
	public String getIP() {
		return ip;
	}
	
	public int getPort() {
		return port;
	}
	
	public static void setUserPassword(String password) {
		userPasswordSetFlag = true;
		MochiGatewayHeartBeatScheduledTask.setPassword(password);
		heartBeatTask.tryReactiveTaskLoop();
	}
	
	public static void setUsername(String username) throws ConnectorUsernameSetException {
		if(usernameHasBeenSetFlag == true) {
			throw new ConnectorUsernameSetException();
		} else {
			usernameHasBeenSetFlag = true;
			MochiGatewayHeartBeatScheduledTask.setUsername(username);
			heartBeatTask.tryReactiveTaskLoop();
		}
	}
	
	public static void setToken(String token) {
		tokenHasBeenSetFlag = true;
		MochiGatewayHeartBeatScheduledTask.setToken(token);
		heartBeatTask.tryReactiveTaskLoop();
	}
	
	public static void setDeviceID(String deviceID) throws ConnectorDeviceIDSetException {
		if(deviceIDHasBeenSetFlag == true) {
			throw new ConnectorDeviceIDSetException();
		} else {
			deviceIDHasBeenSetFlag = true;
			MochiGatewayHeartBeatScheduledTask.setDeviceID(deviceID);
			heartBeatTask.tryReactiveTaskLoop();
		}
	}
	
	public MessageResponse registerNewUser(byte[] message) throws UnknownHostException, IOException, RegisterNewUserCommandIDWrongException {
		short commandid = DataInterconversionTool.bytesToShort(
				Arrays.copyOfRange(message, DataConfig.TOTALLENGTH_LENGTH, 
						DataConfig.TOTALLENGTH_LENGTH + DataConfig.COMMANDID_LENGTH));
		if(commandid == MessageProtocolConfig.COMMAND_ID_USER_REGISTER) {
			socket = new Socket(ip, port);
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
			socket = new Socket(ip, port);
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
	
	public MessageResponse registerNewDataSource(byte[] message) throws UnknownHostException, IOException, RegisterNewDataSourceCommandIDWrongException {
		short commandid = DataInterconversionTool.bytesToShort(
				Arrays.copyOfRange(message, DataConfig.TOTALLENGTH_LENGTH, 
						DataConfig.TOTALLENGTH_LENGTH + DataConfig.COMMANDID_LENGTH));
		if(commandid == MessageProtocolConfig.COMMAND_ID_SOURCE_REGISTER) {
			socket = new Socket(ip, port);
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
			throw new RegisterNewDataSourceCommandIDWrongException();
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
					if(MochiGatewayUploadDataScheduledTask.getInterval() == 0) {
						socket = new Socket(ip, port);
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
						MochiGatewayUploadDataScheduledTask.setUploadDataMessage(uploadDataMessage);
					}
					break;
				case MessageProtocolConfig.COMMAND_ID_REGENERATE_TOKEN:
					socket = new Socket(ip, port);
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
							MochiGatewayHeartBeatScheduledTask.setToken(DataInterconversionTool.bytesToString(rb.getCurrentValue()));
						}
					}
					din.close();
					dout.close();
					socket.close();
					return mr;
				default:
					socket = new Socket(ip, port);
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
		MochiGatewayUploadDataScheduledTask.setUploadDataTaskReadyFlag(status);
		if(userLoginStatus) {
			uploadDataTask.tryReactiveTask();
		}
	}
	
	private boolean checkReady() {
		if(userLoginStatus) {
			MochiGatewayUploadDataScheduledTask.setUploadDataTaskReadyFlag(userLoginStatus);
			uploadDataTask.tryReactiveTask();
			return true;
		} else {
			MochiGatewayUploadDataScheduledTask.setUploadDataTaskReadyFlag(userLoginStatus);
			uploadDataTask.reschedule(0);
			return false;
		}
	}
	
}
