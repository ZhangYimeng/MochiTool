package mochi.tool.module.usermanagement;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import mochi.tool.module.usermanagement.exception.AuthenticFailureException;
import mochi.tool.module.usermanagement.exception.NoSuchUserException;
import mochi.tool.module.usermanagement.exception.ReduplicateContentException;
import mochi.tool.module.usermanagement.exception.UserNotLoginException;
import mochi.tool.module.usermanagement.foundation.UserManagementConfiguration_MongoDBMode;
import mochi.tool.module.usermanagement.foundation.UserTokenObjectInCache;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

@SuppressWarnings("deprecation")
public class UserManagementModuleUsingMongoDB_LocalMod_NoSecurity {
	
	private static MongoClient mc;
	private static DB db;
	private static HashMap<String, UserTokenObjectInCache> cache;
	private static class CacheCheck implements Runnable {

		@Override
		public void run() {
			Timer timer = new Timer();
			TimerTask tt = new TimerTask(){
				@Override
				public void run() {
					System.out.println("UserOnlineCheckThread is running.");
					Iterator<UserTokenObjectInCache> it = cache.values().iterator();
					while(it.hasNext()) {
						UserTokenObjectInCache token = it.next();
						if(token.getActiveFlag()) {
							token.setActiveFlagFalse();
							System.out.println(token.hashCode() + "被标记为false！");
						} else {
							cache.remove(token.getUsername());
							System.out.println(token.hashCode() + "被移出了Cache！");
						}
					}
				}
			};
			timer.schedule(tt, 100, 60000);
		}
		
	}
	
	static {
		mc = new MongoClient(UserManagementConfiguration_MongoDBMode.IP, UserManagementConfiguration_MongoDBMode.PORT);
		db = mc.getDB(UserManagementConfiguration_MongoDBMode.DATABASE);
		cache = new HashMap<String, UserTokenObjectInCache>();
		new Thread(new CacheCheck()).start();
	}
	
	public static String register(LinkedList<String[]> registerContent, Runnable afterDoing) throws ReduplicateContentException {
		try {
			DBCollection dbc = db.getCollection(UserManagementConfiguration_MongoDBMode.USER_INFO_COLLECTION);
			BasicDBObject dbo = new BasicDBObject();
			while(!registerContent.isEmpty()) {
				String[] line = registerContent.poll();
				dbo.append(line[0], line[1]);
			}
			String token = generateToken();
			dbo.append(UserManagementConfiguration_MongoDBMode.USER_INFO_COLLECTION_KEYS.token, token);
			dbc.insert(dbo);
			if(afterDoing != null) {
				new Thread(afterDoing).start();
			}
			return token;
		} catch(MongoException e) {
			System.out.println("注册失败");
			throw new ReduplicateContentException();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static UserinfoResult login(String userid_key, String userid, String password_key, String password, Runnable afterDoing) throws NoSuchUserException, AuthenticFailureException {
		DBCollection dbc = db.getCollection(UserManagementConfiguration_MongoDBMode.USER_INFO_COLLECTION);
		DBObject result = dbc.findOne(new BasicDBObject(userid_key, userid));
		if(result == null) {
			throw new NoSuchUserException();
		} else if(!result.get(password_key).equals(password)) {
			throw new AuthenticFailureException();
		} else {
			UserTokenObjectInCache userToken = new UserTokenObjectInCache(userid, (String) result.get(UserManagementConfiguration_MongoDBMode.USER_INFO_COLLECTION_KEYS.token));
			cache.put(userid, userToken);
			if(afterDoing != null) {
				new Thread(afterDoing).start();
			}
			return new UserinfoResult((Map<String, String>) result.toMap());
		}
	}
	
	public static void authenticate(String userid, String token) throws AuthenticFailureException, UserNotLoginException {
		UserTokenObjectInCache userToken = cache.get(userid);
		if(userToken == null) {
			System.out.println("用户登录过期");
			throw new UserNotLoginException("用户登录过期");
		} else if(userToken.getToken().equals(token)) {
			cache.get(userid).setActiveFlagTrue();
		} else {
			System.out.println("用户Token验证不一致");
			throw new AuthenticFailureException("用户Token验证不一致");
		}
	}
	
	public static boolean authenticateFromDatabase(String userid_key, String userid, String token) throws NoSuchUserException {
		DBCollection dbc = db.getCollection(UserManagementConfiguration_MongoDBMode.USER_INFO_COLLECTION);
		DBObject result = dbc.findOne(new BasicDBObject(userid_key, userid));
		if(result == null) {
			throw new NoSuchUserException();
		} else {
			String tokenFromDatabase = (String) result.get(UserManagementConfiguration_MongoDBMode.USER_INFO_COLLECTION_KEYS.token);
			return tokenFromDatabase.equals(token)? true: false;
		}
	}
	
	public static DBObject getUserinfo(String userid_key, String userid) {
		DBCollection dbc = db.getCollection(UserManagementConfiguration_MongoDBMode.USER_INFO_COLLECTION);
		DBObject result = dbc.findOne(new BasicDBObject(userid_key, userid));
		return result;
	}
	
	public static void updateUserinfo(String userid_key, String userid, LinkedList<String[]> updateContent) {
		DBCollection dbc = db.getCollection(UserManagementConfiguration_MongoDBMode.USER_INFO_COLLECTION);
		BasicDBObject waitForUpdate = new BasicDBObject();
		Iterator<String[]> it = updateContent.iterator();
		while(it.hasNext()) {
			String[] line = it.next();
			waitForUpdate.append(line[0], line[1]);
		}
		dbc.update(new BasicDBObject(userid_key, userid), new BasicDBObject("$set", waitForUpdate));
	}
	
	public static String regenerateToken(String userid_key, String userid) {
		DBCollection dbc = db.getCollection(UserManagementConfiguration_MongoDBMode.USER_INFO_COLLECTION);
		String token = generateToken();
		DBObject dbo = new BasicDBObject(UserManagementConfiguration_MongoDBMode.USER_INFO_COLLECTION_KEYS.token, token);
		dbc.update(new BasicDBObject(userid_key, userid), new BasicDBObject("$set", dbo));
		return token;
	}
	
	private static String generateToken() {
		UUID uuid = UUID.randomUUID();
		String[] temp = uuid.toString().substring(0, 23).split("-");
		String token = temp[0] + temp[1] + temp[2] + temp[3];
		return token;
	}
	
	public static void resetUserPassword(String userid_key, String userid, String password_key, String newPassword) {
		DBCollection dbc = db.getCollection(UserManagementConfiguration_MongoDBMode.USER_INFO_COLLECTION);
		BasicDBObject targetDbo = new BasicDBObject(userid_key, userid);
		BasicDBObject waitToUpdateDbo = new BasicDBObject(password_key, newPassword);
		dbc.update(targetDbo, new BasicDBObject("$set", waitToUpdateDbo));
	}
	
}
