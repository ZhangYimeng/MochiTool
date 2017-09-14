package mochi.tool.module.iotplatform;

import java.net.Socket;
import java.util.LinkedList;

import mochi.tool.bson.types.BasicBSONList;
import mochi.tool.data.interconversion.DataInterconversionTool;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import mochi.tool.module.iotplatform.foundation.DataSourceDevice;
import mochi.tool.module.iotplatform.foundation.GatewayDevice;
import mochi.tool.module.iotplatform.foundation.OnlineGatewayDevicePool;
import mochi.tool.module.iotplatform.foundation.dataanalyse.MessageBody;
import mochi.tool.module.iotplatform.foundation.dataanalyse.MessageHead;
import mochi.tool.module.iotplatform.foundation.dataanalyse.MessageProtocolConfig;
import mochi.tool.module.iotplatform.foundation.dataanalyse.MessageReader;
import mochi.tool.module.iotplatform.foundation.dataanalyse.MessageReceiverAndSender;
import mochi.tool.module.iotplatform.foundation.dataanalyse.TLV;
import mochi.tool.module.iotplatform.foundation.dataanalyse.exception.MessageContentWrongException;
import mochi.tool.module.iotplatform.foundation.dataanalyse.exception.MessageProtocolException;
import mochi.tool.module.iotplatform.foundation.exception.DeviceNotExistsException;
import mochi.tool.module.iotplatform.foundation.exception.DeviceNotOnlineException;
import mochi.tool.module.iotplatform.foundation.exception.DuplicateException;
import mochi.tool.module.iotplatform.foundation.exception.DuplicatedDeviceIDException;
import mochi.tool.module.iotplatform.foundation.exception.DuplicatedSourceTagException;
import mochi.tool.module.iotplatform.foundation.exception.DuplicatedUsernameException;
import mochi.tool.module.iotplatform.foundation.exception.UserPasswordNotMatchException;
import mochi.tool.module.iotplatform.foundation.exception.UsernameWrongException;
import mochi.tool.module.iotplatform.foundation.mongodbtool.DBReader;
import mochi.tool.module.iotplatform.foundation.mongodbtool.DBResultList;
import mochi.tool.module.iotplatform.foundation.mongodbtool.MongoDBConfig;
import mochi.tool.module.iotplatform.foundation.mongodbtool.exception.DBCollectionNotExistException;
import mochi.tool.module.iotplatform.foundation.mongodbtool.exception.DBObjectNullPointerException;
import mochi.tool.module.iotplatform.open.api.datatool.BytesCombineTool;
import mochi.tool.module.iotplatform.open.api.datatool.DataContent;
import mochi.tool.module.usermanagement.UserManagementModuleUsingMongoDB_LocalMod_NoSecurity;
import mochi.tool.module.usermanagement.exception.AuthenticFailureException;
import mochi.tool.module.usermanagement.exception.NoSuchUserException;
import mochi.tool.module.usermanagement.exception.ReduplicateContentException;
import mochi.tool.module.usermanagement.exception.UserNotLoginException;
import mochi.tool.module.usermanagement.foundation.UserManagementConfiguration_MongoDBMode;

//20160119:测试查询数据时打印测试数据，出现了“null”多与数据
public class RequestAcceptThread extends Thread {

	MessageReceiverAndSender mr;
	MessageReader reader;
	private MessageHead mh;
	private MessageBody mb;
	private String username;
	private String token;
	
	public RequestAcceptThread(Socket socket) {
		mr = new MessageReceiverAndSender(socket);
	}
	
	@Override
	public void run() {
		reader = mr.getMessageReader();
		mh = reader.getHead();
		mb = reader.getBody();
		byte[] response;
		try {
			switch(mh.getCommandID()) {
			case MessageProtocolConfig.COMMAND_ID_DATA_UPLOAD:
				System.out.println("收到上传数据报文");
				response = dataUpload();
				mr.sendResponse(response);
				mr.close();
				break;
			case MessageProtocolConfig.COMMAND_ID_DEVICE_DELETE:
				System.out.println("收到删除设备报文");
				response = deviceDelete();
				mr.sendResponse(response);
				mr.close();
				break;
			case MessageProtocolConfig.COMMAND_ID_DEVICE_REGISTER:
				System.out.println("收到设备注册报文");
				response = deviceRegister();
				mr.sendResponse(response);
				mr.close();
				break;
			case MessageProtocolConfig.COMMAND_ID_SOURCE_DELETE:
				System.out.println("收到删除数据源报文");
				response = dataSourceDelete();
				mr.sendResponse(response);
				mr.close();
				break;
			case MessageProtocolConfig.COMMAND_ID_SOURCE_REGISTER:
				System.out.println("收到数据源注册报文");
				response = dataSourceRegister();
				mr.sendResponse(response);
				mr.close();
				break;
			case MessageProtocolConfig.COMMAND_ID_USER_LOGIN:
				System.out.println("收到用户登录报文");
				response = userLogin();
				mr.sendResponse(response);
				mr.close();
				break;
			case MessageProtocolConfig.COMMAND_ID_USER_LOGOUT:
				System.out.println("收到用户登出报文");
				response = userLogout();
				mr.sendResponse(response);
				mr.close();
				break;
			case MessageProtocolConfig.COMMAND_ID_USER_REGISTER:
				System.out.println("收到用户注册报文");
				response = userRegister();
				mr.sendResponse(response);
				mr.close();
				break;
			case MessageProtocolConfig.COMMAND_ID_RESET_PASSWORD:
				System.out.println("收到重设密码报文");
				response = userUpdataPassword();
				mr.sendResponse(response);
				mr.close();
				break;
			case MessageProtocolConfig.COMMAND_ID_REGENERATE_TOKEN:
				System.out.println("收到重设用户Token报文");
				response = regenerateToken();
				mr.sendResponse(response);
				mr.close();
				break;
			case MessageProtocolConfig.COMMAND_ID_QUERY_DATA:
				System.out.println("收到查询数据报文");
				response = queryData();
				mr.sendResponse(response);
				mr.close();
				break;
			case MessageProtocolConfig.COMMAND_ID_SET_DEVICE_INTERVAL:
				System.out.println("收到设置设备上传数据时间间隔报文");
				response = setDeviceInterval();
				mr.sendResponse(response);
				mr.close();
				break;
			case MessageProtocolConfig.COMMAND_ID_USER_HEART_BEAT:
				System.out.println("收到用户心跳报文");
				response = userHeartBeat();
				mr.sendResponse(response);
				mr.close();
				break;
			case MessageProtocolConfig.COMMAND_ID_DEVICE_HEART_BEAT:
				System.out.println("收到设备心跳报文");
				response = deviceHeartBeat();
				mr.sendResponse(response);
				mr.close();
				break;
			}
		} catch (MessageProtocolException e) {
			sendMessageProtocolFeedback(e);
		} catch (DuplicatedDeviceIDException e) {
			sendDuplicatedDeviceIDFeedback(e);
		} catch (DBCollectionNotExistException e) {
			sendDBCollectionNotExistFeedback(e);
		} catch (AuthenticFailureException e) {
			sendAuthenticFailureFeedback(e);
		} catch (UserNotLoginException e) {
			sendUserNotLoginFeedback(e);
		} catch (UserPasswordNotMatchException e) {
			sendUserPasswordNotMatchFeedback(e);
		} catch (NoSuchUserException e) {
			sendNoSuchUserFeedback(e);
		} catch (MessageContentWrongException e) {
			sendMessageContentWrongFeedback(e);
		} catch (DuplicatedSourceTagException e) {
			sendDuplicatedSourceTagFeedback(e);
		} catch (DuplicatedUsernameException e) {
			sendDuplicatedUsernameFeedback(e);
		} catch (DeviceNotExistsException e) {
			sendDeviceNotExistsFeedback(e);
		} catch (UsernameWrongException e) {
			sendUsernameWrongFeedback(e);
		} catch (DeviceNotOnlineException e) {
			sendDeviceNotOnlineFeedback(e);
		} catch (DBObjectNullPointerException e) {
			e.printStackTrace();
		}
	}

	private void sendDeviceNotOnlineFeedback(DeviceNotOnlineException e) {
		e.printStackTrace();
		mr.sendResponse(generateResponseMessage(MessageProtocolConfig.ERR_DEVICE_NOT_ONLINE, MessageProtocolConfig.ERR_DEVICE_NOT_ONLINE_MESSAGE, null));
		mr.close();
	}
	
	private void sendUsernameWrongFeedback(UsernameWrongException e) {
		e.printStackTrace();
		mr.sendResponse(generateResponseMessage(MessageProtocolConfig.ERR_USERNAME_WRONG, MessageProtocolConfig.ERR_USERNAME_WRONG_MESSAGE, null));
		mr.close();
	}
	
	private void sendDeviceNotExistsFeedback(DeviceNotExistsException e) {
		e.printStackTrace();
		mr.sendResponse(generateResponseMessage(MessageProtocolConfig.ERR_DEVICE_NOT_EXISTS, MessageProtocolConfig.ERR_DEVICE_NOT_EXISTS_MESSAGE, null));
		mr.close();
	}
	
	private void sendDuplicatedUsernameFeedback(DuplicatedUsernameException e) {
		e.printStackTrace();
		mr.sendResponse(generateResponseMessage(MessageProtocolConfig.ERR_DUPLICATED_USERNAME, MessageProtocolConfig.ERR_DUPLICATED_USERNAME_MESSAGE, null));
		mr.close();
	}
	
	private void sendDuplicatedSourceTagFeedback(DuplicatedSourceTagException e) {
		e.printStackTrace();
		mr.sendResponse(generateResponseMessage(MessageProtocolConfig.ERR_DUPLICATED_DATASOURCETAG, MessageProtocolConfig.ERR_DUPLICATED_DATASOURCETAG_MESSAGE, null));
		mr.close();
	}
	
	private void sendMessageContentWrongFeedback(MessageContentWrongException e) {
		e.printStackTrace();
		mr.sendResponse(generateResponseMessage(MessageProtocolConfig.ERR_MESSAGE_CONTENT_WRONG, MessageProtocolConfig.ERR_MESSAGE_CONTENT_WRONG_MESSAGE, null));
		mr.close();
	}

	private void sendUserNotLoginFeedback(UserNotLoginException e) {
		e.printStackTrace();
		mr.sendResponse(generateResponseMessage(MessageProtocolConfig.ERR_UNLOGIN, MessageProtocolConfig.ERR_UNLOGIN_MESSAGE, null));
		mr.close();
	}
	
	private void sendDBCollectionNotExistFeedback(DBCollectionNotExistException e) {
		e.printStackTrace();
		mr.sendResponse(generateResponseMessage(MessageProtocolConfig.ERR_MESSAGE_CONTENT_WRONG, MessageProtocolConfig.ERR_MESSAGE_CONTENT_WRONG_MESSAGE, null));
		mr.close();
	}
	
	private void sendNoSuchUserFeedback(NoSuchUserException e) {
		e.printStackTrace();
		mr.sendResponse(generateResponseMessage(MessageProtocolConfig.ERR_NO_SUCH_USER, MessageProtocolConfig.ERR_NO_SUCH_USER_MESSAGE, null));
		mr.close();
	}
	
	private void sendUserPasswordNotMatchFeedback(UserPasswordNotMatchException e) {
		e.printStackTrace();
		mr.sendResponse(generateResponseMessage(MessageProtocolConfig.ERR_USER_PASSWORD_NOT_MATCH, MessageProtocolConfig.ERR_USER_PASSWORD_NOT_MATCH_MESSAGE, null));
		mr.close();
	}
	
	private void sendDuplicatedDeviceIDFeedback(DuplicatedDeviceIDException e) {
		e.printStackTrace();
		mr.sendResponse(generateResponseMessage(MessageProtocolConfig.ERR_DUPLICATED_DEVICEID, MessageProtocolConfig.ERR_DUPLICATED_DEVICEID_MESSAGE, null));
		mr.close();
	}
	
	private void sendMessageProtocolFeedback(MessageProtocolException e) {
		e.printStackTrace();
		mr.sendResponse(generateResponseMessage(MessageProtocolConfig.ERR_PROTOCOL_ILLEGAL, MessageProtocolConfig.ERR_PROTOCOL_ILLEGAL_MESSAGE, null));
		mr.close();
	}
	
	private void sendAuthenticFailureFeedback(AuthenticFailureException e) {
		e.printStackTrace();
		mr.sendResponse(generateResponseMessage(MessageProtocolConfig.ERR_TOKEN_ILLEGAL, MessageProtocolConfig.ERR_TOKEN_ILLEGAL_MESSAGE, null));
		mr.close();
	}

	private void authenticate() throws AuthenticFailureException, MessageProtocolException, UserNotLoginException {
		TLV tlv = mb.nextTVL();
		username = DataInterconversionTool.bytesToString(tlv.getValue());
		tlv = mb.nextTVL();
		token = DataInterconversionTool.bytesToString(tlv.getValue());
		UserManagementModuleUsingMongoDB_LocalMod_NoSecurity.authenticate(username, token);
	}
	
	private byte[] generateResponseMessage_LongFeedback(String x1001, String x1002, Long feedback) {
		byte[] tlv = new byte[0];
		byte[] body = new byte[0];
		byte[] x1001Bytes = DataInterconversionTool.stringToBytes(x1001);
		tlv = BytesCombineTool.combineThreeBytes(DataInterconversionTool.shortToBytes(MessageProtocolConfig.X1001), 
				DataInterconversionTool.shortToBytes((short) x1001Bytes.length), 
				x1001Bytes);
		body = BytesCombineTool.append(body, tlv);
		if(x1001.equals(MessageProtocolConfig.SUCCEED)) {
			switch(mh.getCommandID()) {
			case MessageProtocolConfig.COMMAND_ID_DEVICE_HEART_BEAT:
				if(feedback != null) {
					byte[] tokenBytes = DataInterconversionTool.longToBytes(feedback);
					tlv = BytesCombineTool.combineThreeBytes(DataInterconversionTool.shortToBytes(MessageProtocolConfig.X0014), 
							DataInterconversionTool.shortToBytes((short) tokenBytes.length), 
							tokenBytes);
					body = BytesCombineTool.append(body, tlv);
				} else {
					System.out.println("返回的用户Token为空。");
				}
				break;
			} 
		} else {
			byte[] x1002Bytes = DataInterconversionTool.stringToBytes(x1002);
			tlv = BytesCombineTool.combineThreeBytes(DataInterconversionTool.shortToBytes(MessageProtocolConfig.X1002), 
					DataInterconversionTool.shortToBytes((short) x1002Bytes.length), 
					x1002Bytes);
			body = BytesCombineTool.append(body, tlv);
		}
		return BytesCombineTool.combineThreeBytes(DataInterconversionTool.shortToBytes((short) (MessageProtocolConfig.HEAD_LENGTH + body.length)), 
				mh.getHeadBytes(), body);
	}
	
	//20160119:测试查询数据时打印测试数据，出现了“null”多余数据。但是返回给网关数据无异常
	private byte[] generateResponseMessage(String x1001, String x1002, String feedback) {
		byte[] tlv = new byte[0];
		byte[] body = new byte[0];
		byte[] x1001Bytes = DataInterconversionTool.stringToBytes(x1001);
		tlv = BytesCombineTool.combineThreeBytes(DataInterconversionTool.shortToBytes(MessageProtocolConfig.X1001), 
				DataInterconversionTool.shortToBytes((short) x1001Bytes.length), 
				x1001Bytes);
		body = BytesCombineTool.append(body, tlv);
		if(x1001.equals(MessageProtocolConfig.SUCCEED)) {
			switch(mh.getCommandID()) {
			case MessageProtocolConfig.COMMAND_ID_REGENERATE_TOKEN:
				if(feedback != null) {
					byte[] tokenBytes = DataInterconversionTool.stringToBytes(feedback);
					tlv = BytesCombineTool.combineThreeBytes(DataInterconversionTool.shortToBytes(MessageProtocolConfig.X0004), 
							DataInterconversionTool.shortToBytes((short) tokenBytes.length), 
							tokenBytes);
					body = BytesCombineTool.append(body, tlv);
				} else {
					System.out.println("返回的用户Token为空。");
				}
				break;
			case MessageProtocolConfig.COMMAND_ID_DEVICE_REGISTER:
				if(feedback != null) {
					byte[] tokenBytes = DataInterconversionTool.stringToBytes(feedback);
					tlv = BytesCombineTool.combineThreeBytes(DataInterconversionTool.shortToBytes(MessageProtocolConfig.X0004), 
							DataInterconversionTool.shortToBytes((short) tokenBytes.length), 
							tokenBytes);
					body = BytesCombineTool.append(body, tlv);
				} else {
					System.out.println("返回的用户Token为空。");
				}
				break;
			case MessageProtocolConfig.COMMAND_ID_QUERY_DATA:
				if(feedback != null) {
					byte[] dataBytes = DataInterconversionTool.stringToBytes(feedback);
					tlv = BytesCombineTool.combineThreeBytes(DataInterconversionTool.shortToBytes(MessageProtocolConfig.X1003), 
							DataInterconversionTool.shortToBytes((short) dataBytes.length), 
							dataBytes);
					body = BytesCombineTool.append(body, tlv);
				} else {
					System.out.println("没有数据");
				}
				break;
			case MessageProtocolConfig.COMMAND_ID_USER_REGISTER:
				if(feedback != null) {
					byte[] tokenBytes = DataInterconversionTool.stringToBytes(feedback);
					tlv = BytesCombineTool.combineThreeBytes(DataInterconversionTool.shortToBytes(MessageProtocolConfig.X0004), 
							DataInterconversionTool.shortToBytes((short) tokenBytes.length), 
							tokenBytes);
					body = BytesCombineTool.append(body, tlv);
				} else {
					System.out.println("返回的用户Token为空。");
				}
				break;
			default:
				//do nothing
				break;
			}
		} else {
			byte[] x1002Bytes = DataInterconversionTool.stringToBytes(x1002);
			tlv = BytesCombineTool.combineThreeBytes(DataInterconversionTool.shortToBytes(MessageProtocolConfig.X1002), 
					DataInterconversionTool.shortToBytes((short) x1002Bytes.length), 
					x1002Bytes);
			body = BytesCombineTool.append(body, tlv);
		}
		//System.out.println(feedback);
		//System.out.println(BytesCombineTool.combineThreeBytes(DataInterconversionTool.shortToBytes((short) (MessageProtocolConfig.HEAD_LENGTH + body.length)), 
				//mh.getHeadBytes(), body).length);
		return BytesCombineTool.combineThreeBytes(DataInterconversionTool.shortToBytes((short) (MessageProtocolConfig.HEAD_LENGTH + body.length)), 
				mh.getHeadBytes(), body);
	}
	
	private byte[] userLogout() throws AuthenticFailureException, MessageProtocolException, UserNotLoginException {
		authenticate();
		return generateResponseMessage(MessageProtocolConfig.SUCCEED, null, null);
	}
	
	private byte[] userLogin() throws MessageProtocolException, UserPasswordNotMatchException, NoSuchUserException {
		TLV tlv = null;
		String username = null;
		String password = null;
		while(mb.hasNextTLV()) {
			tlv = mb.nextTVL();
			switch(tlv.getTag()) {
			case MessageProtocolConfig.X0001:
				username = DataInterconversionTool.bytesToString(tlv.getValue());
				break;
			case MessageProtocolConfig.X0002:
				password = DataInterconversionTool.bytesToString(tlv.getValue());
				break;
			}
		}
		try {
			UserManagementModuleUsingMongoDB_LocalMod_NoSecurity.login(
					UserManagementConfiguration_MongoDBMode.USER_INFO_COLLECTION_KEYS.username, username, 
					UserManagementConfiguration_MongoDBMode.USER_INFO_COLLECTION_KEYS.password, password, null);
			return generateResponseMessage(MessageProtocolConfig.SUCCEED, null, null);
		} catch(AuthenticFailureException e) {
			throw new UserPasswordNotMatchException();
		}
	}
	
	private byte[] userUpdataPassword() throws AuthenticFailureException, MessageProtocolException, UserNotLoginException, UserPasswordNotMatchException, NoSuchUserException, DBCollectionNotExistException {
		authenticate();
		TLV tlv = null;
		String password = null;
		String newPassword = null;
		while(mb.hasNextTLV()) {
			tlv = mb.nextTVL();
			switch(tlv.getTag()) {
			case MessageProtocolConfig.X0002:
				password = DataInterconversionTool.bytesToString(tlv.getValue());
				break;
			case MessageProtocolConfig.X0005:
				newPassword = DataInterconversionTool.bytesToString(tlv.getValue());
				break;
			}
		}
		try {
			UserManagementModuleUsingMongoDB_LocalMod_NoSecurity.login(
					UserManagementConfiguration_MongoDBMode.USER_INFO_COLLECTION_KEYS.username, username, 
					UserManagementConfiguration_MongoDBMode.USER_INFO_COLLECTION_KEYS.password, password, null);
		} catch(AuthenticFailureException e) {
			throw new UserPasswordNotMatchException();
		}
		UserManagementModuleUsingMongoDB_LocalMod_NoSecurity.resetUserPassword(
				UserManagementConfiguration_MongoDBMode.USER_INFO_COLLECTION_KEYS.username, 
				username, UserManagementConfiguration_MongoDBMode.USER_INFO_COLLECTION_KEYS.password, newPassword);
		return generateResponseMessage(MessageProtocolConfig.SUCCEED, null, null);
	}
	
	private byte[] regenerateToken() throws AuthenticFailureException, MessageProtocolException, UserNotLoginException, MessageContentWrongException, UserPasswordNotMatchException, NoSuchUserException {
		authenticate();
		TLV tlv = null;
		String password = null;
		if(mb.hasNextTLV()) {
			tlv = mb.nextTVL();
			password = DataInterconversionTool.bytesToString(tlv.getValue());
		} else {
			throw new MessageContentWrongException();
		}
		try {
			UserManagementModuleUsingMongoDB_LocalMod_NoSecurity.login(
					UserManagementConfiguration_MongoDBMode.USER_INFO_COLLECTION_KEYS.username, username, 
					UserManagementConfiguration_MongoDBMode.USER_INFO_COLLECTION_KEYS.password, password, null);
		} catch(AuthenticFailureException e) {
			throw new UserPasswordNotMatchException();
		}
		token = UserManagementModuleUsingMongoDB_LocalMod_NoSecurity.regenerateToken(UserManagementConfiguration_MongoDBMode.USER_INFO_COLLECTION_KEYS.username, username);
		return generateResponseMessage(MessageProtocolConfig.SUCCEED, null, token);
	}
	
	private byte[] dataSourceDelete() throws AuthenticFailureException, MessageProtocolException, UserNotLoginException, DBCollectionNotExistException {
		authenticate();
		TLV tlv = null;
		String deviceID = null;
		String sourceTag = null;
		while(mb.hasNextTLV()) {
			tlv = mb.nextTVL();
			switch(tlv.getTag()) {
			case MessageProtocolConfig.X000D:
				deviceID = DataInterconversionTool.bytesToString(tlv.getValue());
				break;
			case MessageProtocolConfig.X000F:
				sourceTag = DataInterconversionTool.bytesToString(tlv.getValue());
				break;
			}
		}
		DataSourceDevice.deleteDataSourceDevice(username, deviceID, sourceTag);
		return generateResponseMessage(MessageProtocolConfig.SUCCEED, null, null);
	}
	
	private byte[] deviceDelete() throws AuthenticFailureException, MessageProtocolException, UserNotLoginException, MessageContentWrongException, DBCollectionNotExistException {
		authenticate();
		TLV tlv = null;
		String deviceID = null;
		while(mb.hasNextTLV()) {
			tlv = mb.nextTVL();
			if(tlv.getTag() == MessageProtocolConfig.X000D) {
				deviceID = DataInterconversionTool.bytesToString(tlv.getValue());
			} else {
				throw new MessageContentWrongException();
			}
		}
		GatewayDevice.deleteGatewayDevice(username, deviceID);
		return generateResponseMessage(MessageProtocolConfig.SUCCEED, null, null);
	}
	
	private byte[] dataSourceRegister() throws AuthenticFailureException, MessageProtocolException, UserNotLoginException, DBCollectionNotExistException, DuplicatedSourceTagException {
		authenticate();
		TLV tlv = null;
		String deviceID = null;
		String sourceName = null;
		String sourceTag = null;
		String sourceDescription = null;
		String sourceLocation = null;
		String sourceDiagram = null;
		while(mb.hasNextTLV()) {
			tlv = mb.nextTVL();
			switch(tlv.getTag()) {
			case MessageProtocolConfig.X000D:
				deviceID = DataInterconversionTool.bytesToString(tlv.getValue());
				break;
			case MessageProtocolConfig.X000E:
				sourceName = DataInterconversionTool.bytesToString(tlv.getValue());
				break;
			case MessageProtocolConfig.X000F:
				sourceTag = DataInterconversionTool.bytesToString(tlv.getValue());
				break;
			case MessageProtocolConfig.X0010:
				sourceDescription = DataInterconversionTool.bytesToString(tlv.getValue());
				break;
			case MessageProtocolConfig.X0011:
				sourceLocation = DataInterconversionTool.bytesToString(tlv.getValue());
				break;
			case MessageProtocolConfig.X0012:
				sourceDiagram = DataInterconversionTool.bytesToString(tlv.getValue());
				break;
			}
		}
		DataSourceDevice.registerDataSourceDevice(username, deviceID, sourceName, sourceTag, sourceDescription, sourceLocation, sourceDiagram);
		return generateResponseMessage(MessageProtocolConfig.SUCCEED, null, null);
	}
	
	private byte[] userRegister() throws MessageProtocolException, DuplicatedUsernameException, DBCollectionNotExistException {
		TLV tlv = null;
		String username = null;
		String password = null;
		String email = null;
		while(mb.hasNextTLV()) {
			tlv = mb.nextTVL();
			switch(tlv.getTag()) {
			case MessageProtocolConfig.X0001:
				username = DataInterconversionTool.bytesToString(tlv.getValue());
				break;
			case MessageProtocolConfig.X0002:
				password = DataInterconversionTool.bytesToString(tlv.getValue());
				break;
			case MessageProtocolConfig.X0003:
				email = DataInterconversionTool.bytesToString(tlv.getValue());
				break;
			}
		}
		LinkedList<String[]> registerContent = new LinkedList<String[]>();
		String[] usernameSeq = {UserManagementConfiguration_MongoDBMode.USER_INFO_COLLECTION_KEYS.username, username};
		String[] passwordSeq = {UserManagementConfiguration_MongoDBMode.USER_INFO_COLLECTION_KEYS.password, password};
		String[] emailSeq = {UserManagementConfiguration_MongoDBMode.USER_INFO_COLLECTION_KEYS.email, email};
		registerContent.add(usernameSeq);
		registerContent.add(passwordSeq);
		registerContent.add(emailSeq);
		try {
			String token = UserManagementModuleUsingMongoDB_LocalMod_NoSecurity.register(registerContent, null);
			GatewayDevice.createUserDevicelistCollection(username);
	    	DataSourceDevice.createUserSourcelistCollection(username);
			return generateResponseMessage(MessageProtocolConfig.SUCCEED, null, token);
		} catch (ReduplicateContentException e) {
			throw new DuplicatedUsernameException();
		}
	}
	
	private byte[] deviceRegister() throws AuthenticFailureException, MessageProtocolException, UserNotLoginException, DuplicatedDeviceIDException, DBCollectionNotExistException {
		authenticate();
		TLV tlv = null;
		String deviceName = null;
		String deviceDescription = null;
		String deviceLocation = null;
		String deviceWriteType = null;
		String fixipFlag = null;
		String devicefixip = null;
		String deviceType = null;
		while(mb.hasNextTLV()) {
			tlv = mb.nextTVL();
			switch(tlv.getTag()) {
			case MessageProtocolConfig.X0006:
				deviceName = DataInterconversionTool.bytesToString(tlv.getValue());
				break;
			case MessageProtocolConfig.X0007:
				deviceDescription = DataInterconversionTool.bytesToString(tlv.getValue());
				break;
			case MessageProtocolConfig.X0008:
				deviceLocation = DataInterconversionTool.bytesToString(tlv.getValue());
				break;
			case MessageProtocolConfig.X0009:
				deviceWriteType = DataInterconversionTool.bytesToString(tlv.getValue());
				break;
			case MessageProtocolConfig.X000A:
				fixipFlag = DataInterconversionTool.bytesToString(tlv.getValue());
				if(fixipFlag.equals(MessageProtocolConfig.DEVICE_HAS_FIX_IP)) {
					tlv = mb.nextTVL();
					if(tlv.getTag() == MessageProtocolConfig.X000B) {
						devicefixip = DataInterconversionTool.bytesToString(tlv.getValue());
					} else {
						throw new MessageProtocolException();
					}
				}
				break;
			case MessageProtocolConfig.X000C:
				deviceType = DataInterconversionTool.bytesToString(tlv.getValue());
				break;
			}
		}
		String deviceID = GatewayDevice.registerNewGatewayDevice(username, deviceName, deviceDescription, deviceLocation, deviceWriteType, fixipFlag, devicefixip, deviceType);
		return generateResponseMessage(MessageProtocolConfig.SUCCEED, null, deviceID);
	}
	
	private byte[] dataUpload() throws MessageProtocolException, DBCollectionNotExistException, AuthenticFailureException, UserNotLoginException, DBObjectNullPointerException {
		authenticate();
		TLV tlv = null;
		String deviceID = null;
		String sourceTag = null;
		LinkedList<DataContent> dataContentList = new LinkedList<DataContent>();
		while(mb.hasNextTLV()) {
			tlv = mb.nextTVL();
			switch(tlv.getTag()) {
			case MessageProtocolConfig.X000D:
				deviceID = DataInterconversionTool.bytesToString(tlv.getValue());
				break;
			case MessageProtocolConfig.X2002:
				sourceTag = DataInterconversionTool.bytesToString(tlv.getValue());
				tlv = mb.nextTVL();
				if(tlv.getTag() == MessageProtocolConfig.X2003) {
					short dataType = DataInterconversionTool.bytesToShort(tlv.getValue());
					tlv = mb.nextTVL();
					byte[] value = tlv.getValue();
					DataContent dc = new DataContent(sourceTag, dataType, value);
					dataContentList.add(dc);
				} else if(tlv.getTag() == MessageProtocolConfig.X2004) {
					byte[] value = tlv.getValue();
					tlv = mb.nextTVL();
					short dataType = DataInterconversionTool.bytesToShort(tlv.getValue());
					DataContent dc = new DataContent(sourceTag, dataType, value);
					dataContentList.add(dc);
				} else {
					throw new MessageProtocolException();
				}
			}
		}
		try {
			if(DBReader.readOne(MongoDBConfig.USER_SOURCE_COLLECTIONS_PREFIX + username, new BasicDBObject(MongoDBConfig.SOURCE_LIST_KEYS.SOURCE_TAG, sourceTag)) != null) {
				GatewayDevice.uploadDataToDatabase(username, deviceID, mh.getTime(), dataContentList);
			}
			return generateResponseMessage("0", null, null);
		} catch (DBObjectNullPointerException e) {
			System.out.println("数据源未注册。");
			return generateResponseMessage(MessageProtocolConfig.ERR_DATASOURCE_NOT_EXIST, MessageProtocolConfig.ERR_DATASOURCE_NOT_EXIST_MESSAGE, null);
		} catch (DuplicateException e) {
			return generateResponseMessage(MessageProtocolConfig.ERR_DATASOURCE_NOT_EXIST, MessageProtocolConfig.ERR_DATASOURCE_NOT_EXIST_MESSAGE, null);
		}
		
	}
	
	
	//need to be thinking
	private byte[] queryData() throws AuthenticFailureException, MessageProtocolException, UserNotLoginException, DBCollectionNotExistException {
		authenticate();
		TLV tlv = null;
		String deviceID = null;
		String sourceTag = null;
		long timeSpan = 0;
		while(mb.hasNextTLV()) {
			tlv = mb.nextTVL();
			switch(tlv.getTag()) {
			case MessageProtocolConfig.X000D:
				deviceID = DataInterconversionTool.bytesToString(tlv.getValue());
				break;
			case MessageProtocolConfig.X000F:
				sourceTag = DataInterconversionTool.bytesToString(tlv.getValue());
				break;
			case MessageProtocolConfig.X0013:
				timeSpan = DataInterconversionTool.bytesToLong(tlv.getValue());
				break;
			}
		}
		DBResultList result = GatewayDevice.queryDataByTimeSpan(username, deviceID, sourceTag, timeSpan);
		BasicBSONList jsonList = new BasicBSONList();
		DBObject dbo = null;
		while(result.hasNext()) {
			result.next();
			dbo = result.getCurrentDBObject();
			jsonList.add(dbo);
		}
		return generateResponseMessage(MessageProtocolConfig.SUCCEED, null, jsonList.toString());
	}
	
	private byte[] setDeviceInterval() throws AuthenticFailureException, MessageProtocolException, UserNotLoginException, DBCollectionNotExistException, DeviceNotOnlineException {
		authenticate();
		TLV tlv = null;
		String deviceID = null;
		long interval = 0;
		while(mb.hasNextTLV()) {
			tlv = mb.nextTVL();
			switch(tlv.getTag()) {
			case MessageProtocolConfig.X000D:
				deviceID = DataInterconversionTool.bytesToString(tlv.getValue());
				break;
			case MessageProtocolConfig.X0014:
				interval = DataInterconversionTool.bytesToLong(tlv.getValue());
				break;
			}
		}
		//将时间间隔存入数据库和消息池
		GatewayDevice.setDeviceUploadInterval(username, deviceID, interval);		
		return generateResponseMessage(MessageProtocolConfig.SUCCEED, null, null);
	}
	
	private byte[] userHeartBeat() throws AuthenticFailureException, MessageProtocolException, UserNotLoginException {
		authenticate();
		return generateResponseMessage(MessageProtocolConfig.SUCCEED, null, null);
	}
	
	private byte[] deviceHeartBeat() throws AuthenticFailureException, MessageProtocolException, UserNotLoginException, MessageContentWrongException, DeviceNotExistsException, UsernameWrongException, DeviceNotOnlineException {
		authenticate();
		TLV tlv = null;
		String deviceID = null;
		while(mb.hasNextTLV()) {
			tlv = mb.nextTVL();
			if(tlv.getTag() == MessageProtocolConfig.X000D) {
				deviceID = DataInterconversionTool.bytesToString(tlv.getValue());
			} else {
				throw new MessageContentWrongException();
			}
		}
		if(GatewayDevice.doesDeviceExist(username, deviceID)) {
			OnlineGatewayDevicePool.putDeviceIntoDevicePool(username, deviceID);
			Long interval = OnlineGatewayDevicePool.tryToGetDeviceInterval(deviceID);
			if(interval != null) {
				return generateResponseMessage_LongFeedback(MessageProtocolConfig.SUCCEED, null, interval);
			} else {
				return generateResponseMessage(MessageProtocolConfig.SUCCEED, null, null);
			}
		} else {
			throw new DeviceNotExistsException();
		}
	}
	
}
