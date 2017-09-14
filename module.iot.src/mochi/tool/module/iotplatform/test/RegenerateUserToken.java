package mochi.tool.module.iotplatform.test;

import mochi.tool.module.usermanagement.UserManagementModuleUsingMongoDB_LocalMod_NoSecurity;
import mochi.tool.module.usermanagement.foundation.UserManagementConfiguration_MongoDBMode;

public class RegenerateUserToken {

	public static void main(String[] args) {
		String token = UserManagementModuleUsingMongoDB_LocalMod_NoSecurity.regenerateToken(
				UserManagementConfiguration_MongoDBMode.USER_INFO_COLLECTION_KEYS.username, 
				"zhangyimeng");
		System.out.println(token);
	}

}
