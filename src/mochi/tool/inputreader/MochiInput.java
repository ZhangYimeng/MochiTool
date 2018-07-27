package mochi.tool.inputreader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import mochi.tool.file.exception.NotAFileException;

public class MochiInput {

	private InputStream input;

	public MochiInput(InputStream input) throws IOException, NotAFileException {
		this.input = input;
	}

	public String readAllContents() throws IOException {
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length;
		while ((length = input.read(buffer)) != -1) {
			result.write(buffer, 0, length);
		}
		return result.toString("UTF-8");
	}
	
	public void close() {
		try {
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
