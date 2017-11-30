package mochi.tool.module.iotplatform.foundation.mongodbtool;

import java.net.UnknownHostException;
import java.util.LinkedList;

import mochi.tool.module.iotplatform.foundation.exception.DuplicateException;
import mochi.tool.module.iotplatform.foundation.mongodbtool.exception.DBCollectionNotExistException;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

public class DBWriter extends DBBridge {

	public DBWriter(String ip, int port) throws UnknownHostException {
		super(ip, port);
	}
	
	public static void createCollection(String collectionName) {
		DBObject option = new BasicDBObject("capped", false);
		db.createCollection(collectionName, option);
	}
	
	public static void setIndex(String collectionName, String key, String option) throws DBCollectionNotExistException {
		if(db.collectionExists(collectionName)) {
			DBCollection dbc = db.getCollection(collectionName);
			DBObject key_dbo = new BasicDBObject(key, 1);
			if(option != null) {
				DBObject option_dbo = new BasicDBObject(option, true);
				dbc.createIndex(key_dbo, option_dbo);
			} else {
				dbc.createIndex(key_dbo);
			}
		} else {
			throw new DBCollectionNotExistException();
		}
	}
	
	public static void setIndex(String collectionName, String key, DBObject option) throws DBCollectionNotExistException {
		if(db.collectionExists(collectionName)) {
			DBCollection dbc = db.getCollection(collectionName);
			DBObject key_dbo = new BasicDBObject(key, 1);
			if(option != null) {
				dbc.createIndex(key_dbo, option);
			} else {
				dbc.createIndex(key_dbo);
			}
		} else {
			throw new DBCollectionNotExistException();
		}
	}
	
	public static void setIndex(String collectionName, String key) throws DBCollectionNotExistException {
		if(db.collectionExists(collectionName)) {
			DBCollection dbc = db.getCollection(collectionName);
			DBObject key_dbo = new BasicDBObject(key, 1);
			dbc.createIndex(key_dbo);
		} else {
			throw new DBCollectionNotExistException();
		}
	}
	
	/**
	 * 
	 * @param collectionName
	 * @param content
	 * @throws DBCollectionNotExistException 
	 * @throws DuplicateException 
	 * @throws MongoException
	 */
	public static void write(String collectionName, LinkedList<Object[]> content) throws DBCollectionNotExistException, DuplicateException{
		try {
			if(db.collectionExists(collectionName)) {
				DBCollection dbc = db.getCollection(collectionName);
				BasicDBObject dbo = new BasicDBObject();
				for(Object[] o: content) {
					dbo.append((String) o[0], o[1]);
				}
				dbc.insert(dbo);
			} else {
				throw new DBCollectionNotExistException();
			}
		} catch (MongoException e) {
			throw new DuplicateException();
		}
	}
	
	public static void write(String collectionName, String key, String value) throws DBCollectionNotExistException, DuplicateException{
		try {
			if(db.collectionExists(collectionName)) {
				DBCollection dbc = db.getCollection(collectionName);
				BasicDBObject dbo = new BasicDBObject(key, value);
				dbc.insert(dbo);
			} else {
				throw new DBCollectionNotExistException();
			}
		} catch (MongoException e) {
			throw new DuplicateException();
		}
	}
	
	public static void update(String collectionName, String targetKey, Object targetValue, String waitToUpdateKey, Object waitToUpdateValue) throws DBCollectionNotExistException {
		if(db.collectionExists(collectionName)) {
			DBCollection dbc = db.getCollection(collectionName);
			BasicDBObject targetDbo = new BasicDBObject(targetKey, targetValue);
			BasicDBObject waitToUpdateDbo = new BasicDBObject(waitToUpdateKey, waitToUpdateValue);
			dbc.update(targetDbo, new BasicDBObject("$set", waitToUpdateDbo));
		} else {
			throw new DBCollectionNotExistException();
		}
	}
	
	public static void update(String collectionName, LinkedList<Object[]> target, LinkedList<Object[]> waitForUpdateContent) throws DBCollectionNotExistException {
		if(db.collectionExists(collectionName)) {
			DBCollection dbc = db.getCollection(collectionName);
			BasicDBObject targetDbo = new BasicDBObject();
			for(Object[] o: target) {
				targetDbo.append((String) o[0], o[1]);
			}
			BasicDBObject waitForUpdateDbo = new BasicDBObject();
			for(Object[] o: waitForUpdateContent) {
				waitForUpdateDbo.append((String) o[0], o[1]);
			}
			dbc.update(targetDbo, new BasicDBObject("$set", waitForUpdateDbo));
		} else {
			throw new DBCollectionNotExistException();
		}
	}
	
	public static void deleteDBCollection(String collectionName) throws DBCollectionNotExistException {
		if(db.collectionExists(collectionName)) {
			DBCollection dbc = db.getCollection(collectionName);
			dbc.drop();
		} else {
			throw new DBCollectionNotExistException();
		}
	}
	
	public static void deleteDBObject(String collectionName, String targetKey, String targetValue) throws DBCollectionNotExistException {
		if(db.collectionExists(collectionName)) {
			DBCollection dbc = db.getCollection(collectionName);
			DBObject dbo = new BasicDBObject(targetKey, targetValue);
			dbc.remove(dbo);
		} else {
			throw new DBCollectionNotExistException();
		}
	}
	
	public static void deleteDBObject(String collectionName, LinkedList<Object[]> targetContent) throws DBCollectionNotExistException {
		if(db.collectionExists(collectionName)) {
			DBCollection dbc = db.getCollection(collectionName);
			BasicDBObject dbo = new BasicDBObject();
			for(Object[] o: targetContent) {
				dbo.append((String) o[0], o[1]);
			}
			dbc.remove(dbo);
		} else {
			throw new DBCollectionNotExistException();
		}
	}

}
