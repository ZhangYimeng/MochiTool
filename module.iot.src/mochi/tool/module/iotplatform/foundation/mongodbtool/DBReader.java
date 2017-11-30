package mochi.tool.module.iotplatform.foundation.mongodbtool;

import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.LinkedList;

import mochi.tool.module.iotplatform.foundation.mongodbtool.exception.DBCollectionNotExistException;
import mochi.tool.module.iotplatform.foundation.mongodbtool.exception.DBObjectNullPointerException;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class DBReader extends DBBridge {

	private DBReader(String ip, int port) throws UnknownHostException {
		super(ip, port);
	}
	
	public static boolean doesCollectionExist(String collection) {
		return db.collectionExists(collection);
	}
	
	public static DBResult readOne(String collection, String key, String value) throws DBObjectNullPointerException, DBCollectionNotExistException {
		if(db.collectionExists(collection)) {
			DBCollection dbc = db.getCollection(collection);
			DBObject dbo = new BasicDBObject(key, value);
			return new DBResult(dbc.findOne(dbo, null, new BasicDBObject("_id", -1)));
		} else {
			throw new DBCollectionNotExistException();
		}
	}
	
	public static DBResult readOne(String collection, LinkedList<Object[]> queryContent) throws DBObjectNullPointerException, DBCollectionNotExistException {
		if(db.collectionExists(collection)) {
			DBCollection dbc = db.getCollection(collection);
			BasicDBObject dbo = new BasicDBObject();
			Iterator<Object[]> it = queryContent.iterator();
			while(it.hasNext()) {
				Object[] line = (Object[]) it.next();
				dbo.append((String) line[0], line[1]);
			}
			return new DBResult(dbc.findOne(dbo, null, new BasicDBObject("_id", -1)));
		} else {
			throw new DBCollectionNotExistException();
		}
	}
	
	public static DBResult readOne(String collection, DBObject dbo) throws DBObjectNullPointerException, DBCollectionNotExistException {
		if(db.collectionExists(collection)) {
			DBCollection dbc = db.getCollection(collection);
			return new DBResult(dbc.findOne(dbo, null, new BasicDBObject("_id", -1)));
		} else {
			throw new DBCollectionNotExistException();
		}
	}
	
	public static DBResultList read(String collection, String key, String value) throws DBCollectionNotExistException {
		if(db.collectionExists(collection)) {
			DBCollection dbc = db.getCollection(collection);
			DBObject dbo = new BasicDBObject(key, value);
			DBCursor cursor = dbc.find(dbo);
			return new DBResultList(cursor);
		} else {
			throw new DBCollectionNotExistException();
		}
	}
	
	public static DBResultList read(String collection, LinkedList<Object[]> queryContent) throws DBCollectionNotExistException {
		if(db.collectionExists(collection)) {
			DBCollection dbc = db.getCollection(collection);
			BasicDBObject dbo = new BasicDBObject();
			Iterator<Object[]> it = queryContent.iterator();
			while(it.hasNext()) {
				Object[] line = (Object[]) it.next();
				dbo.append((String) line[0], line[1]);
			}
			DBCursor cursor = dbc.find(dbo);
			return new DBResultList(cursor);
		} else {
			throw new DBCollectionNotExistException();
		}
	}
	
	public static DBResultList read(String collection, DBObject dbo) throws DBCollectionNotExistException {
		if(db.collectionExists(collection)) {
			DBCollection dbc = db.getCollection(collection);
			return new DBResultList(dbc.find(dbo));
		} else {
			throw new DBCollectionNotExistException();
		}
	}
	
}
