package mochi.tool.mongodb.cache.foundation.exception;

public class CahcaExistWarning extends Exception {

	private static final long serialVersionUID = 4781076614130682291L;
	private static final String info = "创建的缓存已经存在，因此您会直接得到该缓存的句柄（指针）。如果此后您使用了之前不曾声明的KeyField，将极大地影响效率。";
	
	public CahcaExistWarning() {
		super(info);
	}

}
