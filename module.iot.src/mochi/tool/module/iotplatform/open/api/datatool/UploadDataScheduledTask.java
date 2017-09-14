package mochi.tool.module.iotplatform.open.api.datatool;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import mochi.tool.data.interconversion.DataInterconversionTool;
import mochi.tool.module.iotplatform.open.api.connector.OpenAPIConfig;
import mochi.tool.module.iotplatform.open.api.exception.BodyLengthZeroException;
import mochi.tool.util.task.ScheduledTask;

@Deprecated
public class UploadDataScheduledTask extends ScheduledTask {
	
	private static boolean readyFlag = false;
	private static byte[] uploadDataMessage;
	private static long interval;
	
	private static Socket socket;
	private static DataInputStream din;
	private static DataOutputStream dout;
	
	public UploadDataScheduledTask() {
		UploadDataScheduledTask.interval = 0;
	}
	
	public static void setUploadDataMessage(byte[] message) {
		uploadDataMessage = message;
	}

	public void setInterval(long interval) {
		UploadDataScheduledTask.interval = interval;
		this.reschedule(interval);
	}
	
	public static long getInterval() {
		return interval;
	}
	
	public static void setUploadDataTaskReadyFlag(boolean readyFlag) {
		UploadDataScheduledTask.readyFlag = readyFlag;
	}
	
	private boolean checkReady() {
		if(readyFlag) {
			this.reschedule(interval);
			return true;
		} else {
			return false;
		}
	}
	
	public void tryReactiveTask() {
		this.reschedule(interval);
	}
	
	@Override
	public void run() {
		if(checkReady() && uploadDataMessage != null) {
			try {
				socket = new Socket(OpenAPIConfig.IP, OpenAPIConfig.PORT);
				din = new DataInputStream(socket.getInputStream());
				dout = new DataOutputStream(socket.getOutputStream());
				byte[] currentTime = DataInterconversionTool.longToBytes(System.currentTimeMillis());
				System.arraycopy(currentTime, 0, uploadDataMessage, 
						DataConfig.TOTALLENGTH_LENGTH + DataConfig.COMMANDID_LENGTH, 
						currentTime.length);
				dout.write(uploadDataMessage);
				byte[] feedbackLengthBytes = new byte[2];
				din.read(feedbackLengthBytes);
				short feedbackLength = DataInterconversionTool.bytesToShort(feedbackLengthBytes);
				byte[] feedback = new byte[feedbackLength];
				din.read(feedback);
				MessageResponse mr = new MessageResponse(feedbackLength, feedback);
				din.close();
				dout.close();
				socket.close();
				ResponseBody rb = mr.getResponseBody();
				while(rb.next()) {
					switch(rb.getCurrentTag()) {
					case MessageProtocolConfig.X1001:
						System.out.println("网关上传数据返回码：" + DataInterconversionTool.bytesToString(rb.getCurrentValue()));
					case MessageProtocolConfig.X1002:
						System.out.println(DataInterconversionTool.bytesToString(rb.getCurrentValue()));
						System.out.println("网关上传数据错误描述：" + DataInterconversionTool.bytesToString(rb.getCurrentValue()));
					}
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (BodyLengthZeroException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			this.reschedule(0);
			System.out.println("上传设备数据缺少用户账号信息或者没有上传数据。");
		}
	}

}
