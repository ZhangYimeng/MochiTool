package mochi.tool.net.newhttpprotocol;

/**
 * 一些常用的Http请求header的参数，直接调用即可。
 * @author saito
 *
 */
public class HttpRequestHeaderInfo {
	
	/**
	 * Accept-encoding: gzip, deflate
	 */
	public static final String Accept_encoding_gzip_deflate = "Accept-Encoding: gzip, deflate";
	/**
	 * Accept: text/plain
	 */
	public static final String Accept_text_plain = "Accept: text/plain";
	
	/**
	 * Accept: text/html; charset=UTF-8
	 */
	public static final String Accept_text_html_utf_8 = "Accept: text/html; charset=UTF-8";
	
	/**
	 * Accept: x/x
	 */
	public static final String Accept_X_X = "Accept: */*";
	
	/**
	 * Accept-Language: en-US
	 */
	public static final String Accept_Language_en_US = "Accept-Language: en-US";
	
	/**
	 * Accept-Language: zh-CN
	 */
	public static final String Accept_Language_zh_CN = "Accept-Language: zh-CN";
	
	/**
	 * User_Agent_Mochi
	 */
	public static final String User_Agent_Zhang_Yimeng = "User-Agent: Mochi";
	
	/**
	 * POST方法需要Content-Type。
	 * Content-Type: application/x-www-form-urlencoded
	 */
	public static final String Content_Type_application_x_www_form_urlencoded = "Content-Type: "
			+ "application/x-www-form-urlencoded";
	
	/**
	 * POST方法需要Content-Type。
	 * Content-Type: text/xml
	 */
	public static final String Content_Type_text_xml = "Content-Type: text/xml";
	
	/**
	 * POST方法需要Content-Type。
	 * Content-Type: text/plain
	 */
	public static final String Content_Type_text_plain = "Content-Type: text/plain";
	
	/**
	 * 本地测试Host。
	 */
	public static final String Host_LocalHost = "Host: localhost";
	
	/**
	 * Connection: keep-alive
	 */
	public static final String Connection_keep_alive = "Connection: keep-alive";
	
	/**
	 * Connection: close
	 */
	public static final String Connection_close = "Connection: close";
	
	/**
	 * Cache-Control: no-cache
	 */
	public static final String Cache_Control_no_cache = "Cache-Control: no-cache";
	
	/**
	 * Content_Length: *, '*'需要具体指定。
	 */
	public static final String Content_Length = "Content-Length: ";

}
