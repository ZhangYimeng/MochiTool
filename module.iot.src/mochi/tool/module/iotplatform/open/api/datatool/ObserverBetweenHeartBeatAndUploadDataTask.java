package mochi.tool.module.iotplatform.open.api.datatool;

@Deprecated
public class ObserverBetweenHeartBeatAndUploadDataTask {

	private static HeartBeatScheduledTask heartBeat;
	private static UploadDataScheduledTask dataUpload;
	
	public ObserverBetweenHeartBeatAndUploadDataTask(HeartBeatScheduledTask heartBeat, UploadDataScheduledTask dataUpload) {
		ObserverBetweenHeartBeatAndUploadDataTask.heartBeat = heartBeat;
		ObserverBetweenHeartBeatAndUploadDataTask.dataUpload = dataUpload;
	}
	
	public static void notifyUploadDataTaskGetInterval() {
		dataUpload.setInterval(heartBeat.getInterval());
	}
	
}
