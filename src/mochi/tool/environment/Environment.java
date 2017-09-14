package mochi.tool.environment;

import java.io.File;

public class Environment {

	private File currentDirectoryFile;
	
	public Environment() {
		currentDirectoryFile = new File("");
	}
	
	public String getCurrentRuntimePath() {
		return currentDirectoryFile.getAbsolutePath();
	}
	
}
