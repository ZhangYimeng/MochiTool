package atest;

import java.util.Arrays;

import mochi.tool.mongodb.cache.AdvancedFilter;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;

public class MongoDBTest {

	public static void main(String[] args) {
		ServerAddress server = new ServerAddress("127.0.0.1", 27017);
		MongoCredential cred = MongoCredential.createCredential("mochi", "admin", "gotohellmyevilex".toCharArray());
		MongoClient mc = new MongoClient(server, Arrays.asList(cred));
		MongoDatabase db = mc.getDatabase("test");
		MongoCollection<Document> collection = db.getCollection("test");
		collection.dropIndexes();
		collection.drop();
		Document index = new Document();
		index.append("key", 1);
		IndexOptions options = new IndexOptions();
		options.unique(false);
		collection.createIndex(index, options);
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				for(int k = 0; k < 3; k++) {
					Document doc = new Document();
					doc.append("key", i).append("value",
							new Document().append("key", j).append("value", k));
					collection.insertOne(doc);
				}
			}
		}
		
//		Document filter = new Document("sss", 32);
//		Document target = new Document("key", 1);
//		target.append("sss", 32);
//		Document update = new Document(UpdateOperators.$SET, new Document().append("eee", 89));
//		collection.findOneAndUpdate(filter, update);
		
		FindIterable<Document> fit = collection.find();
		MongoCursor<Document> cursor = fit.iterator();
		while(cursor.hasNext()) {
			System.out.println(cursor.next());
		}
		System.out.println("=============");
		Document filter = new Document();
		//filter.append("key", 1).append("value", new Document().append("key", 1));
		filter.append("key", 1);
		fit = collection.find(filter);
		cursor = fit.iterator();
		while(cursor.hasNext()) {
			System.out.println(cursor.next());
		}
		
		System.out.println("====================");
		Document doc1 = new Document();
		doc1.append("aaa", 89);
		Document doc2 = new Document();
		doc2.append("aaa", 90);
		collection.insertOne(doc1);
		collection.insertOne(doc2);
		Document filter1 = new Document().append("key", new Document("$exists", true));
		fit = collection.find(filter1);
		cursor = fit.iterator();
		while(cursor.hasNext()) {
			System.out.println(cursor.next());
		}
		System.out.println("++++++++++++++");
		AdvancedFilter filter2 = (AdvancedFilter) new AdvancedFilter().append("aaa", new Document("$exists", true));
		System.out.println(filter2.toString());
		fit = collection.find(filter2);
		cursor = fit.iterator();
		while(cursor.hasNext()) {
			System.out.println(cursor.next());
		}
		System.out.println("==========");
		
		DistinctIterable<Integer> distinct = collection.distinct("key", new Document().append("key", new Document("$exists", true)), Integer.class);
		MongoCursor<Integer> cursor1 = distinct.iterator();
		while(cursor1.hasNext()) {
			System.out.println(cursor1.next());
		}
		
//		SomeThing st = new SomeThing();
//		st.setA("aa");
//		st.setB("bbb");
//		st.setC(999);
//		Document somethingInDoc = new Document();
//		somethingInDoc.append("1", st);
		mc.close();
	}

}
