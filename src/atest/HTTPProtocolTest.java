package atest;

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
			"/Gateway/report/1?uri=/gatewayID/resource/RSoilHumiditySensor/Agriculture/RealTimeSoilHumidity&method=GET&urivariable=0&commonparam=JSON&privateparam=0&returnmessage=0&abilitydescription=RealTimeSoilHumidity&messagesupporttype=0&wrongcode=000&worngdescription=CAN_NOT_GET_DATA&supplementary=0",
			"/Gateway/report/1?uri=/gatewayID/resource/RSoilTemperatureSensor/Agriculture/RealTimeSoilTemperature&method=GET&urivariable=0&commonparam=JSON&privateparam=0&returnmessage=0&abilitydescription=RealTimeSoilTemperature&messagesupporttype=0&wrongcode=000&worngdescription=CAN_NOT_GET_DATA&supplementary=0",
			"/Gateway/report/1?uri=/gatewayID/resource/RLightSensor/Agriculture/RealTimeLight&method=GET&urivariable=0&commonparam=JSON&privateparam=0&returnmessage=0&abilitydescription=RealTimeLight&messagesupporttype=0&wrongcode=000&worngdescription=CAN_NOT_GET_DATA&supplementary=0",
			"/Gateway/report/1?uri=/gatewayID/resource/RAirHumiditySensor/Agriculture/RealTimeAirHumidity&method=GET&urivariable=0&commonparam=JSON&privateparam=0&returnmessage=0&abilitydescription=RealTimeAirHumidity&messagesupporttype=0&wrongcode=000&worngdescription=CAN_NOT_GET_DATA&supplementary=0",
			"/Gateway/report/1?uri=/gatewayID/resource/RAirTemperatureSensor/Agriculture/RealTimeAirTemperature&method=GET&urivariable=0&commonparam=JSON&privateparam=0&returnmessage=0&abilitydescription=RealTimeAirTemperature&messagesupporttype=0&wrongcode=000&worngdescription=CAN_NOT_GET_DATA&supplementary=0",
			"/Gateway/report/1?uri=/Gateway/temperature.jsp&method=GET&urivariable=0&commonparam=JSON&privateparam=0&returnmessage=0&abilitydescription=RealTimeAirTemperature&messagesupporttype=0&wrongcode=000&worngdescription=CAN_NOT_GET_DATA&supplementary=0"
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
			hrh.setHost("Host: 127.0.0.1");
			hrh.setAccept("Accept: text/html,application/xhtml+xml,application/xml,*/*");
			HttpRequestSender hrs = new HttpRequestSender(hrl, hrh, null, 80);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HttpRequestLineNoMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HttpRequestLineNoUrlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HttpRequestLineNoVersionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HttpRequestHeaderNoHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
