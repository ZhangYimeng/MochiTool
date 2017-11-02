package z.test;

import java.io.IOException;

public class 服务端启动 {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		服务端2 s1 = new 服务端2("192.168.1.36", 8888);
		服务端2Socket接收器 s2 = new 服务端2Socket接收器("192.168.1.36", 8887);
	}

}
