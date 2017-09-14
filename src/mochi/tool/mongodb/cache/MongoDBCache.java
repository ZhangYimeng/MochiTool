package mochi.tool.mongodb.cache;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Iterator;

import org.bson.Document;

import mochi.tool.mongodb.cache.foundation.CacheConfig;
import mochi.tool.mongodb.cache.foundation.CacheConfigDefault;
import mochi.tool.mongodb.cache.foundation.exception.CacheFindDistinctValueLackFiledException;
import mochi.tool.mongodb.cache.foundation.exception.CacheInitException;
import mochi.tool.mongodb.cache.foundation.exception.CahcaExistWarning;
import mochi.tool.mongodb.cache.foundation.exception.MatryoshkaNotMatchValueKeyFieldException;
import mochi.tool.mongodb.cache.foundation.exception.ValueNullException;
import mochi.tool.mongodb.operators.QueryOperators;
import mochi.tool.mongodb.operators.UpdateOperators;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDBCache {
	
	private MongoClient mc;
	private MongoCollection<Document> collection;
	private Matryoshka matry;
	private MongoDatabase db;
	private String cacheName;
	private static int[] lock = new int[0];
	
	public MongoDBCache(CacheConfig config, String cacheName, Matryoshka matry) throws UnknownHostException, CacheInitException {
		synchronized(lock) {
			if(config.getClass().equals(CacheConfigDefault.class)) {
				mc = new MongoClient(config.getIP(), config.getPort());
			} else if(config.getClass().equals(MongoDBCacheConfig.class)) {
				ServerAddress server = new ServerAddress(config.getIP(), config.getPort());
				MongoCredential cred = MongoCredential.createCredential(config.getUser(), "admin", config.getPassword().toCharArray());
				mc = new MongoClient(server, Arrays.asList(cred));
			} else {
				throw new CacheInitException();
			}
			this.cacheName = cacheName;
			this.matry = matry;
			db = mc.getDatabase("cache");
			@SuppressWarnings("deprecation")
			DB olddb = mc.getDB("cache");
			if(!olddb.collectionExists(cacheName)) {
				collection = db.getCollection(cacheName);
				collection.dropIndexes();
				collection.drop();
				BasicDBObject matryIndex = new BasicDBObject("matry", 1);
				collection.createIndex(matryIndex);
				BasicDBObject keyIndex = new BasicDBObject("overallkey", 1);
				collection.createIndex(keyIndex);
				Iterator<Field> it = matry.getFieldsIterator();
				while(it.hasNext()) {
					Field field = it.next();
					BasicDBObject index = new BasicDBObject(field.getFieldName(), 1);
					collection.createIndex(index);
				}
				it = matry.getFieldsIterator();
				while(it.hasNext()) {
					Field field = it.next();
					Document matryToBeStore = new Document();
					matryToBeStore.append("matry", field.getFieldName());
					collection.insertOne(matryToBeStore);
				}
			} else {
				collection = db.getCollection(cacheName);
				Document martyFilter = new Document();
				martyFilter.append("matry", new Document(QueryOperators.$EXIST, true));
				this.matry = new Matryoshka();
				Iterator<Document> it = collection.find(martyFilter).sort(new Document("_id", 1)).iterator();
				while(it.hasNext()) {
					Document matryField = it.next();
					this.matry.addField(new Field(matryField.getString("matry")));
				}
				try {
					throw new CahcaExistWarning();
				} catch (CahcaExistWarning e) {
					System.err.println(e.getMessage());
				}
			}
		}
	}
	
	/**
	 * 如果Value的KeyField已经存在，那么会覆盖原来的值。
	 * @param value 等待被加入的Value。
	 * @throws MatryoshkaNotMatchValueKeyFieldException
	 * @throws ValueNullException
	 */
	public void putData(Value value) throws MatryoshkaNotMatchValueKeyFieldException, ValueNullException {
		if(value != null) {
			int keyFieldNum = value.getKeyFieldNumber();
			StringBuffer key = new StringBuffer();
			Document doc = new Document();
			if(keyFieldNum == matry.getFieldsNumber()) {
				for(int i = 0; i < keyFieldNum; i++) {
					String keyField = value.getKeyFieldByIndex(i);
					doc.append(matry.getFieldByIndex(i).getFieldName(), keyField);
					key.append(keyField);
				}
			} else {
				throw new MatryoshkaNotMatchValueKeyFieldException();
			}
			doc.append("overallkey", key.toString());
			doc.append("value", value.getValue());
			Document filter = new Document();
			filter.append("overallkey", key.toString());
			Document update = new Document();
			update.append(UpdateOperators.$SET, doc);
			if(collection.findOneAndUpdate(filter, update) == null) {
				collection.insertOne(doc);
			}
		} else {
			throw new ValueNullException("Value不能为null。");
		}
	}
	
	/**
	 * 当且仅当Key所对应的值为整型时，才可以使用该方法，不然会报错。
	 * 如果Key对应的值不存在，则会创建一个。
	 * @throws MatryoshkaNotMatchValueKeyFieldException 
	 * @throws ValueNullException 
	 */
	public void increaseData(Value value) throws MatryoshkaNotMatchValueKeyFieldException, ValueNullException {
		if(value != null) {
			int keyFieldNum = value.getKeyFieldNumber();
			StringBuffer key = new StringBuffer();
			Document doc = new Document();
			if(keyFieldNum == matry.getFieldsNumber()) {
				for(int i = 0; i < keyFieldNum; i++) {
					String keyField = value.getKeyFieldByIndex(i);
					doc.append(matry.getFieldByIndex(i).getFieldName(), keyField);
					key.append(keyField);
				}
			} else {
				throw new MatryoshkaNotMatchValueKeyFieldException();
			}
			doc.append("overallkey", key.toString());
			doc.append("value", value.getValue());
			Document filter = new Document();
			filter.append("overallkey", key.toString());
			Document val = new Document("value", value.getValue());
			Document update = new Document();
			update.append(UpdateOperators.$INC, val);
			if(collection.findOneAndUpdate(filter, update) == null) {
				collection.insertOne(doc);
			}
		} else {
			throw new ValueNullException("Value不能为null。");
		}
	}
	
	/**
	 * 根据给定的Filter取得缓存中的数据。
	 * @param filter
	 */
	public Results getData(Filter filter) {
		if(filter != null) {
			int filterFieldNumber = filter.getFilterFieldValueNumber();
			Document searchKey = new Document();
			for(int i = 0; i < filterFieldNumber; i++) {
				searchKey.append(matry.getFieldByIndex(i).getFieldName(), filter.getFieldValueByIndex(i));
			}
			searchKey.append("matry", null);
			FindIterable<Document> fit = collection.find(searchKey);
			return new Results(fit, fit.iterator());
		} else {
			Document searchKey = new Document();
			searchKey.append("matry", null);
			FindIterable<Document> fit = collection.find(searchKey);
			return new Results(fit, fit.iterator());
		}
	}
	
	public Results advancedGetData(AdvancedFilter filter) {
		if(filter != null) {
			FindIterable<Document> fit = collection.find(filter);
			return new Results(fit, fit.iterator());
		} else {
			FindIterable<Document> fit = collection.find();
			return new Results(fit, fit.iterator());
		}
	}
	
	public <T> DistinctResults<T> distinct(Field field, Class<T> className) throws CacheFindDistinctValueLackFiledException {
		if(field != null) {
			DistinctIterable<T> distinct = collection.distinct(field.getFieldName(), new Document(field.getFieldName(), new Document(QueryOperators.$EXIST, true)), className);
			return new DistinctResults<T>(distinct, distinct.iterator());
		} else {
			throw new CacheFindDistinctValueLackFiledException();
		}
	}
	
	public <T> DistinctResults<T> advancedDistinct(Field field, AdvancedFilter filter, Class<T> className) throws CacheFindDistinctValueLackFiledException {
		if(field != null) {
			DistinctIterable<T> distinct = collection.distinct(field.getFieldName(), filter, className);
			return new DistinctResults<T>(distinct, distinct.iterator());
		} else {
			throw new CacheFindDistinctValueLackFiledException();
		}
	}
	
	public void deleteData(Filter filter) {
		if(filter != null) {
			int filterFieldNumber = filter.getFilterFieldValueNumber();
			Document searchKey = new Document();
			for(int i = 0; i < filterFieldNumber; i++) {
				searchKey.append(matry.getFieldByIndex(i).getFieldName(), filter.getFieldValueByIndex(i));
			}
			searchKey.append("matry", null);
			collection.deleteMany(searchKey);
		} else {
			Document searchKey = new Document();
			searchKey.append("matry", null);
			collection.deleteMany(searchKey);
		}
	}
	
	public void destroy() {
		collection.drop();
	}
	
	public String getCacheName() {
		return cacheName;
	}
	
	public Matryoshka getMatry() {
		return matry;
	}
	
	public static void main(String[] args) throws UnknownHostException, CacheInitException, MatryoshkaNotMatchValueKeyFieldException, ValueNullException, CacheFindDistinctValueLackFiledException {
		MongoDBCacheConfig mcc = new MongoDBCacheConfig("192.168.1.37", 27017, "mochi", "gotohellmyevilex");
		Matryoshka matry = new Matryoshka();
		matry.addField(new Field("date"));
		matry.addField(new Field("store"));
		matry.addField(new Field("class"));
		MongoDBCache cache = new MongoDBCache(mcc, "test", matry);
		Value value = new Value();
		value.addKeyField("20170121");
		value.addKeyField("yyy");
		value.addKeyField("sport");
		value.setValue(77);
		cache.putData(value);
		value = new Value();
		value.addKeyField("20170121");
		value.addKeyField("yyy");
		value.addKeyField("food");
		value.setValue(88);
		cache.putData(value);
		Filter filter = new Filter();
		filter.addFilterFieldValue("20170121");
		filter.addFilterFieldValue("yyy");
		Results results = cache.getData(filter);
		while(results.hasNext()) {
			System.out.println(results.next());
		}
		System.out.println(cache.getMatry());
		AdvancedFilter af = new AdvancedFilter();
		af.append("date", new AdvancedFilter(QueryOperators.$EXIST, true));
		Results resul = cache.advancedGetData(af);
		while(resul.hasNext()) {
			System.out.println(resul.next());
		}
		DistinctResults<String> drs = cache.distinct(new Field("date"), String.class);
		while(drs.hasNext()) {
			System.out.println(drs.next());
		}
		value = new Value();
		value.addKeyField("20170208");
		value.addKeyField("uuu");
		value.addKeyField("food");
		value.setValue(9090);
		cache.putData(value);
		value = new Value();
		value.addKeyField("20170208");
		value.addKeyField("uuu");
		value.addKeyField("stone");
		value.setValue(9091);
		cache.putData(value);
		value = new Value();
		value.addKeyField("20170208");
		value.addKeyField("ttt");
		value.addKeyField("food");
		value.setValue(9090);
		cache.putData(value);
		
//		filter = new Filter();
//		filter.addFilterFieldValue("20170121");
//		cache.deleteData(filter);
//		
//		filter = new Filter();
//		cache.deleteData(filter);
		System.out.println("======================================================================================");
		AdvancedFilter ff = new AdvancedFilter();
		ff.append("date", "20170208");
		ff.append("store", "uuu");
		DistinctResults<String> rrr = cache.advancedDistinct(new Field("class"), ff, String.class);
		while(rrr.hasNext()) {
			System.out.println(rrr.next());
		}
//		cache.destroy();
	}
	
	/**
	 * 不建议使用。
	 */
	public String toString() {
		return null;
	}

}
