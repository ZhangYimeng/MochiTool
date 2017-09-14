package mochi.tool.module.iotplatform.test.dbtest;

import mochi.tool.module.iotplatform.open.api.connector.Connector;
import mochi.tool.module.iotplatform.open.api.exception.ConnectorDeviceIDSetException;
import mochi.tool.module.iotplatform.open.api.exception.ConnectorUsernameSetException;

@SuppressWarnings("deprecation")
public class ConnectorTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Connector conn = new Connector();
		try {
			Connector.setUsername("saitoiyoli");
			Connector.setToken("ba44b53070a44a67a147");
			Connector.setUserPassword("f820bc6ab2");
			Connector.setDeviceID("119OX9I46D");
			System.out.println("done");
		} catch (ConnectorUsernameSetException e) {
			e.printStackTrace();
		} catch (ConnectorDeviceIDSetException e) {
			e.printStackTrace();
		}
	}

}
