package mochi.tool.mongodb.cache.foundation.exception;

public class CacheInitException extends Exception {

	private static final long serialVersionUID = 5467880954657119132L;
	private static final String info = "缓存初始化失败。";
	
	public CacheInitException() {
		super(info);
	}

}
