package mochi.tool.module.iotplatform.test.dbtest;

import java.net.UnknownHostException;


import mochi.tool.module.iotplatform.foundation.exception.DuplicatedDeviceIDException;
import mochi.tool.module.iotplatform.foundation.exception.DuplicatedSourceTagException;
//import mochi.tool.module.iotplatform.foundation.DataSourceDevice;
//import mochi.tool.module.iotplatform.foundation.GatewayDevice;
//import mochi.tool.module.iotplatform.foundation.dataanalyse.MessageProtocolConfig;
//import mochi.tool.module.iotplatform.foundation.mongodbtool.MongoDBConfig;
import mochi.tool.module.iotplatform.foundation.mongodbtool.exception.DBCollectionNotExistException;

public class registerUserTest {

	public static void main(String[] args) throws DBCollectionNotExistException, DuplicatedDeviceIDException, UnknownHostException, DuplicatedSourceTagException {		
		//GatewayDevice.createUserDevicelistCollection("saitoiyoli");
		//DataSourceDevice.createUserSourcelistCollection("saitoiyoli");
		//GatewayDevice.registerNewGatewayDevice("saitoiyoli", "oppo", "phone", "Beijing", MessageProtocolConfig.DEVICE_WRITE_TYPE_ASYN, MessageProtocolConfig.DEVICE_DO_NOT_HAS_FIX_IP, null, MessageProtocolConfig.DEVICE_TYPE_STANDARD);
		//DataSourceDevice.registerDataSourceDevice("saitoiyoli", "16200F1C1G", "dd", "a1", "Location", "Beijing", MongoDBConfig.SOURCE_DIAGRAM_CURVE);
		//GatewayDevice.deleteGatewayDevice("saitoiyoli", "16200F1C1G");
	}

}
