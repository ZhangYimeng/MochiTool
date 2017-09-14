package mochi.tool.module.usermanagement.foundation;

public class UserManagementConfiguration_MongoDBMode {

	public static final String IP = "127.0.0.1";
	public static final int PORT = 27017;
	public static final String DATABASE = "authentication";
	public static final String USER_INFO_COLLECTION = "userinfo";
	public final static class USER_INFO_COLLECTION_KEYS {
		public static final String username = "username";
		public static final String password = "password";
		public static final String email = "email";
		public static final String token = "token";
	}
	
}
