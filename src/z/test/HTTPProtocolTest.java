package z.test;

import java.io.IOException;

import mochi.tool.net.httpprotocol.HttpRequestHeader;
import mochi.tool.net.httpprotocol.HttpRequestLine;
import mochi.tool.net.httpprotocol.HttpRequestLineFieldsInfo;
import mochi.tool.net.httpprotocol.HttpRequestSender;
import mochi.tool.net.httpprotocol.HttpResponseContent;
import mochi.tool.net.httpprotocol.exception.HttpRequestHeaderNoHostException;
import mochi.tool.net.httpprotocol.exception.HttpRequestLineNoMethodException;
import mochi.tool.net.httpprotocol.exception.HttpRequestLineNoUrlException;
import mochi.tool.net.httpprotocol.exception.HttpRequestLineNoVersionException;

public class HTTPProtocolTest implements Runnable {

	public static String[] s = {
//			"/Gateway/report/1?uri=/gatewayID/resource/RSoilHumiditySensor/Agriculture/RealTimeSoilHumidity&method=GET&urivariable=0&commonparam=JSON&privateparam=0&returnmessage=0&abilitydescription=RealTimeSoilHumidity&messagesupporttype=0&wrongcode=000&worngdescription=CAN_NOT_GET_DATA&supplementary=0",
//			"/Gateway/report/1?uri=/gatewayID/resource/RSoilTemperatureSensor/Agriculture/RealTimeSoilTemperature&method=GET&urivariable=0&commonparam=JSON&privateparam=0&returnmessage=0&abilitydescription=RealTimeSoilTemperature&messagesupporttype=0&wrongcode=000&worngdescription=CAN_NOT_GET_DATA&supplementary=0",
//			"/Gateway/report/1?uri=/gatewayID/resource/RLightSensor/Agriculture/RealTimeLight&method=GET&urivariable=0&commonparam=JSON&privateparam=0&returnmessage=0&abilitydescription=RealTimeLight&messagesupporttype=0&wrongcode=000&worngdescription=CAN_NOT_GET_DATA&supplementary=0",
//			"/Gateway/report/1?uri=/gatewayID/resource/RAirHumiditySensor/Agriculture/RealTimeAirHumidity&method=GET&urivariable=0&commonparam=JSON&privateparam=0&returnmessage=0&abilitydescription=RealTimeAirHumidity&messagesupporttype=0&wrongcode=000&worngdescription=CAN_NOT_GET_DATA&supplementary=0",
//			"/Gateway/report/1?uri=/gatewayID/resource/RAirTemperatureSensor/Agriculture/RealTimeAirTemperature&method=GET&urivariable=0&commonparam=JSON&privateparam=0&returnmessage=0&abilitydescription=RealTimeAirTemperature&messagesupporttype=0&wrongcode=000&worngdescription=CAN_NOT_GET_DATA&supplementary=0",
//			"/Gateway/report/1?uri=/Gateway/temperature.jsp&method=GET&urivariable=0&commonparam=JSON&privateparam=0&returnmessage=0&abilitydescription=RealTimeAirTemperature&messagesupporttype=0&wrongcode=000&worngdescription=CAN_NOT_GET_DATA&supplementary=0"
			"/",
			"/",
			"/",
			"/",
			"/",
			"/"
		};
	
	private String uri;
	
	public HTTPProtocolTest(String s) {
		uri = s;
	}
	
	public static void main(String[] args) throws IOException, HttpRequestLineNoMethodException, HttpRequestLineNoUrlException, HttpRequestLineNoVersionException, HttpRequestHeaderNoHostException {
		
		for(int i = 0; i < 6; i++) {
			HTTPProtocolTest ht = new HTTPProtocolTest(s[i]);
			Thread t = new Thread(ht);
			t.start();
		}
	}

	@Override
	public void run() {
		try {
			HttpRequestLine hrl = new HttpRequestLine(HttpRequestLineFieldsInfo.GET, uri, HttpRequestLineFieldsInfo.HTTP_1_1);
			HttpRequestHeader hrh = new HttpRequestHeader();
//			hrh.setAccept("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
//			hrh.setAccept_encoding("Accept-Encoding: gzip, deflate");
//			hrh.setAccept_language("Accept-Language: zh-CN,zh;q=0.8,en;q=0.6");
//			hrh.setCache_control("Cache-Control: max-age=0");
//			hrh.setConnection("Connection: keep-alive");
//			hrh.setHost("Host: cn.bing.com");
//			hrh.setUser_agent("User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");
			hrh.setHost("Host: 192.168.1.7:3000");
			hrh.setSomeThing("Connection: Upgrade\n"
					+ "Pragma: no-cache\n"
					+ "Cache-Control: no-cache\n"
					+ "Upgrade: websocket\n"
					+ "Origin: file://\n"
					+ "Sec-WebSocket-Version: 13\n"
					+ "User-Agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36\n"
					+ "Accept-Encoding: gzip, deflate\n"
					+ "Accept-Language: zh-CN,zh;q=0.8\n"
					+ "Sec-WebSocket-Key: BC56stsOOnfI6mMG1RRQzQ==\n"
					+ "Sec-WebSocket-Extensions: permessage-deflate; client_max_window_bits");
			HttpRequestSender hrs = new HttpRequestSender(hrl, hrh, null, 3000);
			System.out.println(hrs.toString());
			HttpResponseContent hrc = hrs.sendHttpRequest();
			System.out.println(hrc.readNext());
			System.out.println(hrc.readNext());
			System.out.println(hrc.readNext());
			System.out.println(hrc.readNext());
			System.out.println(hrc.readNext());
			System.out.println(hrc.readNext());
			System.out.println(hrc.readNext());
			System.out.println(hrc.readNext());
			System.out.println(hrc.readNext());
			System.out.println(hrc.readNext());
			System.out.println(hrc.readNext());
			System.out.println(hrc.readNext());
			System.out.println(hrc.readNext());
			System.out.println(hrc.readNext());
			System.out.println(hrc.readNext());
			System.out.println(hrc.readNext());
			System.out.println(hrc.readNext());
			System.out.println(hrc.readNext());
			System.out.println(hrc.readNext());
			System.out.println(hrc.readNext());
			System.out.println(hrc.readNext());
			System.out.println(hrc.readNext());
			System.out.println(hrc.readNext());
			System.out.println(hrc.readNext());
			System.out.println(hrc.readNext());
			System.out.println(hrc.readNext());
			System.out.println(hrc.readNext());
			System.out.println(hrc.readNext());
			System.out.println(hrc.readNext());
			System.out.println(hrc.readNext());
			System.out.println(hrc.readNext());
			System.out.println(hrc.readNext());
			System.out.println(hrc.readNext());
			System.out.println(hrc.readNext());
			System.out.println(hrc.readNext());
			System.out.println(hrc.readNext());
			System.out.println(hrc.readNext());
			System.out.println(hrc.readNext());
			System.out.println(hrc.readNext());
			System.out.println(hrc.readNext());
			hrc.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (HttpRequestLineNoMethodException e) {
			e.printStackTrace();
		} catch (HttpRequestLineNoUrlException e) {
			e.printStackTrace();
		} catch (HttpRequestLineNoVersionException e) {
			e.printStackTrace();
		} catch (HttpRequestHeaderNoHostException e) {
			e.printStackTrace();
		}
	}

}
