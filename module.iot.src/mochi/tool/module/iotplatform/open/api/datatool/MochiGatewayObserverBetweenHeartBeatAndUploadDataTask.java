package mochi.tool.module.iotplatform.open.api.datatool;

public class MochiGatewayObserverBetweenHeartBeatAndUploadDataTask {

	private static MochiGatewayHeartBeatScheduledTask heartBeat;
	private static MochiGatewayUploadDataScheduledTask dataUpload;
	
	public MochiGatewayObserverBetweenHeartBeatAndUploadDataTask(MochiGatewayHeartBeatScheduledTask heartBeat, MochiGatewayUploadDataScheduledTask dataUpload) {
		MochiGatewayObserverBetweenHeartBeatAndUploadDataTask.heartBeat = heartBeat;
		MochiGatewayObserverBetweenHeartBeatAndUploadDataTask.dataUpload = dataUpload;
	}
	
	public static void notifyUploadDataTaskGetInterval() {
		dataUpload.setInterval(heartBeat.getInterval());
	}
	
}
