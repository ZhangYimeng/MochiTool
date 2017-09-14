package mochi.tool.net.sustainingconnector.foundation;

import java.io.DataOutputStream;

public interface DataHandlerInterface extends Runnable {
	
	/**
	 * 加载待处理的数据。
	 * @param data 待处理数据。
	 */
	public void setBytesData(byte[] data);
	
	/**
	 * 加载消息回复实例
	 * @param replier 消息回复实例。
	 */
	public void setReplier(DataOutputStream replier);
	
}
