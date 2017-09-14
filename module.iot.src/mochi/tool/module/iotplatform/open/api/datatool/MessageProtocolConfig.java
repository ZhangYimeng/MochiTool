package mochi.tool.module.iotplatform.open.api.datatool;

public class MessageProtocolConfig {

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
	
	public static final short X2002 = 0x2002;//数据源Tag
	public static final short X2003 = 0x2003;//数据源数据类型
	public static final short X2004 = 0x2004;//数据源数据
	
	public static final String DEVICE_HAS_FIX_IP = "true";
	public static final String DEVICE_DO_NOT_HAS_FIX_IP = "false";
	public static final String DEVICE_WRITE_TYPE_SYN = "syn";
	public static final String DEVICE_WRITE_TYPE_ASYN = "asyn";
	public static final String DEVICE_TYPE_STANDARD = "s";
	public static final String DEVICE_TYPE_CUSTOM = "c";
	
	public static final String SOURCE_DIAGRAM_CURVE = "1";
	
	public static final short COMMAND_ID_USER_REGISTER = 0x4000;
	public static final short COMMAND_ID_USER_LOGIN = 0x4001;
	public static final short COMMAND_ID_USER_LOGOUT = 0x4002;
	public static final short COMMAND_ID_RESET_PASSWORD = 0x4003;
	public static final short COMMAND_ID_DEVICE_REGISTER = 0x4004;
	public static final short COMMAND_ID_SOURCE_REGISTER = 0x4005;
	public static final short COMMAND_ID_DATA_UPLOAD = 0x4006;
	public static final short COMMAND_ID_DEVICE_DELETE = 0x4007;
	public static final short COMMAND_ID_SOURCE_DELETE = 0x4008;
	public static final short COMMAND_ID_REGENERATE_TOKEN = 0x4009;
	public static final short COMMAND_ID_QUERY_DATA = 0x400B;
	public static final short COMMAND_ID_SET_DEVICE_INTERVAL = 0x400C;
	public static final short COMMAND_ID_DEVICE_HEART_BEAT = 0x400D;
	public static final short COMMAND_ID_USER_HEART_BEAT = 0x400E;
	
	public static final short DATA_SOURCE_DATATYPE_SHORT = 0;
	public static final short DATA_SOURCE_DATATYPE_INT = 1;
	public static final short DATA_SOURCE_DATATYPE_LONG = 2;
	public static final short DATA_SOURCE_DATATYPE_FLOAT = 3;
	public static final short DATA_SOURCE_DATATYPE_DOUBLE = 4;
	public static final short DATA_SOURCE_DATATYPE_VARCHAR = 5;
	public static final short DATA_SOURCE_DATATYPE_BINARY = 6;
	
	public static int HEAD_LENGTH = 12;
	public static int TOTALLENGTH_LENGTH = 2;
	public static int COMMANDID_LENGTH = 2;
	public static int TIME_LENGTH = 8;
	
	public static int TAG_LENGTH = 2;
	public static int LENGTH_LENGTH = 2;
	
	public static String SUCCEED = "0";
	public static String ERR_UNLOGIN = "2";
	public static String ERR_UNLOGIN_MESSAGE = "用户未登录。";
	public static String ERR_TOKEN_ILLEGAL = "3";
	public static String ERR_TOKEN_ILLEGAL_MESSAGE = "用户Token非法。";
	public static String ERR_PROTOCOL_ILLEGAL = "777";
	public static String ERR_PROTOCOL_ILLEGAL_MESSAGE = "报文格式错误。";
	public static String ERR_DUPLICATED_DEVICEID = "6";
	public static String ERR_DUPLICATED_DEVICEID_MESSAGE = "设备ID重复，请重新注册。";
	public static String ERR_USER_PASSWORD_NOT_MATCH = "7";
	public static String ERR_USER_PASSWORD_NOT_MATCH_MESSAGE = "密码错误。";
	public static String ERR_NO_SUCH_USER = "8";
	public static String ERR_NO_SUCH_USER_MESSAGE = "没有此用户。";
	public static String ERR_MESSAGE_CONTENT_WRONG = "9";
	public static String ERR_MESSAGE_CONTENT_WRONG_MESSAGE = "报文内容有误。";
	public static String ERR_DUPLICATED_DATASOURCETAG = "10";
	public static String ERR_DUPLICATED_DATASOURCETAG_MESSAGE = "数据源Tag重复。";
	public static String ERR_DUPLICATED_USERNAME = "11";
	public static String ERR_DUPLICATED_USERNAME_MESSAGE = "用户已存在。";
	
}
