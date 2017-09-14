package mochi.tool.module.usermanagement.foundation;

public class UserTokenObjectInCache {

	private String username;
	private String token;
	private boolean activeFlag;
	
	public UserTokenObjectInCache(String username, String token) {
		this.username = username;
		this.token = token;
		this.activeFlag = true;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getToken() {
		return token;
	}
	
	public boolean getActiveFlag() {
		return activeFlag;
	}
	
	public void setActiveFlagTrue() {
		activeFlag = true;
	}
	
	public void setActiveFlagFalse() {
		activeFlag = false;
	}
	
}
