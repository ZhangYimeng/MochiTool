package atest;

import java.util.LinkedList;

import com.mongodb.DBObject;

import mochi.tool.module.usermanagement.UserManagementModuleDefaultDatabaseGeneratorForMongoDB;
import mochi.tool.module.usermanagement.UserManagementModuleUsingMongoDB_LocalMod_NoSecurity;
import mochi.tool.module.usermanagement.UserinfoResult;
import mochi.tool.module.usermanagement.exception.AuthenticFailureException;
import mochi.tool.module.usermanagement.exception.NoSuchUserException;
import mochi.tool.module.usermanagement.exception.ReduplicateContentException;
import mochi.tool.module.usermanagement.exception.UserNotLoginException;
import mochi.tool.module.usermanagement.foundation.UserManagementConfiguration_MongoDBMode;

public class AutenticTest {

	public static void main(String[] args) {
		UserManagementModuleDefaultDatabaseGeneratorForMongoDB amd = new UserManagementModuleDefaultDatabaseGeneratorForMongoDB();
		amd.generate();
		String[] username = {UserManagementConfiguration_MongoDBMode.USER_INFO_COLLECTION_KEYS.username, "zhangyimeng"};
		String[] password = {UserManagementConfiguration_MongoDBMode.USER_INFO_COLLECTION_KEYS.password, "f820bc6ab2"};
		String[] email = {UserManagementConfiguration_MongoDBMode.USER_INFO_COLLECTION_KEYS.email, "saitoiyoli@sina.com"};
		LinkedList<String[]> registerContent = new LinkedList<String[]>();
		registerContent.add(username);
		registerContent.add(password);
		registerContent.add(email);
		try {
			String token = UserManagementModuleUsingMongoDB_LocalMod_NoSecurity.register(registerContent, null);
			System.out.println(token);
			UserinfoResult flag = UserManagementModuleUsingMongoDB_LocalMod_NoSecurity.login(UserManagementConfiguration_MongoDBMode.USER_INFO_COLLECTION_KEYS.username, 
					"zhangyimeng", UserManagementConfiguration_MongoDBMode.USER_INFO_COLLECTION_KEYS.password, "f820bc6ab2", null);
			System.out.println("登陆状态：" + flag);
			UserManagementModuleUsingMongoDB_LocalMod_NoSecurity.authenticate("zhangyimeng", token);
			System.out.println("认证状态：" + "true");
			DBObject dbo = UserManagementModuleUsingMongoDB_LocalMod_NoSecurity.getUserinfo(UserManagementConfiguration_MongoDBMode.USER_INFO_COLLECTION_KEYS.username, "zhangyimeng");
			System.out.println(dbo.get("email"));
			String[] email2 = {"email", "821862672@qq.com"};
			LinkedList<String[]> updateContent = new LinkedList<String[]>();
			updateContent.add(email2);
			UserManagementModuleUsingMongoDB_LocalMod_NoSecurity.updateUserinfo(UserManagementConfiguration_MongoDBMode.USER_INFO_COLLECTION_KEYS.username, "zhangyimeng", 
					updateContent);
			dbo = UserManagementModuleUsingMongoDB_LocalMod_NoSecurity.getUserinfo(UserManagementConfiguration_MongoDBMode.USER_INFO_COLLECTION_KEYS.username, "zhangyimeng");
			System.out.println(dbo.get("email"));
			token = UserManagementModuleUsingMongoDB_LocalMod_NoSecurity.regenerateToken("username", "zhangyimeng");
			System.out.println(token);
		} catch (ReduplicateContentException e) {
			e.printStackTrace();
		} catch (NoSuchUserException e) {
			e.printStackTrace();
		} catch (AuthenticFailureException e) {
			e.printStackTrace();
		} catch (UserNotLoginException e) {
			e.printStackTrace();
		}
	}

}
