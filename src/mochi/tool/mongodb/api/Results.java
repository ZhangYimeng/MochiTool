package mochi.tool.mongodb.api;

import java.io.Serializable;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;

public class Results implements Serializable {

	private static final long serialVersionUID = -3324538925529040700L;
	private MongoCursor<Document> resultsc;
	private FindIterable<Document> resultsf;
	private MongoCursor<Document> temp;
	private long size;
	
	public Results(FindIterable<Document> resultsf, MongoCursor<Document> resultsc) {
		this.resultsc = resultsc;
		this.resultsf = resultsf;
		size = -1;
	}
	
	public boolean hasNext() {
		return resultsc.hasNext();
	}
	
	public Result next() {
		return new Result(resultsc.next());
	}
	
	public long size() {
		if(size == -1) {
			size = 0;
			temp = resultsf.iterator();
			while(temp.hasNext()) {
				size++;
			}
			return size;
		} else {
			return size;
		}
	}
	
}
