package mochi.tool.module.iotplatform.foundation;

public class StandardDataPackage {

	@SuppressWarnings("unused")
	private class Head {
		String[] h1 = {"totallength 2字节", "commandid 2字节", "time 8字节"};
	}
	
	@SuppressWarnings("unused")
	private class Body_UserLogin {
		String[] b1 = {"0x0001", "length", "username"};
		String[] b2 = {"0x0002", "length", "password"};
	}
	
	@SuppressWarnings("unused")
	private class Body_UserLogin_Response {
		String[] b1 = {"0x1001", "length", "1"};
		String[] b2 = {"0x1002", "length", "错误原因描述，可选"};
	}
	
	@SuppressWarnings("unused")
	private class Body_UserRegister {
		String[] b1 = {"0x0001", "length", "username"};
		String[] b2 = {"0x0002", "length", "password"};
		String[] b3 = {"0x0003", "length", "email"};
	}
	
	@SuppressWarnings("unused")
	private class Body_UserRegister_Response {
		String[] b1 = {"0x1001", "length", "1"};
		String[] b2 = {"0x1002", "length", "错误原因描述，可选"};
	}
	
	@SuppressWarnings("unused")
	private class Body_Device_Data_Upload {
		String[] b1 = {"0x0001", "length", "username"};
		String[] b2 = {"0x0004", "length", "token"};
		String[] b3 = {"0x2001", "length", "deviceID"};
		String[] b4 = {"TLV组", "b5", "b6", "……", "b5", "b6"};
		String[] b5 = {"0x2002","length","sourceTag"};
		String[] b6 = {"0x2003","length","sourceValue"};
	}
	
	@SuppressWarnings("unused")
	private class Body_Device_Data_Upload_Response {
		String[] b1 = {"0x1001", "length", "1"};
		String[] b2 = {"0x1002", "length", "错误原因描述，可选"};
	}
	
	public static final short X0001 = 0x0001;//用户名
	public static final short X0002 = 0x0002;//密码
	public static final short X0003 = 0x0003;//邮箱
	public static final short X0004 = 0x0004;//Token
	public static final short X0005 = 0x0005;//新密码
	public static final short X0006 = 0x0006;//设备名称
	public static final short X0007 = 0x0007;//设备描述
	public static final short X0008 = 0x0008;//设备位置
	public static final short X0009 = 0x0009;//设备写入数据库方式
	public static final short X000A = 0x000A;//设备固定IP标志
	public static final short X000B = 0x000B;//设备IP
	public static final short X000C = 0x000C;//设备类型
	public static final short X000D = 0x000D;//设备ID
	public static final short X000E = 0x000E;//数据源名称
	public static final short X000F = 0x000F;//数据源Tag
	public static final short X0010 = 0x0010;//数据源描述信息
	public static final short X0011 = 0x0011;//数据源位置信息
	public static final short X0012 = 0x0012;//数据源的图形展示页面
	public static final short X0013 = 0x0013;//查看时间单位跨度，单位毫秒
	public static final short X0014 = 0x0014;//时间间隔，单位毫秒
	
	public static final short X1001 = 0x1001;//成功标记
	public static final short X1002 = 0x1002;//错误原因描述
	public static final short X1003 = 0x1003;//请求的历史数据信息
	
	public static final short COMMAND_ID_USER_REGISTER = 0x4000;
	public static final short COMMAND_ID_USER_LOGIN = 0x4001;
	public static final short COMMAND_ID_USER_LOGOUT = 0x4002;
	public static final short COMMAND_ID_USER_UPDATE_PASSWORD = 0x4003;
	public static final short COMMAND_ID_DEVICE_REGISTER = 0x4004;
	public static final short COMMAND_ID_SOURCE_REGISTER = 0x4005;
	public static final short COMMAND_ID_DATA_UPLOAD = 0x4006;
	public static final short COMMAND_ID_DEVICE_DELETE = 0x4007;
	public static final short COMMAND_ID_SOURCE_DELETE = 0x4008;
	
}
