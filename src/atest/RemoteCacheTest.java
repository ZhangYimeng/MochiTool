package atest;

import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import mochi.tool.mongodb.cache.Field;
import mochi.tool.mongodb.cache.Matryoshka;
import mochi.tool.mongodb.cache.MongoDBCache;
import mochi.tool.mongodb.cache.MongoDBCacheConfig;
import mochi.tool.mongodb.cache.Value;
import mochi.tool.mongodb.cache.foundation.exception.CacheInitException;
import mochi.tool.mongodb.cache.foundation.exception.MatryoshkaNotMatchValueKeyFieldException;
import mochi.tool.mongodb.cache.foundation.exception.ValueNullException;

public class RemoteCacheTest {

	public static void main(String[] args) throws UnknownHostException, CacheInitException, MatryoshkaNotMatchValueKeyFieldException, ValueNullException {
		MongoDBCacheConfig mcc = new MongoDBCacheConfig("127.0.0.1", 27017, "mochi", "gotohellmyevilex");
		Matryoshka matry = new Matryoshka();
		matry.addField(new Field("date"));
		matry.addField(new Field("moment"));
		matry.addField(new Field("id"));
		MongoDBCache cache = new MongoDBCache(mcc, "test", matry);
		Random random = new Random();
		Calendar date = Calendar.getInstance();
		long t1 = System.currentTimeMillis();
		long t2 = 0;
		for(long i = 0; i < Long.MAX_VALUE; i++) {
			date.setTime(new Date(random.nextLong()));
			Value value = new Value();
			value.addKeyField(date.get(Calendar.YEAR) + "" + date.get(Calendar.MONTH) + 1 + "" + date.get(Calendar.DAY_OF_MONTH));
			value.addKeyField(date.get(Calendar.HOUR_OF_DAY) + ":" + date.get(Calendar.MINUTE) + ":" + date.get(Calendar.SECOND));
			value.addKeyField(UUID.randomUUID().toString());
			value.setValue(random.nextInt());
			cache.putData(value);
			if(i%10000 == 0) {
				t2 = System.currentTimeMillis();
				System.out.println(i + " in " + (t2 - t1)/1000 + " seconds.");
				t1 = t2;
			}
		}
		
	}

}
