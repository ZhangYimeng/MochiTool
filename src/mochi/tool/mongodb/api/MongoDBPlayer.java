package mochi.tool.mongodb.api;

import java.util.Arrays;

import mochi.tool.mongodb.api.exception.DBFindDistinctValueLackFiledException;
import mochi.tool.mongodb.api.exception.IllegalPortValueException;
import mochi.tool.mongodb.api.exception.NoServerIPException;
import mochi.tool.mongodb.api.foundation.MongoDBConfig;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDBPlayer {
	
	private MongoClient mc;
	private MongoDatabase currentDB;
	private MongoCollection<Document> currentCollection;
	
	public MongoDBPlayer(MongoDBConfig conf, String currentDataBaseName, String currentCollectionName) {
		if(conf.getClass().equals(MongoDBConfigWithNoAuth.class)) {
			mc = new MongoClient(conf.getIP(), conf.getPort());
		} else if(conf.getClass().equals(MongoDBConfigWithAuth.class)) {
			ServerAddress server = new ServerAddress(conf.getIP(), conf.getPort());
			MongoCredential cred = MongoCredential.createCredential(conf.getUsername(), "admin", conf.getPassword().toCharArray());
			mc = new MongoClient(server, Arrays.asList(cred));
		}
		currentDB = mc.getDatabase(currentDataBaseName);
		currentCollection = currentDB.getCollection(currentCollectionName);
	}
	
	public void switchDB(String dataBaseName) {
		currentDB = mc.getDatabase(dataBaseName);
	}
	
	public void switchCollection(String collectionName) {
		currentCollection = currentDB.getCollection(collectionName);
	}
	
	public Results getData(Duality duality) {
		FindIterable<Document> fit = currentCollection.find(duality.getInsideDoc());
		return new Results(fit, fit.iterator());
	}
	
	public void insertData(Duality duality) {
		currentCollection.insertOne(duality.getInsideDoc());
	}
	
	public long getCount(Duality duality) {
		return currentCollection.count(duality.getInsideDoc());
	}
	
	public <T> DistinctResults<T> advancedDistinct(String field, Duality filter, Class<T> className) throws DBFindDistinctValueLackFiledException {
		if(field != null) {
			DistinctIterable<T> distinct = currentCollection.distinct(field, filter, className);
			return new DistinctResults<T>(distinct, distinct.iterator());
		} else {
			throw new DBFindDistinctValueLackFiledException();
		}
	}
	
	public void createIndex(Duality duality) {
		currentCollection.createIndex(duality.getInsideDoc());
	}
	
	public static void main(String[] args) throws NoServerIPException, IllegalPortValueException {
		MongoDBConfigWithAuth conf = new MongoDBConfigWithAuth("127.0.0.1", 27017, "mochi", "gotohellmyevilex");
		MongoDBPlayer db = new MongoDBPlayer(conf, "test", "test");
		Duality data = new Duality();
		data.append("aaa", 1);
		db.insertData(data);
		System.out.println(db.getCount(data));
	}

}
